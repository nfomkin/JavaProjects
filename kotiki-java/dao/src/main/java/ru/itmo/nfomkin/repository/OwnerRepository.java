package ru.itmo.nfomkin.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itmo.nfomkin.domain.Owner;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Long> {
  Optional<Owner> findFirstByUsername(String username);
  List<Owner> findByName(String name);
}
