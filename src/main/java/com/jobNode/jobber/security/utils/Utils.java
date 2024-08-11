package com.jobNode.jobber.security.utils;

import java.util.List;

public class Utils {
    public static int SEVEN = 7;
    public static String BEARER = "Bearer ";
    public static List<String>END_POINTS = List.of("/api/v1/jobberNode/login");
    public static String JWT_PREFIX = "Bearer ";
    public static String ROLES = "roles";
    public static String APP_NAME = "JobberNode";
    public static String SECRET = "secrets";
    public static String[] CUSTOMER_END_POINTS = {"/api/v1/jobberNode/customer/**"};
    public static String[] PROVIDER_URL = {"/api/v1/jobberNode/provider/**"};
    public static String[] ADMIN_URL = {"/api/v1/jobberNode/admin/**"};
    public static String[] PUBLIC_URLS = {
                                            "/api/v1/jobberNode/customer/register",
                                            "/api/v1/jobberNode/login",
                                            "/api/v1/jobbberNode/provider/register"};
}
