package util;

import java.io.*;
import java.util.*;

public final class PropertiesUtil {

  private static final Properties PROPERTIES = new Properties();

  static{
    loadProperties();
  }

  private static void loadProperties() {
    try (var inputStream = PropertiesUtil.class.getClassLoader().getResourceAsStream("app.properties")) {
      PROPERTIES.load(inputStream);
    } catch (IOException e) {
        throw new RuntimeException(e);
    }
  }

  public static String get(String key) {
    return PROPERTIES.getProperty(key);
  }

}
