package fr.gfi.scriptexecutor.resource;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.util.HashMap;

import org.junit.Test;
import org.springframework.http.MediaType;

import fr.gfi.scriptexecutor.AbstractMvcTest;

public class ScriptExecutorResourceTest extends AbstractMvcTest {

	public static final String SCRIPT_EXECUTOR_PATH = "script-executor";

	/**
	 * Tests a successfull script <br />
	 * - Message : test ok<br />
	 * - exitCode : 0
	 * 
	 * @throws IOException
	 * 
	 * @throws Exception
	 */
	@Test
	public void executeSucess() throws IOException, Exception {
		this.mockMvc
				.perform(post("/testsuccess").contentType(MediaType.APPLICATION_JSON).content(json(new HashMap<>())))
				.andExpect(status().isOk()).andExpect(jsonPath("$.message").value("test ok"))
				.andExpect(jsonPath("$.exitCode").value("0")).andExpect(jsonPath("$.messageKey").value("test-ok"));
	}

	@Test
	public void executeFail() throws IOException, Exception {
		this.mockMvc.perform(post("/testfail").contentType(MediaType.APPLICATION_JSON).content(json(new HashMap<>())))
				.andExpect(status().isOk()).andExpect(jsonPath("$.message").value("test fail"))
				.andExpect(jsonPath("$.exitCode").value("1")).andExpect(jsonPath("$.messageKey").value("test-fail"));
	}

	@Override
	protected void doInit() throws Exception {

	}

}
