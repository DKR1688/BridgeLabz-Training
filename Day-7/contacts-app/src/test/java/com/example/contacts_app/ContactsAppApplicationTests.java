package com.example.contacts_app;

import com.example.contacts_app.repository.ContactRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ContactsAppApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ContactRepository contactRepository;

	@BeforeEach
	void clearContacts() {
		contactRepository.deleteAll();
	}

	@Test
	void contextLoads() {
	}

	@Test
	void contactCrudAndErrorResponsesWork() throws Exception {
		String contact = """
				{"firstName":"Ada","lastName":"Lovelace","email":"ADA@EXAMPLE.COM", "phoneNumber":"+91 9876543210", "address":"London"}
				""";

		mockMvc.perform(post("/api/contacts").contentType(MediaType.APPLICATION_JSON).content(contact))
				.andExpect(status().isCreated())
				.andExpect(header().string("Location", org.hamcrest.Matchers.endsWith("/api/contacts/1")))
				.andExpect(jsonPath("$.email").value("ada@example.com"));

		mockMvc.perform(post("/api/contacts").contentType(MediaType.APPLICATION_JSON).content(contact))
				.andExpect(status().isConflict())
				.andExpect(jsonPath("$.fieldErrors.email").exists());

		mockMvc.perform(get("/api/contacts/1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.firstName").value("Ada"));

		String updatedContact = """
				{"firstName":"Augusta","lastName":"Lovelace","email":"ada@example.com", "phoneNumber":"+91 9876543210", "address":"London"}
				""";
		mockMvc.perform(put("/api/contacts/1").contentType(MediaType.APPLICATION_JSON).content(updatedContact))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.firstName").value("Augusta"));

		mockMvc.perform(get("/api/contacts/999"))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.status").value(404));

		mockMvc.perform(post("/api/contacts").contentType(MediaType.APPLICATION_JSON)
				.content("{\"firstName\":\"\",\"lastName\":\"Lovelace\",\"email\":\"invalid\",\"phoneNumber\":\"1\"}"))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.fieldErrors.firstName").exists());

		mockMvc.perform(delete("/api/contacts/1"))
				.andExpect(status().isNoContent());
	}

}
