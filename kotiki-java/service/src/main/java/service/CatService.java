package service;

import dao.*;
import entities.*;
import java.util.*;
import javax.swing.text.html.*;

public class CatService {
  private CatDao dao;

  public CatService(CatDao dao) {
    this.dao = dao;
  }

  public Optional<Cat> findById(Long id) {
    return dao.findById(id);
  }

  public Cat save(Cat cat) {
    return dao.save(cat);
  }

  public void update(Cat cat) {
    dao.update(cat);
  }

  public void delete(Cat cat) {
    dao.delete(cat);
  }

}
