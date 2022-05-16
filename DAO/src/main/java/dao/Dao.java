package dao;

import java.util.*;

public interface Dao<E, K> {
  Optional<E> findById(K id);
  List<E> findAll();
  E save(E entity);
  void update(E entity);
  boolean delete(K id);
}
