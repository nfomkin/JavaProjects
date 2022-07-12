package ru.itmo.nfomkin.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itmo.nfomkin.domain.Owner;
import ru.itmo.nfomkin.dto.OwnerDto;
import ru.itmo.nfomkin.exception.NoEntityException;
import ru.itmo.nfomkin.service.OwnerService;


@RestController
@RequestMapping(value = "/owners")
public class OwnerController {

  private final OwnerService service;

  public OwnerController(OwnerService service) {
    this.service = service;
  }

  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping()
  public ResponseEntity<List<OwnerDto>> getAll() {
    List<OwnerDto> owners = service.findAll().stream().map(service::toDto).toList();
    return !owners.isEmpty()
        ? new ResponseEntity<>(owners, HttpStatus.OK)
        : new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("/{id}")
  public ResponseEntity<OwnerDto> show(@PathVariable("id") Long id) {
    Optional<OwnerDto> owner = service.findById(id).map(service::toDto);
    return owner.map(ownerDto -> new ResponseEntity<>(ownerDto, HttpStatus.OK))
        .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping("/new")
  public ResponseEntity<?> create(@RequestBody @Valid OwnerDto ownerDto, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      String message = bindingResult.getFieldErrors().stream()
          .map(FieldError::getDefaultMessage)
          .collect(Collectors.joining(", "));
      throw new RuntimeException(message);
    }
    service.saveOrUpdate(service.toEntity(ownerDto));
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @PatchMapping("/{id}")
  public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody OwnerDto ownerDto)
      throws NoEntityException {
    Optional<Owner> maybeOwner = service.findById(id);
    if (maybeOwner.isEmpty()) {
      throw new NoEntityException(String.format("Owner with id = %s doesn't exist", id));
    }

    Owner owner = maybeOwner.get();
    if (ownerDto.getUsername() != null) {
      owner.setUsername(ownerDto.getUsername());
    }
    if (ownerDto.getPassword() != null) {
      owner.setPassword(ownerDto.getPassword());
    }
    if (ownerDto.getName() != null) {
      owner.setName(ownerDto.getName());
    }
    if (ownerDto.getBirthDate() != null) {
      owner.setBirthDate(LocalDate.parse(ownerDto.getBirthDate()));
    }

    service.saveOrUpdate(owner);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @DeleteMapping("/{id}")
  public ResponseEntity<?> delete(@PathVariable("id") Long id) throws NoEntityException {
    service.delete(id);
    return new ResponseEntity<>(HttpStatus.OK);
  }

}
