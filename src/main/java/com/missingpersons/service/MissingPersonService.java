// backend/src/main/java/com/missingpersons/service/MissingPersonService.java
package com.missingpersons.service;

import com.missingpersons.model.MissingPerson;
import com.missingpersons.repository.MissingPersonRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MissingPersonService {
	@Autowired
    private  MissingPersonRepository repository;

    public List<MissingPerson> getAllPersons() {
        return repository.findAll();
    }

    public Optional<MissingPerson> getPersonById(Long id) {
        return repository.findById(id);
    }

    public MissingPerson createPerson(MissingPerson person) {
        // Generate case number if not provided
        if (person.getCaseNumber() == null || person.getCaseNumber().isEmpty()) {
            String caseNumber = "MP-" + System.currentTimeMillis();
            person.setCaseNumber(caseNumber);
        }
        return repository.save(person);
    }

    public MissingPerson updatePerson(Long id, MissingPerson personDetails) {
        MissingPerson person = repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Person not found with id: " + id));
        
        person.setName(personDetails.getName());
        person.setAge(personDetails.getAge());
        person.setLastSeen(personDetails.getLastSeen());
        person.setLocation(personDetails.getLocation());
        person.setDescription(personDetails.getDescription());
        person.setContact(personDetails.getContact());
        person.setStatus(personDetails.getStatus());
        person.setPhotoUrl(personDetails.getPhotoUrl());
        
        return repository.save(person);
    }

    public void deletePerson(Long id) {
        repository.deleteById(id);
    }

    public List<MissingPerson> searchPersons(String name, String location, MissingPerson.Status status) {
        Specification<MissingPerson> spec = Specification.allOf();
        
        if (name != null && !name.isEmpty()) {
            spec = spec.and((root, query, cb) -> 
                cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
        }
        
        if (location != null && !location.isEmpty()) {
            spec = spec.and((root, query, cb) -> 
                cb.like(cb.lower(root.get("location")), "%" + location.toLowerCase() + "%"));
        }
        
        if (status != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("status"), status));
        }
        
        return repository.findAll(spec);
    }

    public List<MissingPerson> searchByKeyword(String keyword) {
        return repository.findByKeyword(keyword);
    }

    public long getMissingCount() {
        return repository.countByStatus(MissingPerson.Status.MISSING);
    }

    public long getFoundCount() {
        return repository.countByStatus(MissingPerson.Status.FOUND);
    }
}