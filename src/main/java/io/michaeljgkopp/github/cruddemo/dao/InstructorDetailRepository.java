package io.michaeljgkopp.github.cruddemo.dao;

import io.michaeljgkopp.github.cruddemo.entity.InstructorDetail;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

// for bidirectional mapping
public interface InstructorDetailRepository extends JpaRepository<InstructorDetail, Integer> {

    // for testing purposes reset auto increment
    @Modifying
    @Transactional
    @Query(value = "ALTER TABLE instructor_detail AUTO_INCREMENT = 1", nativeQuery = true)
    void resetAutoIncrement();
}
