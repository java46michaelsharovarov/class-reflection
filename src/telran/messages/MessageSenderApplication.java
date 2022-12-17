package telran.messages;

import java.util.ArrayList;

import telran.view.ConsoleInputOutput;
import telran.view.Item;
import telran.view.Menu;

public class MessageSenderApplication {

	public static void main(String[] args) throws Exception {
		ArrayList<Item> items = MessageSenderMenu.getMessageSenderItems();
		Menu menu = new Menu("Message senders", 4, items);
		menu.perform(new ConsoleInputOutput());
	}
}