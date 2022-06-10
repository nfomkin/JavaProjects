package ru.itmo.nfomkin.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itmo.nfomkin.entity.Cat;

@Repository
public interface CatRepository extends JpaRepository<Cat, Long> {
  public List<Cat> findByName(String name);
}
