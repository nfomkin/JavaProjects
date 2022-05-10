package service;

import dao.*;
import entities.*;

public class OwnerService {
  private static final OwnerDao dao = OwnerDao.getInstance();

  public Owner findById(Long id) {
    return dao.findById(id);
  }

  public Owner save(Owner owner) {
    return dao.save(owner);
  }

  public void update(Owner owner) {
    dao.update(owner);
  }

  public void delete(Owner owner){
    dao.delete(owner);
  }
}
