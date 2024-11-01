package com.unik.unikForma;

import com.unik.unikForma.dto.CourseDTO;
import com.unik.unikForma.entity.CourseStatus;
import com.unik.unikForma.exception.CourseNotFoundException;
import com.unik.unikForma.mapper.CourseMapper;
import com.unik.unikForma.repository.CourseRepository;
import com.unik.unikForma.service.CourseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("development")
public class CourseServiceIntegrationTest {

    @Autowired
    private CourseService courseService;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CourseMapper courseMapper;

    private CourseDTO testCourseDTO;

    @BeforeEach
    public void setUp() {
        // Initialize a CourseDTO object to be used in tests
        testCourseDTO = new CourseDTO();
        testCourseDTO.setTitle("Test Course");
        testCourseDTO.setLevel("Beginner");
        testCourseDTO.setPrerequisites("None");
        testCourseDTO.setMinCapacity(1);
        testCourseDTO.setMaxCapacity(50);
        testCourseDTO.setStartDate(LocalDate.now());
        testCourseDTO.setEndDate(LocalDate.now().plusMonths(1));
        testCourseDTO.setStatus(CourseStatus.PLANNED);
    }

    @Test
    public void testSaveCourse() {
        CourseDTO savedCourse = courseService.saveCourse(testCourseDTO);

        assertNotNull(savedCourse.getId());
        assertEquals(testCourseDTO.getTitle(), savedCourse.getTitle());
        assertEquals(testCourseDTO.getLevel(), savedCourse.getLevel());
        assertEquals(testCourseDTO.getPrerequisites(), savedCourse.getPrerequisites());
        assertEquals(testCourseDTO.getMinCapacity(), savedCourse.getMinCapacity());
        assertEquals(testCourseDTO.getMaxCapacity(), savedCourse.getMaxCapacity());
        assertEquals(testCourseDTO.getStartDate(), savedCourse.getStartDate());
        assertEquals(testCourseDTO.getEndDate(), savedCourse.getEndDate());
        assertEquals(testCourseDTO.getStatus(), savedCourse.getStatus());
    }

    @Test
    public void testGetAllCourses() {
        courseService.saveCourse(testCourseDTO);
        List<CourseDTO> courses = courseService.getAllCourses();

        assertFalse(courses.isEmpty());
        assertEquals(1, courses.size());
        assertEquals(testCourseDTO.getTitle(), courses.get(0).getTitle());
    }

    @Test
    public void testGetCourseById() {
        CourseDTO savedCourse = courseService.saveCourse(testCourseDTO);
        Long courseId = savedCourse.getId();

        CourseDTO retrievedCourse = courseService.getCourseById(courseId);
        assertEquals(savedCourse.getId(), retrievedCourse.getId());
    }

    @Test
    public void testDeleteCourse() {
        CourseDTO savedCourse = courseService.saveCourse(testCourseDTO);
        Long courseId = savedCourse.getId();

        courseService.deleteCourse(courseId);
        assertThrows(CourseNotFoundException.class, () -> courseService.getCourseById(courseId));
    }

    @Test
    public void testUpdateCourse() {
        CourseDTO savedCourse = courseService.saveCourse(testCourseDTO);
        Long courseId = savedCourse.getId();

        CourseDTO updatedCourseDTO = new CourseDTO();
        updatedCourseDTO.setTitle("Updated Course");
        updatedCourseDTO.setLevel("Intermediate");
        updatedCourseDTO.setPrerequisites("Basic knowledge required");
        updatedCourseDTO.setMinCapacity(10);
        updatedCourseDTO.setMaxCapacity(100);
        updatedCourseDTO.setStartDate(LocalDate.now().plusDays(1));
        updatedCourseDTO.setEndDate(LocalDate.now().plusMonths(2));
        updatedCourseDTO.setStatus(CourseStatus.CANCELLED);

        CourseDTO updatedCourse = courseService.updateCourse(courseId, updatedCourseDTO);
        assertEquals("Updated Course", updatedCourse.getTitle());
        assertEquals("Intermediate", updatedCourse.getLevel());
        assertEquals("Basic knowledge required", updatedCourse.getPrerequisites());
        assertEquals(10, updatedCourse.getMinCapacity());
        assertEquals(100, updatedCourse.getMaxCapacity());
        assertEquals(LocalDate.now().plusDays(1), updatedCourse.getStartDate());
        assertEquals(LocalDate.now().plusMonths(2), updatedCourse.getEndDate());
        assertEquals(CourseStatus.CANCELLED, updatedCourse.getStatus());
    }

    @Test
    public void testGetCoursesByTitle() {
        courseService.saveCourse(testCourseDTO);
        List<CourseDTO> courses = courseService.getCoursesByTitle("Test Course");

        assertEquals(1, courses.size());
        assertEquals(testCourseDTO.getTitle(), courses.get(0).getTitle());
    }

    @Test
    public void testGetCoursesByStatus() {
        courseService.saveCourse(testCourseDTO);
        List<CourseDTO> courses = courseService.getCoursesByStatus("PLANNED");

        assertEquals(1, courses.size());
        assertEquals(testCourseDTO.getStatus(), courses.get(0).getStatus());
    }

    @Test
    public void testGetCoursesByTitleAndLevel() {
        courseService.saveCourse(testCourseDTO);
        List<CourseDTO> courses = courseService.getCoursesByTitleAndLevel("Test Course", "Beginner");

        assertEquals(1, courses.size());
        assertEquals(testCourseDTO.getTitle(), courses.get(0).getTitle());
        assertEquals(testCourseDTO.getLevel(), courses.get(0).getLevel());
    }
}
