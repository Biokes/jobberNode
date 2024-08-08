package com.jobNode.jobber.security.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.util.List;

public class Utils {
    public static int SEVEN = 7;
    public static String BEARER = "Bearer ";
    public static List<String>END_POINTS = List.of("api/vi/jobberNode/login");
    public static String JWT_PREFIX = "Bearer ";
    public static String ROLES = "roles";
    public static String APP_NAME = "JobberNode";
    public static String SECRET = "secrets";
}
