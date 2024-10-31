package com.unik.unikForma.service;

import com.unik.unikForma.dto.CourseDTO;
import com.unik.unikForma.entity.Course;
import com.unik.unikForma.exception.CourseNotFoundException;
import com.unik.unikForma.mapper.CourseMapper; // Import the CourseMapper
import com.unik.unikForma.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    @Autowired
    public CourseService(CourseRepository courseRepository, CourseMapper courseMapper) {
        this.courseRepository = courseRepository;
        this.courseMapper = courseMapper;
    }

    public CourseDTO saveCourse(CourseDTO courseDTO) {
        Course course = courseMapper.toEntity(courseDTO);
        Course savedCourse = courseRepository.save(course);
        return courseMapper.toDTO(savedCourse);
    }

    public List<CourseDTO> getAllCourses() {
        return courseRepository.findAll()
                .stream()
                .map(courseMapper::toDTO)
                .collect(Collectors.toList());
    }

    public CourseDTO getCourseById(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new CourseNotFoundException(id));
        return courseMapper.toDTO(course);
    }

    public void deleteCourse(Long id) {
        if (!courseRepository.existsById(id)) {
            throw new CourseNotFoundException(id);
        }
        courseRepository.deleteById(id);
    }

    // Update Course from DTO
//    public CourseDTO updateCourse(Long id, CourseDTO updatedCourseDTO) {
//        return courseRepository.findById(id).map(course -> {
//            // Use the mapper to update the existing course with values from the DTO
//            courseMapper.updateEntityFromDTO(updatedCourseDTO, course); // Assuming you have an update method
//            Course updatedCourse = courseRepository.save(course);
//            return courseMapper.toDTO(updatedCourse); // Use mapper to convert Entity to DTO
//        }).orElseThrow(() -> new CourseNotFoundException(id));
//    }
}
