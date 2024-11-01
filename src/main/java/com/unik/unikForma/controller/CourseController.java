package com.unik.unikForma.controller;

import com.unik.unikForma.dto.CourseDTO;
import com.unik.unikForma.service.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {

    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping
    @Operation(summary = "Create a new course")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Course created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid course data")
    })
    public ResponseEntity<CourseDTO> createCourse(
            @Parameter(description = "Course details for creation", required = true) @Valid @RequestBody CourseDTO courseDTO) {
        CourseDTO savedCourse = courseService.saveCourse(courseDTO);
        return ResponseEntity.ok(savedCourse);
    }

    @GetMapping
    @Operation(summary = "Retrieve all courses")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Courses retrieved successfully")
    })
    public ResponseEntity<List<CourseDTO>> getAllCourses() {
        List<CourseDTO> courses = courseService.getAllCourses();
        return ResponseEntity.ok(courses);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get course by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Course retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Course not found")
    })
    public ResponseEntity<CourseDTO> getCourseById(
            @Parameter(description = "ID of the course to retrieve", required = true) @PathVariable Long id) {
        CourseDTO course = courseService.getCourseById(id);
        return ResponseEntity.ok(course);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a course by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Course deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Course not found")
    })
    public ResponseEntity<Void> deleteCourse(
            @Parameter(description = "ID of the course to delete", required = true) @PathVariable Long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a course by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Course updated successfully"),
            @ApiResponse(responseCode = "404", description = "Course not found"),
            @ApiResponse(responseCode = "400", description = "Invalid course data")
    })
    public ResponseEntity<CourseDTO> updateCourse(
            @Parameter(description = "ID of the course to update", required = true) @PathVariable Long id,
            @Parameter(description = "Updated course details", required = true) @Valid @RequestBody CourseDTO updatedCourseDTO) {
        CourseDTO updatedCourse = courseService.updateCourse(id, updatedCourseDTO);
        return ResponseEntity.ok(updatedCourse);
    }

    @GetMapping("/search/title")
    @Operation(summary = "Search courses by title")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Courses retrieved successfully")
    })
    public ResponseEntity<List<CourseDTO>> getCoursesByTitle(
            @Parameter(description = "Title of the course to search for", required = true) @RequestParam String title) {
        List<CourseDTO> courses = courseService.getCoursesByTitle(title);
        return ResponseEntity.ok(courses);
    }

    @GetMapping("/search/status")
    @Operation(summary = "Search courses by status")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Courses retrieved successfully")
    })
    public ResponseEntity<List<CourseDTO>> getCoursesByStatus(
            @Parameter(description = "Status of the course to search for", required = true) @RequestParam String status) {
        List<CourseDTO> courses = courseService.getCoursesByStatus(status);
        return ResponseEntity.ok(courses);
    }

    @GetMapping("/search/titleAndLevel")
    @Operation(summary = "Search courses by title and level")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Courses retrieved successfully")
    })
    public ResponseEntity<List<CourseDTO>> getCoursesByTitleAndLevel(
            @Parameter(description = "Title of the course to search for", required = true) @RequestParam String title,
            @Parameter(description = "Level of the course to search for", required = true) @RequestParam String level) {
        List<CourseDTO> courses = courseService.getCoursesByTitleAndLevel(title, level);
        return ResponseEntity.ok(courses);
    }
}
