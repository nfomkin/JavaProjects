package ru.itmo.nfomkin.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import ru.itmo.nfomkin.entity.Owner;
import ru.itmo.nfomkin.repository.OwnerRepository;

@Service
public class OwnerService {

  private final OwnerRepository repository;

  public OwnerService(OwnerRepository repository) {
    this.repository = repository;
  }

  public Optional<Owner> findById(Long id) {
    return repository.findById(id);
  }

  public List<Owner> findAll() { return repository.findAll(); }

  public Owner saveOrUpdate(Owner owner) {
    return repository.save(owner);
  }

  public void delete(Owner owner) {
    repository.delete(owner);
  }
}
