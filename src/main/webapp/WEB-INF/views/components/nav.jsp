<%@ page import="com.univ.util.SessionManager" %>
<%@ page import="jakarta.servlet.http.HttpSession" %>
<%@ page import="com.univ.util.Routes" %>
<%
    SessionManager sessionManager = new SessionManager(request.getSession(false));
    boolean isLoggedIn = sessionManager.isLoggedIn();
    boolean isAdmin = sessionManager.isAdmin();
%>
<nav id="topbar">
    <a href="${pageContext.request.contextPath}/" id="logo">
        <img src="${pageContext.request.contextPath}/resources/assets/images/logo.png" alt="Logo" id="logo-image"/>
        <h1>CruciWeb</h1>
    </a>
    <ul>
        <li><a href="<%=request.getContextPath()%>/">Accueil</a></li>
        <li><a href="<%=request.getContextPath().concat(Routes.GRIDS_ROUTE)%>">Grilles</a></li>
        <% if (isAdmin) { %>
        <li><a href="<%=request.getContextPath().concat(Routes.USERS_ROUTE)%>">Utilisateurs</a></li>
        <% } %>
        <% if (isLoggedIn) {%>
        <li><a href="<%=request.getContextPath().concat(Routes.GAMES_ROUTE)%>">Mes Parties</a></li>
        <li>
            <form action="<%=request.getContextPath().concat(Routes.LOGOUT_ROUTE)%>" method="post">
                <input type="submit" value="Se déconnecter">
                <input type="hidden" name="csrfToken" value="${csrfToken}"/>
            </form>
        </li>

        <% } else { %>
        <li><a href="${pageContext.request.contextPath}/login">Se connecter</a></li>
        <li><a href="${pageContext.request.contextPath}/register">S'inscrire</a></li>
        <% } %>
    </ul>
</nav>