package com;

public class Result {
    
    public static final String LOGIN_SUCCESS               = "SERVER::LOGIN_SUCCESS";
    public static final String LOGIN_BAD_USERNAME          = "SERVER::LOGIN_BAD_USERNAME";
    public static final String LOGIN_BAD_PASSWORD          = "SERVER::LOGIN_BAD_PASSWORD";
    public static final String LOGIN_TOO_MANY_ATTEMPTS     = "SERVER::LOGIN_TOO_MANY_ATTEMPTS";
    
    public static final String REGISTER_SUCCESS            = "SERVER::REGISTER_SUCCESS";
    public static final String REGISTER_DUPLICATE_USER     = "SERVER::REGISTER_DUPLICATE_USER";
    
    public static final String CALC_SUCCESS                = "SERVER::CALC_SUCCESS";
    public static final String CALC_BAD_EXPR               = "SERVER::CALC_BAD_EXPR";
    
    public static final String GAME_BAD_EXPR               = "SERVER::GAME_BAD_EXPR";
    
    public static final String PASSWORD_REC_SUCCESS        = "SERVER::PASSWORD_REC_SUCCESS";
    public static final String PASSWORD_REC_BAD_USERNAME   = "SERVER::PASSWORD_REC_BAD_USERNAME";
    
    public static final String UNKNOWN_COMMAND             = "SERVER::UNKNOWN_COMMAND";
}
