package edu.wisc.hr.dm.prefname;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class PreferredNameValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return PreferredName.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "error.required");
		
		PreferredName pn = (PreferredName)target;
		
		if(!StringUtils.isEmpty(pn.getFirstName()) && pn.getFirstName().length() > 30) {
			errors.rejectValue("firstName", "error.toolong");
		}
		
		if(!StringUtils.isEmpty(pn.getMiddleName()) && pn.getMiddleName().length() > 30) {
			errors.rejectValue("middleName", "error.toolong");
		}
		
		final String regx = "^[A-Za-z .-]*$";
		Pattern ptrn = Pattern.compile(regx);
		Matcher fnameMatcher = ptrn.matcher(pn.getFirstName());
		Matcher mnameMatcher = ptrn.matcher(pn.getMiddleName());
		
		if(!fnameMatcher.find() || !mnameMatcher.find()) {
			errors.rejectValue("firstName", "error.invalidCharacter");
		}
	}
}
