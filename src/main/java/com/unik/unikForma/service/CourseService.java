package com.unik.unikForma.service;

import com.unik.unikForma.dto.CourseDTO;
import com.unik.unikForma.entity.Course;
import com.unik.unikForma.entity.CourseStatus;
import com.unik.unikForma.exception.CourseNotFoundException;
import com.unik.unikForma.mapper.CourseMapper; // Import the CourseMapper
import com.unik.unikForma.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Validated
public class CourseService {

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    @Autowired
    public CourseService(CourseRepository courseRepository, CourseMapper courseMapper) {
        this.courseRepository = courseRepository;
        this.courseMapper = courseMapper;
    }

    public CourseDTO saveCourse(@Valid CourseDTO courseDTO) {
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

    public CourseDTO updateCourse(Long id, @Valid CourseDTO updatedCourseDTO) {
        Course existingCourse = courseRepository.findById(id)
                .orElseThrow(() -> new CourseNotFoundException(id));
        courseMapper.updateEntityFromDTO(updatedCourseDTO, existingCourse);
        Course updatedCourse = courseRepository.save(existingCourse);
        return courseMapper.toDTO(updatedCourse);
    }


    public List<CourseDTO> getCoursesByTitle(String title) {
        return courseRepository.findByTitle(title).stream()
                .map(courseMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<CourseDTO> getCoursesByStatus(String status) {
        try {
            CourseStatus courseStatus = CourseStatus.valueOf(status.toUpperCase());
            return courseRepository.findByStatus(courseStatus)
                    .stream()
                    .map(courseMapper::toDTO)
                    .collect(Collectors.toList());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid course status: " + status);
        }
    }


    public List<CourseDTO> getCoursesByTitleAndLevel(String title, String level) {
        return courseRepository.findByTitleAndLevel(title, level).stream()
                .map(courseMapper::toDTO)
                .collect(Collectors.toList());
    }
}
