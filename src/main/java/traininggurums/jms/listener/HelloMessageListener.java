package traininggurums.jms.listener;

import java.util.UUID;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;

import org.springframework.jms.JmsException;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import traininggurums.jms.config.JmsConfig;
import traininggurums.jms.model.HelloWorldMessage;

@RequiredArgsConstructor
@Component
public class HelloMessageListener {

	private final JmsTemplate jmsTemplate;
	
	@JmsListener(destination = JmsConfig.MY_QUEUE)
	//helloWorldMessage will come serialized as a POJO, while message will come as json
	public void listen(@Payload HelloWorldMessage helloWorldMessage,
						@Headers MessageHeaders headers, Message message) {
		
		//System.out.println("I got a message");
		//System.out.println(helloWorldMessage);
		
		/*
		 * this would make HelloSender to resend the message because the client is throwing an exception,
		 * as it has transactional behaviour
		 */
		//throw new RuntimeException("foo");
	}
	
	@JmsListener(destination = JmsConfig.MY_SEND_RCV_QUEUE)
	//helloWorldMessage will come serialized as a POJO, while message will come as json
	public void listenToHello(@Payload HelloWorldMessage helloWorldMessage,
						@Headers MessageHeaders headers, Message message) throws JMSException {
		
		HelloWorldMessage payloadMsg = HelloWorldMessage
				.builder()
				.id(UUID.randomUUID())
				.message("World!")
				.build();
		
		//destination is the place where the message came from
		jmsTemplate.convertAndSend(message.getJMSReplyTo(), payloadMsg);
	}
}
