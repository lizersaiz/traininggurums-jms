package traininggurums.jms.model;

import java.io.Serializable;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HelloWorldMessage implements Serializable {

	// Message POJOS need to be serializabled
	private static final long serialVersionUID = -4167051474433252519L;
	
	private UUID id;
	private String message;
}
