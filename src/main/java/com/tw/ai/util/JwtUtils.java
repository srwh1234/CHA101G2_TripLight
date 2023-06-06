package com.tw.ai.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

//public class JwtUtils {
//    private static final String SECRET_KEY = "secretKey";
//
//    public static void main(String[] args) {
//        String jwtToken = "yourJwtToken"; // 要解析的JWT令牌
//        Claims claims = parseJwtToken(jwtToken);
//        if (claims != null) {
//            String userId = claims.getSubject();
//            String username = (String) claims.get("username");
//
//            System.out.println("User ID: " + userId);
//            System.out.println("Username: " + username);
//        } else {
//            System.out.println("Invalid JWT token");
//        }
//    }
//
//    public static Claims parseJwtToken(String jwtToken) {
//        try {
//            return Jwts.parser()
//                    .setSigningKey(SECRET_KEY)
//                    .parseClaimsJws(jwtToken)
//                    .getBody();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//}

