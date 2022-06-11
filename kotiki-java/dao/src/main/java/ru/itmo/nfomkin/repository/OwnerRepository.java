package ru.itmo.nfomkin.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itmo.nfomkin.entity.Owner;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Long> {
  public List<Owner> findByName(String name);
  public Optional<Owner> findByUsername(String username);
}
