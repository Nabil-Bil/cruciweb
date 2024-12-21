package com.univ.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.UUID;

public class Utils {
    public static UUID getUUIDFromUrl(HttpServletRequest req) {
        String pathInfo = req.getPathInfo();
        if (pathInfo != null && pathInfo.startsWith("/")) {
            pathInfo = pathInfo.substring(1);
        } else {
            return null;
        }
        return UUID.fromString(pathInfo);
    }

    public static boolean validateRequest(UUID id, HttpServletResponse resp, boolean isEmpty) throws IOException {
        if (id == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return true;
        }
        if (isEmpty) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return true;
        }
        return false;
    }
}
