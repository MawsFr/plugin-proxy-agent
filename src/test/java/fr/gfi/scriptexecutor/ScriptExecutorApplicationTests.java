package fr.gfi.scriptexecutor;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(ScriptExecutorApplicationTests.TEST_PROFILE)
public class ScriptExecutorApplicationTests {

	public static final String TEST_PROFILE = "test";

	@Test
	public void contextLoads() {
	}

}
