package edu.pnu.socket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@Configuration


public class WebSocketConfiguration {
	@Bean
	ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}
