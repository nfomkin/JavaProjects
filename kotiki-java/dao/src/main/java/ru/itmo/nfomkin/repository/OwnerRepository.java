package ru.itmo.nfomkin.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;
import ru.itmo.nfomkin.entity.*;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Long> {

}
