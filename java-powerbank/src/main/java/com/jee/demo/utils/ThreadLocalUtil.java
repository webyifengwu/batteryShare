package com.jee.demo.utils;

public class ThreadLocalUtil {
    private static final ThreadLocal<String> local=new ThreadLocal<>();
    public static void saveUser(String userId){
        local.set(userId);
    }
    public static String getUser(){
        return local.get();
    }
    public static void removeUser(){
        local.remove();
    }
}
