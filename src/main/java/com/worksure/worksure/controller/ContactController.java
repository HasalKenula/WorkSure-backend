package com.worksure.worksure.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.worksure.worksure.dto.ContactRequest;
import com.worksure.worksure.entity.Contact;
import com.worksure.worksure.entity.User;
import com.worksure.worksure.service.ContactService;
import com.worksure.worksure.service.UserService;

@RestController
@CrossOrigin(origins = "*")
public class ContactController {
    @Autowired
    public ContactService contactService;

    @Autowired
    private UserService userService;

    // @PostMapping("/contact")
    // public ResponseEntity<String> createContact(@RequestBody Contact contact) {

    //     if (contact != null) {
    //         contactService.createContact(contact);
    //         return ResponseEntity.status(200).body("Successfull");
    //     } else {
    //         return ResponseEntity.status(501).body("Not found");
    //     }
    // }

    @GetMapping("/contact")
    public ResponseEntity<List<Contact>> getAllContacts() {
        List<Contact> contact = contactService.getAllContacts();

        return ResponseEntity.status(200).body(contact);

    }


    @PostMapping("/contact")
public ResponseEntity<?> createContact(@RequestBody ContactRequest contactReqDTO) {
    try {
        // 1. Create new Contact object
        Contact newContact = new Contact();
        newContact.setName(contactReqDTO.getName());
        newContact.setContactNumber(contactReqDTO.getContactNumber());
        newContact.setSubject(contactReqDTO.getSubject());
        newContact.setMessage(contactReqDTO.getMessage());

        // 2. Fetch user from DB
        User user = userService.getUserById(contactReqDTO.getUserId());
        newContact.setUser(user);

        // 3. Save contact
        Contact savedContact = contactService.createContact(newContact);

        // 4. Return response
        return ResponseEntity.status(201).body(savedContact);

    } catch (Exception e) {
        return ResponseEntity.status(400).body("Error: " + e.getMessage());
    }
}

}
