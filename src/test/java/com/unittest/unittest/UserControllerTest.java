package com.unittest.unittest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.unittest.unittest.controllers.UserController;
import com.unittest.unittest.entities.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(value = "test")
class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;

	private ObjectMapper objectMapper = new ObjectMapper();

	@Autowired
	UserController userController;

	@Test
	void contextLoads() {
		assertThat(userController).isNotNull();
	}

	private User getPlayerFromId(Long id) throws Exception {
		MvcResult result = this.mockMvc.perform(get("/user" + id))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn();

		try {
			String playerJSON = result.getResponse().getContentAsString();
			User user = objectMapper.readValue(playerJSON, User.class);

			assertThat(user).isNotNull();
			assertThat(user.getId()).isNotNull();

			return user;
		} catch (Exception e) {
			return null;
		}
	}

	private User createAUser() throws Exception {
		User user = new User();
		user.setName("Samuele");
		user.setSurname("Catalano");


		return createAUser(user);
	}

	private User createAUser(User user) throws Exception {
		MvcResult result = createAUserRequest(user);
		User userFromResponse = objectMapper.readValue(result.getResponse().getContentAsString(), User.class);

		assertThat(userFromResponse).isNotNull();
		assertThat(userFromResponse.getId()).isNotNull();

		return userFromResponse;
	}

	private MvcResult createAUserRequest(User user) throws Exception {
		if (user == null) return null;
		String playerJSON = objectMapper.writeValueAsString(user);

		return this.mockMvc.perform(post("/user")
						.contentType(MediaType.APPLICATION_JSON)
						.content(playerJSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn();
	}

	@Test
	void createAUserTest() throws Exception {
		try{
			createAUser();
		}catch (MismatchedInputException e){
			e.printStackTrace();
		}

	}

	@Test
	void readUserList() throws Exception {
		try{
			createAUserTest();

			MvcResult result = this.mockMvc.perform(get("/user"))
					.andDo(print())
					.andExpect(status().isOk())
					.andReturn();

			List<User> userResponse = objectMapper.readValue(result.getResponse().getContentAsString(), List.class);
			assertThat(userResponse.size()).isNotNull();
		}catch (MismatchedInputException e){
			e.printStackTrace();
		}

	}

	@Test
	void readSinglePlayer() throws Exception {
		try{
			createAUser();
		}catch (MismatchedInputException e){
			e.printStackTrace();
		}

	}

	@Test
	void updatePlayer() throws Exception {
		try{
			User user = createAUser();

			String newName = "Gionni";
			user.setName(newName);

			String userJSON = objectMapper.writeValueAsString(user);

			MvcResult result = this.mockMvc.perform(put("/user" + user.getId())
							.contentType(MediaType.APPLICATION_JSON)
							.content(userJSON))
					.andDo(print())
					.andExpect(status().isOk())
					.andReturn();
			User userResponse = objectMapper.readValue(result.getResponse().getContentAsString(), User.class);

			assertThat(userResponse.getId()).isEqualTo(user.getId());
			assertThat(userResponse.getName()).isEqualTo(newName);
		}catch (MismatchedInputException e){
			e.printStackTrace();
		}

	}

	@Test
	void deleteUser() throws Exception {
		try{
			User user = createAUser();
			assertThat(user.getId()).isNotNull();

			this.mockMvc.perform(delete("/user" + user.getId()))
					.andDo(print())
					.andExpect(status().isOk())
					.andReturn();

			User userResponse = getPlayerFromId(user.getId());
			assertThat(userResponse).isNull();
		}catch (MismatchedInputException e){
			e.printStackTrace();
		}

	}

}
