package com.kirillus.backend.security;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class TokenStore {
    private static final Map<String, Integer> tokens = new ConcurrentHashMap<>();
    private static final Map<Integer, String> userTokens = new ConcurrentHashMap<>();

    public static String generateToken(Integer userId) {
        // Удаляем старый токен пользователя, если есть
        String oldToken = userTokens.get(userId);
        if (oldToken != null) {
            tokens.remove(oldToken);
        }
        
        // Генерируем новый токен
        String token = UUID.randomUUID().toString();
        tokens.put(token, userId);
        userTokens.put(userId, token);
        
        return token;
    }

    public static boolean isValidToken(String token) {
        return tokens.containsKey(token);
    }

    public static Integer getUserId(String token) {
        return tokens.get(token);
    }

    public static void removeToken(String token) {
        Integer userId = tokens.remove(token);
        if (userId != null) {
            userTokens.remove(userId);
        }
    }
}

