package telran.validation.constraints;

import telran.validation.constraints.annotations.*;

public class Person {

	@Min(1)
	@Max(999999999)
	private long id;
	
	@Min(1900)
	@Max(2022)
	private int yearOfBirth;
	
	@Max(10)
	@Pattern("[A-Z][a-z]*")
	@NotEmpty
	private String name;
	
	@Pattern("[A-Z][a-z]*")
	@NotEmpty
	private String surname;

	public Person(long id, int yearOfBirth, String name, String surname) {
		this.id = id;
		this.yearOfBirth = yearOfBirth;
		this.name = name;
		this.surname = surname;
	}	

}