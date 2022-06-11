package ru.itmo.nfomkin.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itmo.nfomkin.dto.OwnerDto;
import ru.itmo.nfomkin.entity.Owner;
import ru.itmo.nfomkin.exception.NoEntityException;
import ru.itmo.nfomkin.mapper.OwnerMapper;
import ru.itmo.nfomkin.service.OwnerService;


@RestController
@RequestMapping("/owners")
public class OwnerController {

  private OwnerService service;
  private OwnerMapper mapper;

  public OwnerController(OwnerService service, OwnerMapper mapper) {
    this.service = service;
    this.mapper = mapper;
  }

  @GetMapping()
  public ResponseEntity<List<OwnerDto>> index() {
    List<OwnerDto> owners = service.findAll().stream().map(mapper::toDto).toList();
    return !owners.isEmpty()
        ? new ResponseEntity<>(owners, HttpStatus.OK)
        : new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @GetMapping("/{id}")
  @PreAuthorize("hasAnyAuthority('read')")
  public ResponseEntity<OwnerDto> show(@PathVariable("id") Long id) {
    try {
      OwnerDto ownerDto = mapper.toDto(
          service.findById(id).orElseThrow(() -> new NoEntityException("owner not found")));
      return new ResponseEntity<>(ownerDto, HttpStatus.OK);
    } catch (NoEntityException e) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @PostMapping()
  @PreAuthorize("hasAnyAuthority('write')")
  public ResponseEntity<?> create(OwnerDto ownerDto) {
    try {
      service.saveOrUpdate(mapper.toOwner(ownerDto));
      return new ResponseEntity<>(HttpStatus.CREATED);
    }
    catch (Exception e)
    {
      e.printStackTrace();
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }

  @PatchMapping("/{id}")
  @PreAuthorize("hasAnyAuthority('write')")
  public ResponseEntity<?> update(@PathVariable("id") Long id, OwnerDto ownerDto) {
    try {
      Owner owner = service.findById(id).orElse(null);
      if (owner != null) {
        ownerDto.setId(owner.getId());
        owner = mapper.toOwner(ownerDto);
        service.saveOrUpdate(owner);
        return new ResponseEntity<>(HttpStatus.OK);
      }
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    catch (Exception e)
    {
      e.printStackTrace();
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasAnyAuthority('write')")
  public ResponseEntity<?> delete(@PathVariable("id") Long id) {
    Owner owner = service.findById(id).orElse(null);
    if (owner != null) {
      service.delete(owner);
      return new ResponseEntity<>(HttpStatus.OK);
    }
    else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

}
