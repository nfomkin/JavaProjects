package dao;

import entities.*;
import org.hibernate.*;
import utils.*;

public class CatDao {

  private static CatDao instance = new CatDao();

  private CatDao() {}

  public static CatDao getInstance() {
    return instance;
  }

  public Cat findById(Long id) {
     Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
     Transaction tx = session.beginTransaction();
     Cat cat = session.get(Cat.class, id);
     tx.commit();
     session.close();
     return cat;
  }

  public Cat save(Cat cat) {
    Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
    session.beginTransaction();
    session.persist(cat);
    session.getTransaction().commit();
    session.close();
    return cat;
  }

  public void update(Cat cat) {
    Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
    Transaction tx1 = session.beginTransaction();
    session.merge(cat);
    tx1.commit();
    session.close();
  }

  public void delete(Cat cat) {
    Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
    Transaction tx1 = session.beginTransaction();
    session.remove(cat);
    tx1.commit();
    session.close();
  }

}
