package com.gmail.lairmartes.shyftlab.student.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(controllers = StudentController.class)
class StudentControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;


}