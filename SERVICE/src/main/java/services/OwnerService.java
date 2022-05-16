package services;

import dao.*;
import entity.*;
import java.util.*;

public class OwnerService {

  private OwnerDao ownerDao = OwnerDao.getInstance();

  public Optional<Owner> find(Long id) {
    return ownerDao.findById(id);
  }

  public Owner save(Owner owner) {
    return ownerDao.save(owner);
  }

  public void update(Owner owner) {
    ownerDao.update(owner);
  }

  public boolean delete(Long id) {
    return ownerDao.delete(id);
  }

}
