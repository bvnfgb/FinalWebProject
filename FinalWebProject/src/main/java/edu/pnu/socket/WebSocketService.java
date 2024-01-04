package edu.pnu.socket;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.websocket.CloseReason;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;

@Service
@ServerEndpoint("/user/socket/chatt")
public class WebSocketService {
    private static Set<Session> clients = Collections.synchronizedSet(new HashSet<Session>());
    private static Logger logger = LoggerFactory.getLogger(WebSocketService.class);

    @OnOpen
    public void onOpen(Session session) {
        logger.info("open session : {}, clients={}", session.toString(), clients);
        Map<String, List<String>> res = session.getRequestParameterMap();
        logger.info("res={}", res);
        
        if(!clients.contains(session)) {
            clients.add(session);
            logger.info("session open : {}", session);
        }else{
            logger.info("이미 연결된 session");
        }
    }
   
    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        logger.info("receive message : {}", message);
        if(message.compareTo("close")==0) {
        	session.close(new CloseReason(CloseReason.CloseCodes.NORMAL_CLOSURE, "client's request"));
        }
        for (Session s : clients) {
            logger.info("send data : {}", message);
            s.getBasicRemote().sendText(message);
        }
    }

    @OnClose
    public void onClose(Session session) {
        logger.info("session close : {}", session);
        clients.remove(session);
    }
    public int sendTest(HashMap<String, String> jsonObject) {
    	
    	ObjectMapper objectMapper = new ObjectMapper();
    	
    	
    		
    	for(Session s:clients) {
    		try {
    			String jsonString=objectMapper.writeValueAsString(jsonObject);
				s.getBasicRemote().sendText(jsonString);
			} catch (IOException e) {
				e.printStackTrace();
				return 1;
			}
    	}
    	return 0;
    }
}