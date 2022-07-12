package ru.itmo.nfomkin.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itmo.nfomkin.domain.Cat;
import ru.itmo.nfomkin.domain.Owner;

@Repository
public interface CatRepository extends JpaRepository<Cat, Long> {
  List<Cat> findByName(String name);
  Optional<Cat> findByOwnerAndId(Owner owner, Long id);
  List<Cat> findAllByOwner(Owner owner);
}
