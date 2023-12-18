package edu.pnu;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
class FinalWebProjectApplicationTests {
	@MockBean
    private RestTemplate restTemplate;
	@Test
	public void dowork() throws Exception{
		 
	}
	
}
