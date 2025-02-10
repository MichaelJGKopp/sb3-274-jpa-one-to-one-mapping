package io.michaeljgkopp.github.cruddemo.dao;

import io.michaeljgkopp.github.cruddemo.entity.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstructorRepository extends JpaRepository<Instructor, Integer> {
}
