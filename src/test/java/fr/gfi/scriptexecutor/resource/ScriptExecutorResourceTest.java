package fr.gfi.scriptexecutor.resource;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;

import org.junit.Test;
import org.springframework.http.MediaType;

import fr.gfi.scriptexecutor.AbstractMvcTest;
import fr.gfi.scriptexecutor.model.ScriptContext;

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
		final ScriptContext context = new ScriptContext();
		context.setScriptId("testsuccess");
		this.mockMvc.perform(post("").contentType(MediaType.APPLICATION_JSON).content(json(context)))
				.andExpect(status().isOk()).andExpect(jsonPath("$.message").value("test ok"))
				.andExpect(jsonPath("$.exitCode").value("0")).andExpect(jsonPath("$.messageKey").value("test-ok"));
	}

	@Test
	public void executeFail() throws IOException, Exception {
		final ScriptContext context = new ScriptContext();
		context.setScriptId("testfail");
		this.mockMvc.perform(post("").contentType(MediaType.APPLICATION_JSON).content(json(context)))
				.andExpect(status().isOk()).andExpect(jsonPath("$.message").value("test failed"))
				.andExpect(jsonPath("$.exitCode").value("1")).andExpect(jsonPath("$.messageKey").value("test-fail"));
	}

	@Override
	protected void doInit() throws Exception {

	}

}
