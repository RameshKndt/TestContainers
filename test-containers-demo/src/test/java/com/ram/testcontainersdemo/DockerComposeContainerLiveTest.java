package com.ram.testcontainersdemo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.wait.strategy.WaitStrategy;

public class DockerComposeContainerLiveTest {
    
	private DockerComposeContainer compose = null;
	@SuppressWarnings("resource")
	@BeforeEach
    public void init() {
		compose =  new DockerComposeContainer(
        new File("src/test/resources/test-compose.yml"))
          .withExposedService("simpleWebServer_1", 80)
          ;
		compose.start();
	}

    @Test
    public void givenSimpleWebServerContainer_whenGetReuqest_thenReturnsResponse()
      throws Exception {
        String address = "http://" + compose.getServiceHost("simpleWebServer_1", 80)
          + ":" + compose.getServicePort("simpleWebServer_1", 80);
        
        String response = simpleGetRequest(address);
        
        assertEquals(response, "Hello World!");
    }

    private String simpleGetRequest(String address) throws Exception {
        URL url = new URL(address);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();

        return content.toString();
    }
}
