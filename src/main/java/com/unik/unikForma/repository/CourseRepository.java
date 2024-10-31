package com.unik.unikForma.repository;

import com.unik.unikForma.entity.Course;
import com.unik.unikForma.entity.CourseStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    Optional<Course> findById(Long id);
    List<Course> findByTitle(String title);
    List<Course> findByTitleAndLevel(String title, String level);
    @Query("SELECT c FROM Course c WHERE c.status = :status")
    List<Course> findByStatus(@Param("status") CourseStatus status);


}
