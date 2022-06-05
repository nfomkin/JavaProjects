package ru.itmo.nfomkin.controller;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.itmo.nfomkin.dto.cat.CatCreationDto;
import ru.itmo.nfomkin.dto.cat.CatDto;
import ru.itmo.nfomkin.entity.Cat;
import ru.itmo.nfomkin.exception.NoEntityException;
import ru.itmo.nfomkin.mapper.cat.CatMapper;
import ru.itmo.nfomkin.service.CatService;

@Controller
@RequestMapping("/cats")
public class CatController {
  private final CatService service;
  private final CatMapper mapper;

  public CatController(CatService service, CatMapper mapper){

    this.service = service;
    this.mapper = mapper;
  }

  @GetMapping()
  public String index(Model model) {
    List<CatDto> catDtos = service.findAll().stream()
            .map(mapper::toDto).toList();
    model.addAttribute("cats", catDtos);
    return "cats/index";
  }


  @GetMapping("/{id}")
  public String show(@PathVariable("id") Long id, Model model) {
      try {
        Cat cat = service.findById(id).orElseThrow(() -> new NoEntityException(String.format("Cat with id = %d not found", id)));
        CatDto catDto = mapper.toDto(cat);
        model.addAttribute("cat", catDto);
        return "cats/show";
      } catch (NoEntityException e) {
        model.addAttribute("message", e.getMessage());
        return "error";
      }
  }

  @GetMapping("/new")
  public String newCat(Model model) {
    model.addAttribute("cat", new CatCreationDto());
    return "cats/new";
  }

  @PostMapping()
  public String create(@ModelAttribute("cat") CatCreationDto catCreationDto) {
    service.saveOrUpdate(mapper.toCat(catCreationDto));
    return "redirect:/cats";
  }

  @GetMapping("/{id}/edit")
  public String edit(Model model, @PathVariable("id") Long id)
  {
    try {
      Cat cat = service.findById(id).orElseThrow(
          () -> new NoEntityException(String.format("Cat with id = %d not found", id))
      );
      CatCreationDto catCreationDto = mapper.toCreationDto(cat);
      model.addAttribute("cat", catCreationDto);
      return "cats/edit";
    } catch (NoEntityException e) {
        model.addAttribute("message", e.getMessage());
        return "error";
    }
  }

  @PatchMapping("/{id}")
  public String update(@ModelAttribute("cat") CatCreationDto catCreationDto) {
    Cat cat = mapper.toCat(catCreationDto);
    service.saveOrUpdate(cat);
    return "redirect:cats";
  }

}
