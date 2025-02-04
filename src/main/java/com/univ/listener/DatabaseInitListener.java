package com.univ.listener;

import com.univ.util.EntityManagerProvider;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.logging.Logger;

public class DatabaseInitListener implements ServletContextListener {

    private static final Logger LOGGER = Logger.getLogger(DatabaseInitListener.class.getName());

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            EntityManagerFactory emf = EntityManagerProvider.instance().getEntityManagerFactory();
            EntityManager em = emf.createEntityManager();

            if (!isDataLoaded(em)) {
                LOGGER.info("Data not found. Executing initialization script...");
                executeScript(em, "/META-INF/init.sql");
            } else {
                LOGGER.info("Data Already Loaded. Skipping script execution.");
            }

            em.close();
        } catch (Exception e) {
            LOGGER.severe("Error while executing the script: " + e.getMessage());
        }
    }

    private boolean isDataLoaded(EntityManager em) {
        try {
            String query = "SELECT COUNT(u) FROM User u";
            Query q = em.createQuery(query);

            Long count = (Long) q.getSingleResult();
            return count > 0;
        } catch (Exception e) {
            LOGGER.severe("Error checking for users existance: " + e.getMessage());
            return false;
        }
    }

    private void executeScript(EntityManager em, String scriptPath) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                getClass().getResourceAsStream(scriptPath)))) {

            String line;
            StringBuilder script = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                script.append(line).append("\n");
            }

            String[] sqlStatements = script.toString().split(";");
            for (String sql : sqlStatements) {
                if (!sql.trim().isEmpty()) {
                    em.getTransaction().begin();
                    em.createNativeQuery(sql.trim()).executeUpdate();
                    em.getTransaction().commit();
                }
            }

            LOGGER.info("Script executed successfully.");
        } catch (Exception e) {
            LOGGER.severe("Error executing script: " + e.getMessage());
        }
    }
}