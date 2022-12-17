package telran.messages.senders;

import telran.view.InputOutput;

public interface Sender {
	
	void send(InputOutput io, String message);
	
}