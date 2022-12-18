package telran.validation.constraints;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.*;

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
		List<String> listOfErrorsByField = new ArrayList<>();
		Annotation[] annotations = field.getDeclaredAnnotations();
		Arrays.stream(annotations).forEach(annotation -> {
			String annotationTypeName = annotation.annotationType().getSimpleName();
			try {
				Method method = ValidatorMethods.class.getDeclaredMethod(annotationTypeName, Field.class, Object.class);
				String res = (String) method.invoke(null, field, obj);
				if (!res.equals("")) {
					listOfErrorsByField.add(res + " - " + field.getName());
				}
			} catch (Exception e) {
				e.getMessage();
			}
		});
		return listOfErrorsByField;
	}

}
