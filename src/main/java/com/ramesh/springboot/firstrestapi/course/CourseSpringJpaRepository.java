package com.ramesh.springboot.firstrestapi.course;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseSpringJpaRepository extends JpaRepository<Course, Long> {
}
