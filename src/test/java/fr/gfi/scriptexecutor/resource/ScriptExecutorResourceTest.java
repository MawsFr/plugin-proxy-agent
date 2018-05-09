package fr.gfi.scriptexecutor.resource;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;

import org.junit.Test;
import org.springframework.http.MediaType;

import fr.gfi.scriptexecutor.AbstractMvcTest;

public class ScriptExecutorResourceTest extends AbstractMvcTest {

	public static final String SCRIPT_EXECUTOR_PATH = "script-executor";

	// @Autowired
	// private ScriptExecutorResource resource;

	/**
	 * Tests a successfull script <br />
	 * - Message : test ok<br />
	 * - exitCode : 0
	 * 
	 * @throws Exception
	 */
	@Test
	public void executeSucess() throws Exception {
		this.mockMvc
				.perform(post("/testsuccess").contentType(MediaType.APPLICATION_JSON).content(json(new HashMap<>())))
				.andExpect(status().isOk()).andExpect(jsonPath("$.message").value("test ok"))
				.andExpect(jsonPath("$.exitCode").value("0"));

	}

	@Override
	protected void doInit() throws Exception {

	}

}
