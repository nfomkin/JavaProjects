package dao;

import entities.*;
import java.util.*;
import utils.*;
import org.hibernate.*;

public class OwnerDao {

  private static OwnerDao instance = new OwnerDao();

  private OwnerDao() {}

  public static OwnerDao getInstance() {
    return instance;
  }

  public Optional<Owner> findById(Long id) {
    Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
    Transaction tx = session.beginTransaction();
    Owner owner = session.get(Owner.class, id);
    tx.commit();
    session.close();
    return Optional.ofNullable(owner);
  }

  public Owner save(Owner owner) {
    Session session = HibernateSessionFactoryUtil.getSessionFactory().getCurrentSession();
    session.beginTransaction();
    session.persist(owner);
    session.getTransaction().commit();
    session.close();
    return owner;
  }

  public void update(Owner owner) {
    Session session = HibernateSessionFactoryUtil.getSessionFactory().getCurrentSession();
    Transaction tx1 = session.beginTransaction();
    session.merge(owner);
    tx1.commit();
    session.close();
  }

  public void delete(Owner owner) {
    Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
    Transaction tx1 = session.beginTransaction();
    session.remove(owner);
    tx1.commit();
    session.close();
  }

}
