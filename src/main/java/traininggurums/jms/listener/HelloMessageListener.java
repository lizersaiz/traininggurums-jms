package traininggurums.jms.listener;

import javax.jms.Message;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import traininggurums.jms.config.JmsConfig;
import traininggurums.jms.model.HelloWorldMessage;

@Component
public class HelloMessageListener {

	@JmsListener(destination = JmsConfig.MY_QUEUE)
	//helloWorldMessage will come serialized as a POJO, while message will come as json
	public void listen(@Payload HelloWorldMessage helloWorldMessage,
						@Headers MessageHeaders headers, Message message) {
		
		System.out.println("I got a message");
		System.out.println(helloWorldMessage);
		
		/*
		 * this would make HelloSender to resend the message because the client is throwing an exception,
		 * as it has transactional behaviour
		 */
		//throw new RuntimeException("foo");
	}
	
}
