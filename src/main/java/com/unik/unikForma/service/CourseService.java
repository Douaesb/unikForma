package com.unik.unikForma.service;

import com.unik.unikForma.entity.Course;
import com.unik.unikForma.exception.CourseNotFoundException;
import com.unik.unikForma.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public Course saveCourse(Course course) {
        return courseRepository.save(course);
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Optional<Course> getCourseById(Long id) {
        return courseRepository.findById(id);
    }

    public void deleteCourse(Long id) {
        if (!courseRepository.existsById(id)) {
            throw new CourseNotFoundException(id);
        }
        courseRepository.deleteById(id);
    }
//    public Page<Course> findAllCourses(Pageable pageable) {
//        return courseRepository.findAll(pageable);
//    }

    public Optional<Course> updateCourse(Long id, Course updatedCourse) {
        return Optional.ofNullable(courseRepository.findById(id).map(course -> {
            course.setTitle(updatedCourse.getTitle());
            course.setLevel(updatedCourse.getLevel());
            course.setPrerequisites(updatedCourse.getPrerequisites());
            course.setMinCapacity(updatedCourse.getMinCapacity());
            course.setMaxCapacity(updatedCourse.getMaxCapacity());
            course.setStartDate(updatedCourse.getStartDate());
            course.setEndDate(updatedCourse.getEndDate());
            course.setStatus(updatedCourse.getStatus());
            return courseRepository.save(course);
        }).orElseThrow(() -> new CourseNotFoundException(id)));
    }
}
