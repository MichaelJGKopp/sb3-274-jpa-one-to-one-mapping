package io.michaeljgkopp.github.cruddemo.dao;

import io.michaeljgkopp.github.cruddemo.entity.Instructor;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface InstructorRepository extends JpaRepository<Instructor, Integer> {

    // for testing purposes reset auto increment
    @Modifying
    @Transactional
    @Query(value = "ALTER TABLE instructor AUTO_INCREMENT = 1", nativeQuery = true)
    void resetAutoIncrement();
}
