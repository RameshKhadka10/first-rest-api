package com.ramesh.springboot.firstrestapi.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CourseCommandLineRunner implements CommandLineRunner {

    @Autowired
    private CourseJdbcRepository courseJdbcRepository;

    @Autowired
    private CourseJpaRepository courseJpaRepository;

    @Autowired
    private CourseSpringJpaRepository courseSpringJpaRepository;

    @Override
    public void run(String... args) throws Exception {

        //spring JDBC example
        //courseJdbcRepository.insert(new Course(1, "AWS", "Ramesh Khadka"));
        //System.out.println(courseJdbcRepository.findById(1));

        //JPA Example
        /*courseJpaRepository.insert(new Course(1, "AWS", "Ramesh Khadka"));
        System.out.println(courseJpaRepository.findById(1));*/

        //Spring JPA Example
        courseSpringJpaRepository.save(new Course(1, "React", "Ramesh Khadka"));
        System.out.println(courseSpringJpaRepository.findById(1L));

     }
}
