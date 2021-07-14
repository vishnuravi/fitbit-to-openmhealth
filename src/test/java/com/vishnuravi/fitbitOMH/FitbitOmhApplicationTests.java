package com.vishnuravi.fitbitOMH;

import com.vishnuravi.fitbitOMH.controller.HeartRateController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class FitbitOmhApplicationTests {

	@Autowired
	private HeartRateController heartRateController;

	@Test
	public void contextLoads() throws Exception {
		assertThat(heartRateController).isNotNull();
	}

}
