package service;

import dao.*;
import entities.*;
import java.util.*;

public class OwnerService {

  private OwnerDao dao;

  public OwnerService(OwnerDao dao) {
    this.dao = dao;
  }

  public Optional<Owner> findById(Long id) {
    return dao.findById(id);
  }

  public Owner save(Owner owner) {
    return dao.save(owner);
  }

  public void update(Owner owner) {
    dao.update(owner);
  }

  public void delete(Owner owner) {
    dao.delete(owner);
  }
}
