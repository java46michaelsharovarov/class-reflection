package telran.validation.constraints;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Validator {

	public static List<String> validate(Object obj) {
		List<String> listOfErrors = new ArrayList<>();
		Field[] fields = obj.getClass().getDeclaredFields();
		Arrays.stream(fields).forEach(field -> {
			field.setAccessible(true);
			listOfErrors.addAll(getlistOfErrorsByField(field, obj));
		});
		return listOfErrors;
	}

	private static List<String> getlistOfErrorsByField(Field field, Object obj) {
		List<String> listOfErrors = new ArrayList<>();
		Annotation[] annotations = field.getDeclaredAnnotations();
		String annotationTypeName;
		for (Annotation annotation : annotations) {
			annotationTypeName = getAnnotationTypeName(annotation);
			try {
				Method method = Validator.class.getDeclaredMethod(annotationTypeName, Field.class, Object.class);
				method.setAccessible(true);
				String res = (String) method.invoke(null, field, obj);
				if (!res.equals("")) {
					listOfErrors.add(res + " - " + field.getName());
				}
			} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		return listOfErrors;
	}

	private static String getAnnotationTypeName(Annotation annotation) {
		String annatationClassName = annotation.annotationType().getName();
		int indexBeforeName = annatationClassName.lastIndexOf(".");
		return annatationClassName.substring(indexBeforeName + 1);
	}

	@SuppressWarnings("unused")
	private static String Min(Field field, Object obj) {
		Class<?> typeOfField = field.getType();
		String res = "";
		Min min = field.getAnnotation(Min.class);
		try {
			if (typeOfField.isAssignableFrom(String.class)) {
				String fieldValue = (String) field.get(obj);
				if (fieldValue == null || fieldValue.length() < min.value()) {
					res = min.message();
				}
			} else if (typeOfField.isPrimitive()) {
				Number fieldValue = (Number) field.get(obj);
				if (fieldValue.doubleValue() < min.value()) {
					res = min.message();
				}
			}
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return res;
	}

	@SuppressWarnings("unused")
	private static String Max(Field field, Object obj) {
		Class<?> typeOfField = field.getType();
		String res = "";
		Max max = field.getAnnotation(Max.class);
		try {
			if (typeOfField.isAssignableFrom(String.class)) {
				String fieldValue = (String) field.get(obj);
				if (fieldValue == null || fieldValue.length() > max.value()) {
					res = max.message();
				}
			} else if (typeOfField.isPrimitive()) {
				Number fieldValue = (Number) field.get(obj);
				if (fieldValue.doubleValue() > max.value()) {
					res = max.message();
				}
			}
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return res;
	}

	@SuppressWarnings("unused")
	private static String Pattern(Field field, Object obj) {
		String res = "";
		Pattern pattern = field.getAnnotation(Pattern.class);
		if (pattern != null) {
			try {
				String strValue = (String) field.get(obj);
				String regEx = pattern.value();
				if (strValue == null || !strValue.matches(regEx)) {
					res = pattern.message();
				}
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return res;
	}

	@SuppressWarnings("unused")
	private static String NotEmpty(Field field, Object obj) {
		String res = "";
		NotEmpty notEmpty = field.getAnnotation(NotEmpty.class);
		try {
			String fieldValue = (String) field.get(obj);
			if (fieldValue == null || fieldValue.isEmpty()) {
				res = notEmpty.message();
			}
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return res;
	}

}
