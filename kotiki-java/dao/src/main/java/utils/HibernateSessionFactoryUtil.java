package utils;

import entities.*;
import org.hibernate.*;
import org.hibernate.boot.model.naming.*;
import org.hibernate.cfg.*;

public class HibernateSessionFactoryUtil {

  private static SessionFactory sessionFactory;

  static {
    try {
      Configuration configuration = new Configuration();
      configuration.setPhysicalNamingStrategy(new CamelCaseToUnderscoresNamingStrategy());
      configuration.configure();
      sessionFactory = configuration.buildSessionFactory();
    }
    catch(Exception e){
      System.out.println(e.getMessage());
    }
  }

  public static SessionFactory getSessionFactory() {
    return sessionFactory;
  }

}
