package ru.itmo.nfomkin.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.itmo.nfomkin.domain.Owner;
import ru.itmo.nfomkin.domain.Role;
import ru.itmo.nfomkin.dto.OwnerDto;
import ru.itmo.nfomkin.exception.NoEntityException;
import ru.itmo.nfomkin.repository.OwnerRepository;

@Service
public class OwnerService implements UserDetailsService {

  private final OwnerRepository ownerRepository;
  private final PasswordEncoder encoder;

  public OwnerService(OwnerRepository ownerRepository, PasswordEncoder encoder) {
    this.ownerRepository = ownerRepository;
    this.encoder = encoder;
  }

  public Optional<Owner> findById(Long id) {
    return ownerRepository.findById(id);
  }

  public List<Owner> findByName(String name) {
    return ownerRepository.findByName(name);
  }

  public Optional<Owner> findByUsername(String username) {
    return ownerRepository.findFirstByUsername(username);
  }

  public List<Owner> findAll() {
    return ownerRepository.findAll();
  }

  public void saveOrUpdate(Owner owner) {
    ownerRepository.save(owner);
  }

  public void delete(Long id) throws NoEntityException {
    Optional<Owner> owner = findById(id);
    if (owner.isEmpty()) {
      throw new NoEntityException(String.format("Username with id %s doesn't exist", id));
    }
    ownerRepository.delete(owner.get());
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return ownerRepository
        .findFirstByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException(String.format("Username %s not found", username)));
  }

  public OwnerDto toDto(Owner owner) {
    return OwnerDto.builder()
        .id(owner.getId())
        .username(owner.getUsername())
        .password(owner.getPassword())
        .birthDate(String.valueOf(owner.getBirthDate()))
        .name(owner.getName())
        .build();
  }

  public Owner toEntity(OwnerDto dto) {
    return Owner.builder()
        .username(dto.getUsername())
        .password(encoder.encode(dto.getPassword()))
        .name(dto.getName())
        .birthDate(dto.getBirthDate() != null ? LocalDate.parse(dto.getBirthDate()) : null)
        .roles(Set.of(Role.USER))
        .build();
  }

}
