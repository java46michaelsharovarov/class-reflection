package telran.validation.constraints;

import java.lang.reflect.Field;

import telran.validation.constraints.annotations.*;

public class ValidatorMethods {

	public static String validateMin(Field field, Object obj) {
		Class<?> typeOfField = field.getType();
		String res = "";
		Min min = field.getAnnotation(Min.class);
		try {
			if (typeOfField.isAssignableFrom(String.class)) {
				res = checkMinForString(field, obj, min);
			} else if (typeOfField.isPrimitive()) {
				res = checkMinForPrimitive(field, obj, min);
			}
		} catch (Exception e) {
			e.getMessage();
		}
		return res;
	}

	private static String checkMinForPrimitive(Field field, Object obj, Min min) throws IllegalAccessException {
		String res;
		Number fieldValue = (Number) field.get(obj);
		res = fieldValue.doubleValue() < min.value() ? min.message() : "";
		return res;
	}

	private static String checkMinForString(Field field, Object obj, Min min) throws IllegalAccessException {
		String res;
		String fieldValue = (String) field.get(obj);
		res = fieldValue == null || fieldValue.length() < min.value() ? min.message() : "";
		return res;
	}

	public static String validateMax(Field field, Object obj) {
		Class<?> typeOfField = field.getType();
		String res = "";
		Max max = field.getAnnotation(Max.class);
		try {
			if (typeOfField.isAssignableFrom(String.class)) {
				res = checkMaxForString(field, obj, max);
			} else if (typeOfField.isPrimitive()) {
				res = checkMaxForPrimitive(field, obj, max);
			}
		} catch (Exception e) {
			e.getMessage();
		}
		return res;
	}

	private static String checkMaxForPrimitive(Field field, Object obj, Max max)
			throws IllegalAccessException {
		Number fieldValue = (Number) field.get(obj);
		return fieldValue.doubleValue() > max.value() ? max.message() : "";
	}

	private static String checkMaxForString(Field field, Object obj, Max max)
			throws IllegalAccessException {
		String fieldValue = (String) field.get(obj);
		return fieldValue == null || fieldValue.length() > max.value() ? max.message() : "";
	}

	public static String validatePattern(Field field, Object obj) {
		String res = "";
		Pattern pattern = field.getAnnotation(Pattern.class);
		if (pattern == null) {
			return res;
		}
		try {
			String strValue = (String) field.get(obj);
			String regEx = pattern.value();
			res = strValue == null || !strValue.matches(regEx) ? pattern.message() : res;
		} catch (Exception e) {
			e.getMessage();
		}
		return res;
	}

	public static String validateNotEmpty(Field field, Object obj) {
		String res = "";
		NotEmpty notEmpty = field.getAnnotation(NotEmpty.class);
		try {
			String fieldValue = (String) field.get(obj);
			res = fieldValue == null || fieldValue.isEmpty() ? notEmpty.message() : res;
		} catch (Exception e) {
			e.getMessage();
		}
		return res;
	}
}
