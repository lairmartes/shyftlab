package com.gmail.lairmartes.shyftlab.course.rest;

import com.gmail.lairmartes.shyftlab.course.domain.Course;
import com.gmail.lairmartes.shyftlab.course.service.CourseService;
import com.gmail.lairmartes.shyftlab.util.TestUtilConstraintViolationBuilder;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CourseController.class)
class CourseControllerTest {

    @MockBean
    private CourseService courseService;

    @Autowired
    private MockMvc mvc;

    @Nested
    class AddCourse {

        @Test
        void whenDataAreValid_thenSavesCourse_andReturnCourseAdded() throws Exception {

            final Course courseRequest = Course.builder().name("Course Name").build();
            final Course courseResponse = Course.builder().id(3989423L).name("Course Name").build();

            when(courseService.addCourse(courseRequest)).thenReturn(courseResponse);

            final String expectedResponse = "{ 'id': 3989423, 'name':'Course Name' }";

            final String addCourseBody = "{ \"name\":\"Course Name\" }";

            var mvcResult = mvc.perform(post("/courses/")
                    .contentType("application/json")
                    .content(addCourseBody))
                    .andExpect(status().isOk())
                    .andReturn();

            verify(courseService, times(1)).addCourse(courseRequest);

            JSONAssert.assertEquals(expectedResponse, mvcResult.getResponse().getContentAsString(), JSONCompareMode.STRICT);
        }

        @Test
        void whenDataIsNotValid_thenCallService_andReturns400ErrorWithErrorMessages() throws Exception {

            final Set<ConstraintViolation<String>> violationList = Set.of(
                    TestUtilConstraintViolationBuilder.buildConstraintViolation("Validation Message 1"),
                    TestUtilConstraintViolationBuilder.buildConstraintViolation("Validation Message 2")
            );

            when(courseService.addCourse(any(Course.class))).thenThrow(new ConstraintViolationException(violationList));

            var mvcResult = mvc.perform(post("/courses/")
                            .contentType("application/json")
                            .content("{ }"))
                    .andExpect(status().is4xxClientError())
                    .andReturn();

            verify(courseService, times(1)).addCourse(any(Course.class));

            final String responseContent = mvcResult.getResponse().getContentAsString();

            assertNotNull(responseContent);
            assertTrue(responseContent.contains("Validation Message 1"));
            assertTrue(responseContent.contains("Validation Message 2"));
        }

    }

    @Nested
    class ListAllCourses{
        @Test
        void whenReturnsData_theReturnsExpectedJson() throws Exception {

            when(courseService.listAllCourses()).thenReturn(List.of(
                    Course.builder().id(234904L).name("Course 1").build(),
                    Course.builder().id(839289L).name("Course 2").build()
            ));

            var mvcResult = mvc.perform(get("/courses/all"))
                    .andExpect(status().isOk())
                    .andReturn();

            final var expectedResponse = "[ { 'id':234904, 'name':'Course 1' }, { 'id':839289, 'name':'Course 2' } ]";

            verify(courseService, times(1)).listAllCourses();

            JSONAssert.assertEquals(expectedResponse, mvcResult.getResponse().getContentAsString(), JSONCompareMode.STRICT);
        }

        @Test
        void whenListIsEmpty_thenReturnsEmptyJsonList() throws Exception {

            when(courseService.listAllCourses()).thenReturn(Collections.emptyList());

            var mvcResult = mvc.perform(get("/courses/all"))
                    .andExpect(status().isOk())
                    .andReturn();

            final var expectedResponse = "[ ]";

            verify(courseService, times(1)).listAllCourses();

            JSONAssert.assertEquals(expectedResponse, mvcResult.getResponse().getContentAsString(), JSONCompareMode.STRICT);
        }
    }


    @Nested
    class RemoveCourseById {
        @Test
        void whenProvidesId_thenCallsRemoveService() throws Exception {

            mvc.perform(delete("/courses/32748"))
                    .andExpect(status().isOk());

            verify(courseService, times(1)).removeCourseById(32748L);
        }

        @Test
        void whenDoesNotProvideId_thenReturns400Error_andDoesNotCallService() throws Exception {

            mvc.perform(delete("/students/"))
                    .andExpect(status().is4xxClientError());

            verify(courseService, times(0)).removeCourseById(anyLong());
        }
    }
}