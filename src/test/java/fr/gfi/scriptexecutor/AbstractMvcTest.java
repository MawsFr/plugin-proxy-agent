package fr.gfi.scriptexecutor;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import fr.gfi.scriptexecutor.model.ShellScript;
import fr.gfi.scriptexecutor.service.ScriptProvider;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringBootTest(classes = ProxyAgentApplication.class)
@ActiveProfiles(ScriptExecutorApplicationTests.TEST_PROFILE)
@Ignore
public abstract class AbstractMvcTest {
	private static final String TEST_SUCCESS = "testsuccess";
	private static final String TEST_FAIL = "testfail";

	protected MockMvc mockMvc;

	private final ObjectMapper mapper = new ObjectMapper();
	private static Set<Class> inited = new HashSet<>();

	@Autowired
	private WebApplicationContext wac;

	@Autowired
	private ScriptProvider provider;

	@Before
	public void setup() {
		// this.mockMvc =
		// webAppContextSetup(this.wac).apply(SecurityMockMvcConfigurers.springSecurity()).build();
		this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
		provider.addScript(new ShellScript(TEST_SUCCESS));
		provider.addScript(new ShellScript(TEST_FAIL));
	}

	@Before
	public void init() throws Exception {
		if (!inited.contains(getClass())) {
			doInit();
			inited.add(getClass());
		}
	}

	protected abstract void doInit() throws Exception;

	protected String json(final Object o) throws IOException {
		return mapper.writeValueAsString(o);
	}

}