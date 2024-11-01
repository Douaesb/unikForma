package com.unik.unikForma;

import com.unik.unikForma.dto.CourseDTO;
import com.unik.unikForma.entity.Course;
import com.unik.unikForma.entity.CourseStatus;
import com.unik.unikForma.exception.CourseNotFoundException;
import com.unik.unikForma.mapper.CourseMapper;
import com.unik.unikForma.repository.CourseRepository;
import com.unik.unikForma.service.CourseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private CourseMapper courseMapper;

    @InjectMocks
    private CourseService courseService;

    private CourseDTO courseDTO;
    private Course course;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialize a sample CourseDTO and Course entity
        courseDTO = new CourseDTO();
        courseDTO.setTitle("Sample Course");
        courseDTO.setLevel("Beginner");
        courseDTO.setMinCapacity(1);
        courseDTO.setMaxCapacity(30);
        courseDTO.setStartDate(LocalDate.now());
        courseDTO.setEndDate(LocalDate.now().plusDays(10));
        courseDTO.setStatus(CourseStatus.PLANNED);

        course = new Course();
        course.setId(1L);
        course.setTitle(courseDTO.getTitle());
        course.setLevel(courseDTO.getLevel());
        course.setMinCapacity(courseDTO.getMinCapacity());
        course.setMaxCapacity(courseDTO.getMaxCapacity());
        course.setStartDate(courseDTO.getStartDate());
        course.setEndDate(courseDTO.getEndDate());
        course.setStatus(courseDTO.getStatus());
    }

    @Test
    void saveCourse_ShouldSaveAndReturnCourseDTO() {
        when(courseMapper.toEntity(courseDTO)).thenReturn(course);
        when(courseRepository.save(course)).thenReturn(course);
        when(courseMapper.toDTO(course)).thenReturn(courseDTO);

        CourseDTO savedCourseDTO = courseService.saveCourse(courseDTO);

        assertNotNull(savedCourseDTO);
        assertEquals(courseDTO.getTitle(), savedCourseDTO.getTitle());
        verify(courseMapper).toEntity(courseDTO);
        verify(courseRepository).save(course);
        verify(courseMapper).toDTO(course);
    }

    @Test
    void getAllCourses_ShouldReturnListOfCourseDTOs() {
        when(courseRepository.findAll()).thenReturn(Arrays.asList(course));
        when(courseMapper.toDTO(course)).thenReturn(courseDTO);

        List<CourseDTO> courses = courseService.getAllCourses();

        assertEquals(1, courses.size());
        assertEquals(courseDTO.getTitle(), courses.get(0).getTitle());
        verify(courseRepository).findAll();
        verify(courseMapper).toDTO(course);
    }

    @Test
    void getCourseById_ShouldReturnCourseDTO_WhenCourseExists() {
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        when(courseMapper.toDTO(course)).thenReturn(courseDTO);

        CourseDTO foundCourseDTO = courseService.getCourseById(1L);

        assertNotNull(foundCourseDTO);
        assertEquals(courseDTO.getTitle(), foundCourseDTO.getTitle());
        verify(courseRepository).findById(1L);
        verify(courseMapper).toDTO(course);
    }

    @Test
    void getCourseById_ShouldThrowCourseNotFoundException_WhenCourseDoesNotExist() {
        when(courseRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(CourseNotFoundException.class, () -> courseService.getCourseById(1L));
        verify(courseRepository).findById(1L);
    }

    @Test
    void deleteCourse_ShouldDeleteCourse_WhenCourseExists() {
        when(courseRepository.existsById(1L)).thenReturn(true);

        assertDoesNotThrow(() -> courseService.deleteCourse(1L));
        verify(courseRepository).deleteById(1L);
    }

    @Test
    void deleteCourse_ShouldThrowCourseNotFoundException_WhenCourseDoesNotExist() {
        when(courseRepository.existsById(1L)).thenReturn(false);

        assertThrows(CourseNotFoundException.class, () -> courseService.deleteCourse(1L));
        verify(courseRepository, never()).deleteById(any());
    }

    @Test
    void updateCourse_ShouldUpdateAndReturnCourseDTO() {
        // Given
        CourseDTO courseDTO = new CourseDTO();
        courseDTO.setTitle("Updated Title");

        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        when(courseMapper.toEntity(courseDTO)).thenReturn(course);
        when(courseMapper.toDTO(course)).thenReturn(courseDTO);

        doNothing().when(courseMapper).updateEntityFromDTO(courseDTO, course);
        when(courseRepository.save(course)).thenReturn(course);

        CourseDTO updatedCourseDTO = courseService.updateCourse(1L, courseDTO);

        assertNotNull(updatedCourseDTO);
        assertEquals(courseDTO.getTitle(), updatedCourseDTO.getTitle());
        verify(courseRepository).findById(1L);
        verify(courseMapper).updateEntityFromDTO(courseDTO, course);
        verify(courseRepository).save(course);
        verify(courseMapper).toDTO(course);
    }

    @Test
    void updateCourse_ShouldThrowCourseNotFoundException_WhenCourseDoesNotExist() {
        when(courseRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(CourseNotFoundException.class, () -> courseService.updateCourse(1L, courseDTO));
        verify(courseRepository).findById(1L);
    }

    @Test
    void getCoursesByTitle_ShouldReturnListOfCourseDTOs() {
        when(courseRepository.findByTitle("Sample Course")).thenReturn(Arrays.asList(course));
        when(courseMapper.toDTO(course)).thenReturn(courseDTO);

        List<CourseDTO> courses = courseService.getCoursesByTitle("Sample Course");

        assertEquals(1, courses.size());
        assertEquals(courseDTO.getTitle(), courses.get(0).getTitle());
        verify(courseRepository).findByTitle("Sample Course");
    }

    @Test
    void getCoursesByStatus_ShouldReturnListOfCourseDTOs() {
        when(courseRepository.findByStatus(CourseStatus.PLANNED)).thenReturn(Arrays.asList(course));
        when(courseMapper.toDTO(course)).thenReturn(courseDTO);

        List<CourseDTO> courses = courseService.getCoursesByStatus("PLANNED");

        assertEquals(1, courses.size());
        assertEquals(courseDTO.getTitle(), courses.get(0).getTitle());
        verify(courseRepository).findByStatus(CourseStatus.PLANNED);
    }

    @Test
    void getCoursesByStatus_ShouldThrowIllegalArgumentException_WhenStatusIsInvalid() {
        assertThrows(IllegalArgumentException.class, () -> courseService.getCoursesByStatus("INVALID_STATUS"));
    }

    @Test
    void getCoursesByTitleAndLevel_ShouldReturnListOfCourseDTOs() {
        when(courseRepository.findByTitleAndLevel("Sample Course", "Beginner")).thenReturn(Arrays.asList(course));
        when(courseMapper.toDTO(course)).thenReturn(courseDTO);

        List<CourseDTO> courses = courseService.getCoursesByTitleAndLevel("Sample Course", "Beginner");

        assertEquals(1, courses.size());
        assertEquals(courseDTO.getTitle(), courses.get(0).getTitle());
        verify(courseRepository).findByTitleAndLevel("Sample Course", "Beginner");
    }
}
