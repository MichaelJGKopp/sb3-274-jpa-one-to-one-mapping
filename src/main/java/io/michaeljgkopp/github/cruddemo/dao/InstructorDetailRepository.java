package io.michaeljgkopp.github.cruddemo.dao;

import io.michaeljgkopp.github.cruddemo.entity.InstructorDetail;
import org.springframework.data.jpa.repository.JpaRepository;

// for bidirectional mapping
public interface InstructorDetailRepository extends JpaRepository<InstructorDetail, Integer> {
}
