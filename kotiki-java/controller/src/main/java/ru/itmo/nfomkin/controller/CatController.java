package ru.itmo.nfomkin.controller;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.itmo.nfomkin.domain.Cat;
import ru.itmo.nfomkin.domain.Color;
import ru.itmo.nfomkin.dto.CatDto;
import ru.itmo.nfomkin.exception.NoEntityException;
import ru.itmo.nfomkin.service.CatService;

@RestController
@RequestMapping("/cats")
public class CatController {

  private final CatService service;

  public CatController(CatService service){
    this.service = service;
  }

  @GetMapping("/admin")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<List<CatDto>> getAll() {
    List<CatDto> cats =  service.findAll().stream().map(service::toDto).toList();
    return !cats.isEmpty()
        ? new ResponseEntity<>(cats, HttpStatus.OK)
        : new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @GetMapping()
  @PreAuthorize("hasRole('USER')")
  public ResponseEntity<List<CatDto>> getAllForUser(@RequestParam(required = false) String color,
      @RequestParam(required = false) String name, @RequestParam(required = false) String breed,
      Principal principal) throws NoEntityException {
    List<CatDto> result =  service.findAllByOwner(principal.getName()).stream().map(service::toDto).toList();
    if (color != null) {
      result = result.stream()
          .filter(catDto -> Objects.equals(catDto.getColor(), color)).toList();
    }
    if (name != null) {
      result = result.stream()
          .filter(catDto -> Objects.equals(catDto.getName(), name)).toList();
    }
    if (breed != null) {
      result = result.stream()
          .filter(catDto -> Objects.equals(catDto.getBreed(), breed)).toList();
    }

    return !result.isEmpty()
        ? new ResponseEntity<>(result, HttpStatus.OK)
        : new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @GetMapping("/{id}")
  @PreAuthorize("hasRole('USER')")
  public ResponseEntity<CatDto> show(@PathVariable("id") Long id, Principal principal)
      throws NoEntityException {
    Optional<CatDto> maybeCat = service.findByOwnerAndId(principal.getName(), id).map(service::toDto);
    return maybeCat.map(catDto -> new ResponseEntity<>(catDto, HttpStatus.OK))
        .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  @PostMapping("/new")
  @PreAuthorize("hasRole('USER')")
  public ResponseEntity<?> create(@RequestBody @Valid CatDto catDto) throws NoEntityException {
    service.saveOrUpdate(service.toEntity(catDto));
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PostMapping("/addToOwner")
  @PreAuthorize("hasRole('USER')")
  public ResponseEntity<?> addCatToOwner(@RequestBody Long catId, Principal principal) throws NoEntityException {
    service.addToOwner(principal.getName(), catId);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PostMapping("/addFriendship")
  @PreAuthorize("hasRole('USER')")
  public ResponseEntity<?> addFriendship(@RequestBody Long catId1, @RequestBody Long catId2)
      throws NoEntityException {
    service.addFriendship(catId1, catId2);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PostMapping("/deleteFriendship")
  @PreAuthorize("hasRole('USER')")
  public ResponseEntity<?> deleteFriendship(@RequestBody Long catId1, @RequestBody Long catId2)
      throws NoEntityException {
    service.deleteFriendship(catId1, catId2);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PatchMapping("/{id}")
  @PreAuthorize("hasRole('USER')")
  public ResponseEntity<?> update(@PathVariable("id") Long id, CatDto catDto, Principal principal)
      throws NoEntityException {
    Optional<Cat> maybeCat = service.findByOwnerAndId(principal.getName(), id);
    if (maybeCat.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    Cat cat = maybeCat.get();
    if (catDto.getName() != null) {
      cat.setName(catDto.getName());
    }
    if (catDto.getBreed() != null) {
      cat.setBreed(catDto.getBreed());
    }
    if (catDto.getColor() != null) {
      cat.setColor(Color.valueOf(catDto.getColor()));
    }
    if (catDto.getBirthDate() != null) {
      cat.setBirthDate(LocalDate.parse(catDto.getBirthDate()));
    }

    service.saveOrUpdate(cat);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasRole('USER')")
  public ResponseEntity<?> delete(@PathVariable("id") Long id, Principal principal) throws NoEntityException {
    Optional<Cat> maybeCat = service.findByOwnerAndId(principal.getName(), id);
    if (maybeCat.isEmpty())
    {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    service.delete(id);
    return new ResponseEntity<>(HttpStatus.OK);
  }

}
