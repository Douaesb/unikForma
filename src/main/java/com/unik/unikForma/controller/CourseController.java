package com.unik.unikForma.controller;

import com.unik.unikForma.dto.CourseDTO; // Import the CourseDTO
import com.unik.unikForma.service.CourseService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/courses")
public class CourseController {

    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    // Adjusted to use CourseDTO
    @PostMapping
    public ResponseEntity<CourseDTO> createCourse(@Valid @RequestBody CourseDTO courseDTO) {
        CourseDTO savedCourse = courseService.saveCourse(courseDTO);
        return ResponseEntity.ok(savedCourse);
    }

    // Adjusted to return List of CourseDTO
    @GetMapping
    public ResponseEntity<List<CourseDTO>> getAllCourses() {
        List<CourseDTO> courses = courseService.getAllCourses();
        return ResponseEntity.ok(courses);
    }

    // Adjusted to return CourseDTO
    @GetMapping("/{id}")
    public ResponseEntity<CourseDTO> getCourseById(@PathVariable Long id) {
        CourseDTO course = courseService.getCourseById(id);
        return ResponseEntity.ok(course);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<CourseDTO> update(
//            @PathVariable Long id, @Valid @RequestBody CourseDTO updatedCourseDTO) {
//        CourseDTO updatedCourse = courseService.updateCourse(id, updatedCourseDTO);
//        return ResponseEntity.ok(updatedCourse);
//    }
}
