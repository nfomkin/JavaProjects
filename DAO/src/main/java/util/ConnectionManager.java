package util;

import java.lang.reflect.Proxy;
import java.sql.*;
import java.util.*;
import java.util.concurrent.*;

public class ConnectionManager {

  private static BlockingQueue<Connection> pool;
  private static List<Connection> sourceConnections;
  private final static String POOL_SIZE_KEY = "db.pool_size";
  private final static int DEFAULT_POOL_SIZE = 10;
  private final static String URL_KEY = "db.url";
  private final static String USERNAME_KEY = "db.username";
  private final static String PASSWORD_KEY = "db.password";

  static {
    loadDriver();
    initConnectionPool();
  }

  private static void loadDriver() {
    try {
      Class.forName("org.postgresql.Driver");
    } catch (ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
  }

  public static void closePool() {
    try {
      for (Connection sourceConnection : sourceConnections) {
        sourceConnection.close();
      }
    }
    catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  private static Connection getConnection() {
    try {
      return DriverManager.getConnection(
          PropertiesUtil.get(URL_KEY),
          PropertiesUtil.get(USERNAME_KEY),
          PropertiesUtil.get(PASSWORD_KEY)
      );
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  private static void initConnectionPool() {
    var poolSize = PropertiesUtil.get(POOL_SIZE_KEY);
    int size = poolSize == null ? DEFAULT_POOL_SIZE : Integer.parseInt(poolSize);
    pool = new ArrayBlockingQueue<>(size);
    sourceConnections = new ArrayList<>(size);
    for (int i = 0; i < size; i++) {
      var connection = getConnection();
      var proxyConnection = (Connection)Proxy.newProxyInstance(ConnectionManager.class.getClassLoader(), new Class[]{Connection.class},
          (proxy, method, args) -> method.getName().equals("close")
              ? pool.add((Connection)proxy)
              : method.invoke(connection, args));
      pool.add(proxyConnection);
      sourceConnections.add(connection);
    }
  }

  public static Connection get() {
    try {
      return pool.take();
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }
}
