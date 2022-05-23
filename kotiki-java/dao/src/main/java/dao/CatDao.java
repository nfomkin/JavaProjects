package dao;

import entities.*;
import java.util.*;
import org.hibernate.*;
import org.slf4j.*;
import utils.*;

public class CatDao {

  private static final CatDao instance = new CatDao();
  private static final Logger log = LoggerFactory.getLogger(CatDao.class);

  private CatDao() {}

  public static CatDao getInstance() {
    return instance;
  }

  public Optional<Cat> findById(Long id) {
     log.info("method findById");
     Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
     Transaction tx = session.beginTransaction();
     Cat cat = session.get(Cat.class, id);
     tx.commit();
     session.close();
     return Optional.ofNullable(cat);
  }

  public List<Cat> findAll() {
    return null;
  }

  public Cat save(Cat cat) {
    log.info("method save");
    Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
    session.beginTransaction();
    session.persist(cat);
    session.getTransaction().commit();
    session.close();
    return cat;
  }

  public void update(Cat cat) {
    log.info("method update");
    Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
    Transaction tx1 = session.beginTransaction();
    session.merge(cat);
    tx1.commit();
    session.close();
  }

  public void delete(Cat cat) {
    log.info("method delete");
    Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
    Transaction tx1 = session.beginTransaction();
    session.remove(cat);
    tx1.commit();
    session.close();
  }

}
