package services;

import dao.*;
import dto.*;
import entity.*;
import java.util.*;

public class OwnerService {

  private OwnerDao dao;

  public OwnerService(OwnerDao dao) {
    this.dao = dao;
  }

  public Optional<Owner> findByID(Long id) {
    return dao.findById(id);
  }

  public List<Owner> findAll() { return dao.findAll();}

  public List<Owner> findAll(OwnerFilter filter) {return dao.findAll(filter);}

  public Owner save(Owner owner) {
    return dao.save(owner);
  }

  public void update(Owner owner) {
    dao.update(owner);
  }

  public boolean delete(Long id) {
    return dao.delete(id);
  }

}
