package com.worksure.worksure.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.worksure.worksure.entity.Contact;

@Service
public interface ContactService {
    Contact createContact(Contact contact);
    List<Contact> getAllContacts();
}
