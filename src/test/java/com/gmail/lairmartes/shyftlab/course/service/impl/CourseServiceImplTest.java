package com.gmail.lairmartes.shyftlab.course.service.impl;

import com.gmail.lairmartes.shyftlab.common.exception.RecordNotFoundException;
import com.gmail.lairmartes.shyftlab.course.domain.Course;
import com.gmail.lairmartes.shyftlab.course.repository.CourseRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class CourseServiceImplTest {

    @MockBean
    private CourseRepository courseRepository;

    @Autowired
    private CourseServiceImpl courseService;

    @Nested
    class AddCourse {

        @Test
        void whenCourseDataAreValid_thenSavesCourse() {

            final Course validCourse = Course.builder().name("Course Name").build();

            final com.gmail.lairmartes.shyftlab.course.entity.Course mockEntityResult =
                    com.gmail.lairmartes.shyftlab.course.entity.Course
                            .builder().id(32309L)
                            .name("Course Name")
                            .build();

            final Course expectedResult = Course.builder().id(32309L).name("Course Name").build();

            when(courseRepository.save(validCourse.toEntity())).thenReturn(mockEntityResult);

            final Course actualResult = courseService.addCourse(validCourse);

            verify(courseRepository, times(1)).save(validCourse.toEntity());

            assertEquals(expectedResult, actualResult);
        }

        @Test
        void whenFieldsAreNotProvided_theValidatesFields_andThrowsConstraintViolationException() {

            final Course invalidCourse = Course.builder().build();

            final List<String> expectedMessages = List.of("Provide a name for the course.");

            final ConstraintViolationException exception = assertThrows(ConstraintViolationException.class,
                    () -> courseService.addCourse(invalidCourse));

            assertFalse(exception.getConstraintViolations().isEmpty());
            assertTrue(exception.getConstraintViolations()
                    .stream()
                    .map(ConstraintViolation::getMessage)
                    .toList()
                    .containsAll(expectedMessages));

            verify(courseRepository, never()).save(any());
        }
    }

    @Nested
    class FindById {

        @Test
        void whenExists_thenCallsRepository_andReturnsObject() {

            final var repositoryResponse = com.gmail.lairmartes.shyftlab.course.entity.Course
                    .builder().id(15L).name("Course Name").build();

            when(courseRepository.findById(15L)).thenReturn(Optional.of(repositoryResponse));

            final var expectedCourse = Course.builder().id(15L).name("Course Name").build();

            assertEquals(expectedCourse, courseService.findById(15L));

            verify(courseRepository, times(1)).findById(15L);
        }

        @Test
        void whenDoesNotExist_theThrowsException_andCorrectErrorMessage() {
            when(courseRepository.findById(anyLong())).thenThrow(new RecordNotFoundException("Course", 342432L));

            final var exception = assertThrows(RecordNotFoundException.class,
                    () -> courseRepository.findById(342432L));

            assertEquals("Course with id 342432 not found.", exception.getMessage());

            verify(courseRepository, times(1)).findById(342432L);
        }
    }

    @Test
    void listAllCourses() {

        courseService.listAllCourses();

        verify(courseRepository, times(1)).findAll();
    }

    @Test
    void removeCourse() {

        courseService.removeCourseById(2389832L);

        verify(courseRepository, times(1)).deleteById(2389832L);
    }
}