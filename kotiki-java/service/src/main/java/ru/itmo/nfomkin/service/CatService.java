package ru.itmo.nfomkin.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import ru.itmo.nfomkin.entity.Cat;
import ru.itmo.nfomkin.repository.CatRepository;

@Service
public class CatService {

  private CatRepository repository;

  public CatService(CatRepository repository) {
    this.repository = repository;
  }

  public List<Cat> findAll() {return repository.findAll();}

  public Optional<Cat> findById(Long id) {
    return repository.findById(id);
  }

  public Cat saveOrUpdate(Cat cat) {
    return repository.save(cat);
  }

  public void delete(Cat cat) {
    repository.delete(cat);
  }

}
