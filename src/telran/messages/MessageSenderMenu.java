package telran.messages;

import java.util.ArrayList;
import java.util.Arrays;
import telran.messages.senders.Sender;
import telran.view.Item;

public class MessageSenderMenu {
	
	private static final String[] listOfSenderNames = ListSenders.getSenders();

	public static ArrayList<Item> getMessageSenderItems() {
		ArrayList<Item> menuItems = new ArrayList<>();
		Arrays.stream(listOfSenderNames).forEach(senderName -> {
			Item item = Item.of(senderName.replace("Sender", ""), io -> {
				Sender sender = SenderBuilder.getSenderByName(senderName);
				String message = io.readString("Enter text");
				sender.send(io, message);
			});
			menuItems.add(item);
		});
		menuItems.add(Item.exit());
		return menuItems;
	}

}
