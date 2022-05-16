package services;

import dao.*;
import dto.*;
import entity.*;
import java.sql.*;
import java.util.*;

public class CatService {
  private static final CatDao dao = CatDao.getInstance();

  public Optional<Cat> findById(Long id) {
    return dao.findById(id);
  }

  public List<Cat> findAll(CatFilter filter) {
    return dao.findAll(filter);
  }

  public Cat save(Cat cat) throws SQLException {
    return dao.save(cat);
  }

  public void update(Cat cat) {
    dao.update(cat);
  }

  public boolean delete(Long id) {
    return dao.delete(id);
  }

}
