package com.worksure.worksure.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.worksure.worksure.entity.Contact;
import com.worksure.worksure.service.ContactService;

@RestController
@CrossOrigin(origins = "*")
public class ContactController {
    @Autowired
    public ContactService contactService;

    @PostMapping("/contact")
    public ResponseEntity<String> createContact(@RequestBody Contact contact) {

        if (contact != null) {
            contactService.createContact(contact);
            return ResponseEntity.status(200).body("Successfull");
        } else {
            return ResponseEntity.status(501).body("Not found");
        }
    }

    @GetMapping("/contact")
    public ResponseEntity<List<Contact>> getAllContacts() {
        List<Contact> contact = contactService.getAllContacts();

        return ResponseEntity.status(200).body(contact);

    }
}
