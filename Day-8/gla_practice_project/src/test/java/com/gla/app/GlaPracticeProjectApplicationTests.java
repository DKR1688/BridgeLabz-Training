package com.gla.app;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.gla.app.repository.StudentRepository;

@SpringBootTest
@AutoConfigureMockMvc
class GlaPracticeProjectApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private StudentRepository studentRepository;

	@BeforeEach
	void clearDatabase() {
		studentRepository.deleteAll();
	}

	@Test
	void contextLoads() {
	}

	@Test
	void completeCrudLifecycle() throws Exception {
		String student = """
				{ "rollNumber": "GLA-101", "firstName": "Asha", "lastName": "Sharma",
				  "gender": "Female", "email": "asha@example.com", "city": "Mathura",
				  "state": "Uttar Pradesh", "course": "BTech", "department": "CSE",
				  "admissionYear": 2026 }
				""";

		String created = mockMvc.perform(post("/student").contentType(MediaType.APPLICATION_JSON).content(student))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.studentId").isNumber())
				.andExpect(jsonPath("$.firstName").value("Asha"))
				.andReturn().getResponse().getContentAsString();
		int id = com.jayway.jsonpath.JsonPath.read(created, "$.studentId");

		mockMvc.perform(get("/student/{id}", id))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.rollNumber").value("GLA-101"));
		mockMvc.perform(get("/student"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].studentId").value(id));

		String updated = student.replace("Asha", "Ananya").replace("GLA-101", "GLA-102").replace("asha@example.com", "ananya@example.com");
		mockMvc.perform(put("/student/{id}", id).contentType(MediaType.APPLICATION_JSON).content(updated))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.firstName").value("Ananya"));
		mockMvc.perform(delete("/student/{id}", id)).andExpect(status().isNoContent());
		mockMvc.perform(get("/student/{id}", id)).andExpect(status().isNotFound());
	}

	@Test
	void rejectsInvalidStudent() throws Exception {
		mockMvc.perform(post("/student").contentType(MediaType.APPLICATION_JSON)
				.content("{\"rollNumber\":\"\",\"firstName\":\"Asha\",\"gender\":\"Other\"}"))
				.andExpect(status().isBadRequest());
	}

}
