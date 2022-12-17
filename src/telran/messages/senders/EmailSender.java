package telran.messages.senders;

import telran.view.InputOutput;

public class EmailSender implements Sender {

	@Override
	public void send(InputOutput io, String message) {
		String emailAddress = io.readPredicate("Enter email address", "Wrong email", EmailSender::emailPredicate);
		System.out.printf("message: '%s' has been sent to email address: %s\n", message, emailAddress);
	}

	private static boolean emailPredicate(String email) {
		return email.matches(emailRegEx());
	}

	private static String emailRegEx() {
		String firstPart = "[^\\s,]+";
		String domain = "[a-zA-Z]+|[a-zA-Z]+-[a-zA-Z]+";
		String regex = String.format("%1$s@((%2$s)\\.){1,3}(%2$s)", firstPart, domain);
		return regex;
	}

}