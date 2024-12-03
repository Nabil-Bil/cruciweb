package com.univ.util;

import com.univ.enums.Role;
import jakarta.servlet.http.HttpSession;

public final class SessionManager {

    private static final String SESSION_USER_ID_KEY = "userId";
    private static final String SESSION_ROLE_KEY = "role";
    private final HttpSession session;

    public SessionManager(HttpSession session) {
        this.session = session;
    }

    public boolean isLoggedIn() {
        return session != null && session.getAttribute(SESSION_USER_ID_KEY) != null;
    }

    public boolean isAdmin() {
        return isLoggedIn() && session.getAttribute(SESSION_ROLE_KEY) != null && session.getAttribute(SESSION_ROLE_KEY).equals(Role.ADMIN);
    }

    public Object getLoggedInUserId() {
        if (session == null)
            return null;
        return session.getAttribute(SESSION_USER_ID_KEY);
    }

    public void setLoggedinUser(Object userId, Object role) {
        if (session != null) {
            session.setAttribute(SESSION_USER_ID_KEY, userId);
            session.setAttribute(SESSION_ROLE_KEY, role);
        }
    }

    public void setAttribute(String key, Object value) {
        if (session != null) {
            session.setAttribute(key, value);
        }
    }

    public Object getAttribute(String key) {
        if (session == null)
            return null;
        return session.getAttribute(key);
    }
}