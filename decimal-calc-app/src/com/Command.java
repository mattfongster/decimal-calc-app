package com;

public class Command {
    
    public static final String LOGIN                = "LOGIN";
    public static final String REGISTER             = "REGISTER";
    public static final String CALCULATE            = "CALCULATE";
    public static final String GAME                 = "GAME";
    public static final String DISCONNECT           = "DISCONNECT";
    public static final String PASSWORD_RECOVERY    = "PASSWORD_RECOVERY";
    
    public static final String DELIMITER            = "::";
    
    public static String disconnectCommand() {
        return "CLIENT::" + DISCONNECT;
    }
    
    public static String loginCommand(String username, String password) {
        return String.format("CLIENT::%s::%s::%s", LOGIN, username, password);
    }
    
    public static String registerCommand(String username, String password, String emailAddress) {
        return String.format("CLIENT::%s::%s::%s::%s", REGISTER, username, password, emailAddress);
    }
    
    public static String calculateCommand(String expr) {
        return String.format("cLIENT::%s::%s", CALCULATE, expr);
    }
    
    public static String gameCommand(String expr) {
        return String.format("CLIENT::%s::%s", GAME, expr);
    }
    
    public static String passwordRecCommand(String username) {
        return String.format("CLIENT::%s::%s", PASSWORD_RECOVERY, username);
    }
}
