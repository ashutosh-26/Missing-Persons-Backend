// backend/src/main/java/com/missingpersons/repository/MissingPersonRepository.java
package com.missingpersons.repository;

import com.missingpersons.model.MissingPerson;
import com.missingpersons.model.MissingPerson.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MissingPersonRepository 
    extends JpaRepository<MissingPerson, Long>, JpaSpecificationExecutor<MissingPerson> {
    
    List<MissingPerson> findByStatus(Status status);
    List<MissingPerson> findByNameContainingIgnoreCase(String name);
    List<MissingPerson> findByLocationContainingIgnoreCase(String location);
    
    @Query("SELECT p FROM MissingPerson p WHERE " +
           "LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(p.location) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(p.description) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<MissingPerson> findByKeyword(@Param("keyword") String keyword);
    
    List<MissingPerson> findByLastSeenAfter(LocalDate date);
    List<MissingPerson> findByLastSeenBefore(LocalDate date);
    
    long countByStatus(Status status);
}