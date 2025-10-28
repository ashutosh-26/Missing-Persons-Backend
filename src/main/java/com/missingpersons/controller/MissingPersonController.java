// backend/src/main/java/com/missingpersons/controller/MissingPersonController.java
package com.missingpersons.controller;

import com.missingpersons.model.MissingPerson;
import com.missingpersons.service.MissingPersonService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/persons")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class MissingPersonController {
	@Autowired
    private  MissingPersonService personService;

    @GetMapping
    public List<MissingPerson> getAllPersons() {
        return personService.getAllPersons();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MissingPerson> getPersonById(@PathVariable Long id) {
        return personService.getPersonById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<MissingPerson> createPerson(@Valid @RequestBody MissingPerson person) {
        try {
            MissingPerson savedPerson = personService.createPerson(person);
            return new ResponseEntity<>(savedPerson, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<MissingPerson> updatePerson(
            @PathVariable Long id, 
            @Valid @RequestBody MissingPerson personDetails) {
        try {
            MissingPerson updatedPerson = personService.updatePerson(id, personDetails);
            return ResponseEntity.ok(updatedPerson);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable Long id) {
        try {
            personService.deletePerson(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/search")
    public List<MissingPerson> searchPersons(
            @RequestParam(value = "name",required = false) String name,
            @RequestParam(value = "location" ,required = false) String location,
            @RequestParam(value = "status",required = false) MissingPerson.Status status) {
        return personService.searchPersons(name, location, status);
    }

    @GetMapping("/search/keyword")
    public List<MissingPerson> searchByKeyword(@RequestParam String keyword) {
        return personService.searchByKeyword(keyword);
    }

    @GetMapping("/stats")
    public Map<String, Object> getStatistics() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("missingCount", personService.getMissingCount());
        stats.put("foundCount", personService.getFoundCount());
        stats.put("totalCount", personService.getMissingCount() + personService.getFoundCount());
        return stats;
    }
}