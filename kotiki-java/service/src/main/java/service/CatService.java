package service;

import dao.*;
import entities.*;
import java.util.*;

public class CatService {
  private static final CatDao dao = CatDao.getInstance();

  public Cat findById(Long id) {
    return dao.findById(id);
  }

  public Cat save(Cat cat, List<Cat> friends) {
    if (friends != null) {
      for (Cat cat1 : friends) {
        cat.addFriend(cat1);
        cat1.addFriend(cat);
      }
    }
    return dao.save(cat);
  }

  public void update(Cat cat) {
    dao.update(cat);
  }

  public void delete(Cat cat) {
    dao.delete(cat);
  }

}
