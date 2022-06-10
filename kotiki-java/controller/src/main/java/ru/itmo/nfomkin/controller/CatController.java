package ru.itmo.nfomkin.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itmo.nfomkin.dto.CatDto;
import ru.itmo.nfomkin.entity.Cat;
import ru.itmo.nfomkin.exception.NoEntityException;
import ru.itmo.nfomkin.mapper.CatMapper;
import ru.itmo.nfomkin.service.CatService;

@RestController
@RequestMapping("/cats")
public class CatController {
  private final CatService service;
  private final CatMapper mapper;

  public CatController(CatService service, CatMapper mapper){

    this.service = service;
    this.mapper = mapper;
  }

  @GetMapping()
  public ResponseEntity<List<CatDto>> index() {
    List<CatDto> cats =  service.findAll().stream().map(mapper::toDto).toList();
    return !cats.isEmpty()
        ? new ResponseEntity<>(cats, HttpStatus.OK)
        : new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }


  @GetMapping("/{id}")
  public ResponseEntity<CatDto> show(@PathVariable("id") Long id) {
    try {
      CatDto cat = mapper.toDto(service.findById(id).orElseThrow(() -> new NoEntityException("cat not found")));
      return new ResponseEntity<>(cat, HttpStatus.OK);
    } catch (NoEntityException e) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }


  @PostMapping()
  public ResponseEntity<?> create(CatDto cat) {
    try {
      service.saveOrUpdate(mapper.toCat(cat));
      return new ResponseEntity<>(HttpStatus.CREATED);
    }
    catch (Exception e)
    {
      e.printStackTrace();
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }


  @PatchMapping("/{id}")
  public ResponseEntity<?> update(@PathVariable("id") Long id, CatDto catDto) {
    try {
      Cat cat = service.findById(id).orElse(null);
      if (cat != null) {
        catDto.setId(cat.getId());
        cat = mapper.toCat(catDto);
        service.saveOrUpdate(cat);
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
  public ResponseEntity<?> delete(@PathVariable("id") Long id) {
    Cat cat = service.findById(id).orElse(null);
    if (cat != null) {
      service.delete(cat);
      return new ResponseEntity<>(HttpStatus.OK);
    }
    else {
     return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

}
