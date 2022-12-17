package telran.messages;

import java.lang.reflect.Constructor;

import telran.messages.senders.Sender;

public class SenderBuilder {

	private static final String BASE_PACKAGE = "telran.messages.senders.";

	public static Sender getSenderByName(String messageType) {
		try {
			@SuppressWarnings("unchecked")
			Class<Sender> clazz = (Class<Sender>) Class.forName(BASE_PACKAGE + messageType);
			Constructor<Sender> constructor = clazz.getConstructor();
			return constructor.newInstance();
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Wrong message type ");
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}
	
}
