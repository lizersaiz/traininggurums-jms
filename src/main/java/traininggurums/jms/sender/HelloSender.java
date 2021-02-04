package traininggurums.jms.sender;

import java.util.UUID;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import traininggurums.jms.config.JmsConfig;
import traininggurums.jms.model.HelloWorldMessage;

@Component
@RequiredArgsConstructor
public class HelloSender {

	private final JmsTemplate jmsTemplate;
	private final ObjectMapper objectMapper;
	
	@Scheduled(fixedRate = 2000)
	public void sendMessage() {
		
		HelloWorldMessage message = HelloWorldMessage
				.builder()
				.id(UUID.randomUUID())
				.message("Hello World")
				.build();
		
		jmsTemplate.convertAndSend(JmsConfig.MY_QUEUE, message);
	}
	
	@Scheduled(fixedRate = 2000)
	public void sendAndReceiveMessage() throws JMSException {
		
		HelloWorldMessage message = HelloWorldMessage
				.builder()
				.id(UUID.randomUUID())
				.message("Hello")
				.build();

		Message receivedMessage = jmsTemplate.sendAndReceive(JmsConfig.MY_SEND_RCV_QUEUE, new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				Message helloMessage = null;
				try {
					
					helloMessage = session.createTextMessage(objectMapper.writeValueAsString(message));
					helloMessage.setStringProperty("_type", "traininggurums.jms.model.HelloWorldMessage");
					
					System.out.println("Sending hello");
					
					return helloMessage;
					
				} catch (JsonProcessingException e) {
					throw new JMSException("booom");
				}
			}
		});
		
		System.out.println(receivedMessage.getBody(String.class));
	}
}
