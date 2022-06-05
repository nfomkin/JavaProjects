package ru.itmo.nfomkin.controller;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.itmo.nfomkin.entity.Owner;
import ru.itmo.nfomkin.service.OwnerService;


@Controller
@RequestMapping("/owners")
public class OwnerController {

  private OwnerService service;
  public OwnerController(OwnerService service) {
    this.service = service;
  }

  @GetMapping()
  public List<Owner> index() {
    return service.findAll();
  }

  @GetMapping("/new")
  public String newOwner(Model model) {
    model.addAttribute("owner", new Owner());
    return "owners/new";
  }

  @PostMapping()
  public String create(@ModelAttribute("owner") Owner owner) {
    service.saveOrUpdate(owner);
    return "redirect:/owners";
  }

}
