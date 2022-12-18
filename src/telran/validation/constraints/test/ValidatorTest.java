package telran.validation.constraints.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import telran.validation.constraints.*;

class ValidatorTest {

	@Test
	void validateTestWithCorrectData() {
		Person person = new Person(123, 1990, "Vasya", "Lincoln");
		assertTrue(Validator.validate(person).isEmpty());
	}
	
	@Test
	void validateTestWithViolationOfMin() {
		Person person = new Person(123, 1890, "Vasya", "Lincoln"); // year less than 1990
		List<String> expected = List.of("violation of the minimum limit - yearOfBirth");
		assertIterableEquals(expected, Validator.validate(person));
	}
	
	@Test
	void validateTestWithViolationOfMax() {
		Person person = new Person(123, 2023, "Vasya", "Lincoln"); // year greater than 2022
		List<String> expected = List.of("violation of the maximum limit - yearOfBirth");
		assertIterableEquals(expected, Validator.validate(person));
	}
	
	@Test
	void validateTestWithViolationOfPattern() {
		Person person = new Person(123, 1990, "1Vasya", "Lincoln"); // wrong name
		List<String> expected = List.of("Pattern constraint violation - name");
		assertIterableEquals(expected, Validator.validate(person));
	}
	
	@Test
	void validateTestWithEmptyString() {
		Person person = new Person(123, 1990, "", "Lincoln"); // empty string with name
		List<String> expected = List.of("Pattern constraint violation - name", "string must not be empty - name");
		assertIterableEquals(expected, Validator.validate(person));
	}
	
	@Test
	void validateTestWithNullString() {
		Person person = new Person(123, 1990, null, "Lincoln"); // null name
		List<String> expected = List.of("violation of the maximum limit - name", "Pattern constraint violation - name",
				"string must not be empty - name");
		assertIterableEquals(expected, Validator.validate(person));
	}
	
	@Test
	void validateTestWithWrongData() {
		// id less than 1, year greater than 2022, name of more than 10 letters (11), empty string with surname
		Person person = new Person(0, 2023, "Christopher", "");
		List<String> expected = List.of("violation of the minimum limit - id", "violation of the maximum limit - yearOfBirth",
				"violation of the maximum limit - name", "Pattern constraint violation - surname",
				"string must not be empty - surname");
		assertIterableEquals(expected, Validator.validate(person));
	}

}
