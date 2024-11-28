package com.univ.util;

public class Routes {
  public static final String HOME_ROUTE = "/";
  public static final String LOGIN_ROUTE = "/login";
  public static final String REGISTER_ROUTE = "/register";
  public static final String LOGOUT_ROUTE = "/logout";
  public static final String DASHBOARD_ROUTE = "/dashboard";
  public static final String USERS_ROUTE = "/users";

  private Routes() {
    throw new UnsupportedOperationException("Routes class cannot be instantiated");
  }

}
