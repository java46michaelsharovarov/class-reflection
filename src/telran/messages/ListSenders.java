package telran.messages;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class ListSenders {	

	public static String[] getSenders() {
		
		File folder = new File("src/telran/messages/senders");
		File[] listOfFiles = folder.listFiles();
		ArrayList<String> listSenders = new ArrayList<>();

		Arrays.stream(listOfFiles).forEach(file -> {
			if (file.isFile() && file.getName().matches(".+Sender.java")) {
				  listSenders.add(file.getName().replace(".java", ""));
			  }
		});
		return listSenders.toArray(new String[0]);
	}	
	
}