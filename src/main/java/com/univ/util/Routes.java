package com.univ.util;

public class Routes {
    public static final String HOME_ROUTE = "/";
    public static final String LOGIN_ROUTE = "/login";
    public static final String REGISTER_ROUTE = "/register";
    public static final String LOGOUT_ROUTE = "/logout";
    public static final String USERS_ROUTE = "/users";
    public static final String GRIDS_ROUTE = "/grids";
    public static final String GRID_ROUTE = "/grid/*";
    public static final String CREATE_USER_ROUTE = "/users/create";
    public static final String CREATE_GRID_ROUTE = "/grids/create";
    public static final String GAMES_ROUTE = "/games";
    public static final String GAME_ROUTE = "/game/*";
    public static final String ANONYMOUS_GAME_ROUTE = "/anonymous-game/grid/*";

    private Routes() {
        throw new UnsupportedOperationException("Routes class cannot be instantiated");
    }

}
