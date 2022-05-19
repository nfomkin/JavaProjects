package services;

import dao.*;
import dto.*;
import entity.*;
import java.util.*;

public class CatService {
  private CatDao dao;

  public CatService(CatDao dao) {
    this.dao = dao;
  }
  public Optional<Cat> findById(Long id) {
    return dao.findById(id);
  }

  public List<Cat> findAll() { return dao.findAll(); }

  public List<Cat> findAll(CatFilter filter) {
    return dao.findAll(filter);
  }

  public Cat save(Cat cat) {
    return dao.save(cat);
  }

  public void update(Cat cat) {
    dao.update(cat);
  }

  public boolean delete(Long id) {
    return dao.delete(id);
  }

}
