package com.worksure.worksure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.worksure.worksure.entity.Contact;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
    
}
