package group52.comp3004.sockets;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Component
public class SocketHandler extends TextWebSocketHandler{
	
	
	@Override
	public void handleTextMessage(WebSocketSession session, TextMessage mes) throws Exception{
		Map payload = new Gson().fromJson(mes.getPayload(), Map.class);
		Map<String, String> message = new HashMap<>();
		message.put("event", "MESSAGE_RECEIVED");
		message.put("data", "You said " + payload.get("message"));
		Gson gson = new GsonBuilder().create();
		session.sendMessage(new TextMessage(gson.toJson(message)));
	}
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) {
		System.out.println("CONNECTED");
	}
}