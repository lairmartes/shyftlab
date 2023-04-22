package com.gmail.lairmartes.shyftlab.course.rest;

import com.gmail.lairmartes.shyftlab.course.domain.Course;
import com.gmail.lairmartes.shyftlab.course.service.CourseService;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

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

    }

    @Test
    void listAllCourses() {
    }
}