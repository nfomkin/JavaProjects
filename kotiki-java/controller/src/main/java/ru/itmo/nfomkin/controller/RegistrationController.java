//package ru.itmo.nfomkin.controller;
//
//import javax.validation.Valid;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import ru.itmo.nfomkin.RegistrationForm;
//import ru.itmo.nfomkin.service.OwnerService;
//
//@Controller
//@RequestMapping("/registration")
//public class RegistrationController {
//  private OwnerService service;
//  private PasswordEncoder encoder;
//
//  public RegistrationController(PasswordEncoder encoder, OwnerService service) {
//    this.encoder = encoder;
//    this.service = service;
//  }
//
//  @GetMapping
//  public String registrationForm(Model model) {
//    model.addAttribute("registrationForm", new RegistrationForm());
//    return "registration";
//  }
//
//  @PostMapping
//  public String registration(@Valid @ModelAttribute RegistrationForm form, BindingResult bindingResult) {
//    if (bindingResult.hasErrors()) {
//      return "registration";
//    }
//    service.saveOrUpdate(form.toOwner(encoder));
//    return "redirect:/successfulRegistration";
//  }
//}
