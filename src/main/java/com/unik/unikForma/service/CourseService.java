package com.unik.unikForma.service;

import com.unik.unikForma.dto.CourseDTO;
import com.unik.unikForma.entity.Course;
import com.unik.unikForma.exception.CourseNotFoundException;
import com.unik.unikForma.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    // Save Course from DTO
    public CourseDTO saveCourse(CourseDTO courseDTO) {
        Course course = convertToEntity(courseDTO);
        Course savedCourse = courseRepository.save(course);
        return convertToDTO(savedCourse);
    }

    // Get all Courses as DTOs
    public List<CourseDTO> getAllCourses() {
        return courseRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Get Course by ID as DTO
    public CourseDTO getCourseById(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new CourseNotFoundException(id));
        return convertToDTO(course);
    }

    // Delete Course by ID
    public void deleteCourse(Long id) {
        if (!courseRepository.existsById(id)) {
            throw new CourseNotFoundException(id);
        }
        courseRepository.deleteById(id);
    }

    // Update Course from DTO
    public CourseDTO updateCourse(Long id, CourseDTO updatedCourseDTO) {
        return courseRepository.findById(id).map(course -> {
            // Update the existing course with the values from the DTO
            course.setTitle(updatedCourseDTO.getTitle());
            course.setLevel(updatedCourseDTO.getLevel());
            course.setPrerequisites(updatedCourseDTO.getPrerequisites());
            course.setMinCapacity(updatedCourseDTO.getMinCapacity());
            course.setMaxCapacity(updatedCourseDTO.getMaxCapacity());
            course.setStartDate(updatedCourseDTO.getStartDate());
            course.setEndDate(updatedCourseDTO.getEndDate());
            course.setStatus(updatedCourseDTO.getStatus()); // Set the status from DTO
            Course updatedCourse = courseRepository.save(course);
            return convertToDTO(updatedCourse);
        }).orElseThrow(() -> new CourseNotFoundException(id));
    }

    // Convert Course to CourseDTO
    private CourseDTO convertToDTO(Course course) {
        return new CourseDTO(course.getId(), course.getTitle(), course.getLevel(),
                course.getPrerequisites(), course.getMinCapacity(), course.getMaxCapacity(),
                course.getStartDate(), course.getEndDate(), course.getStatus());
    }

    // Convert CourseDTO to Course
    private Course convertToEntity(CourseDTO courseDTO) {
        return new Course(courseDTO.getId(), courseDTO.getTitle(), courseDTO.getLevel(),
                courseDTO.getPrerequisites(), courseDTO.getMinCapacity(), courseDTO.getMaxCapacity(),
                courseDTO.getStartDate(), courseDTO.getEndDate(), courseDTO.getStatus(), null);
    }
}
