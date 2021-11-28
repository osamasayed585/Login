package com.example.login.ui;

public class Constants {
    private static String token;
    private static String logoutToken;

    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        Constants.token = token;
    }

    public static String getLogoutToken() {
        return logoutToken;
    }

    public static void setLogoutToken(String logoutToken) {
        Constants.logoutToken = logoutToken;
    }
}
