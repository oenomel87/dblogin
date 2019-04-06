package dev.oenomel.guide.dblogin;

import dev.oenomel.guide.dblogin.model.MyUser;
import dev.oenomel.guide.dblogin.repository.MyUserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@Transactional
@AutoConfigureMockMvc
@SpringBootTest
public class DBLoginApplicationTests {

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	MyUserRepository repository;

	@Autowired
	MockMvc mockMvc;

	@Before
	public void setUp() {
		MyUser myUser = new MyUser();
		myUser.setUsername("tester");
		myUser.setName("Tester");
		myUser.setRole("TESTER");
		myUser.setPassword(encoder.encode("test123"));
		repository.save(myUser);
	}

	@Test
	public void authenticationTest() throws Exception {
		MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
		param.add("username", "tester");
		param.add("password", "test123");
		mockMvc.perform(post("/login").params(param))
			.andExpect(status().isOk());
	}

	@Test
	public void authenticationFailTest() throws Exception {
		MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
		param.add("username", "tester");
		param.add("password", "test");
		mockMvc.perform(post("/login").params(param))
				.andExpect(status().is3xxRedirection());
	}
}
