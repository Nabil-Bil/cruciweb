package com.univ.util;

import org.mindrot.jbcrypt.BCrypt;

public class BCryptUtil {

  public static String hashPassword(String password) {
    String salt = BCrypt.gensalt();
    return BCrypt.hashpw(password, salt);
  }

  public static boolean verifyPassword(String password, String hashedPassword) {
    return BCrypt.checkpw(password, hashedPassword);
  }
}