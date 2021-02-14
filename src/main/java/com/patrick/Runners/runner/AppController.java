package com.patrick.Runners.runner;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/list_contact")
public class AppController {

  @GetMapping
  public String listContact(Model model) {

    ContactBusiness business = new ContactBusiness();
    List<Contact> contactList = business.getContactList();

    model.addAttribute("contacts", contactList);

    return "contact";
  }
}
