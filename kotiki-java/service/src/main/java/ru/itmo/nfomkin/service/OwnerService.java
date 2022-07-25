package ru.itmo.nfomkin.service;

import java.util.List;
import java.util.Optional;
import org.springframework.security.core.userdetails.UserDetailsService;
import ru.itmo.nfomkin.domain.Owner;
import ru.itmo.nfomkin.dto.OwnerDto;
import ru.itmo.nfomkin.exception.NoEntityException;

public interface OwnerService extends UserDetailsService {

  public Optional<Owner> findById(Long id);

  public List<Owner> findByName(String name);

  public Optional<Owner> findByUsername(String username);

  public List<Owner> findAll();

  public void saveOrUpdate(Owner owner);

  public void delete(Long id) throws NoEntityException;

  public OwnerDto toDto(Owner owner);

  public Owner toEntity(OwnerDto dto);

}
