package com.paymybuddy.paymybuddy;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;


@SpringBootTest
@ActiveProfiles("test")
//@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)

class PaymybuddyApplicationTests {

	@Test
	void contextLoads() {
	}

}
