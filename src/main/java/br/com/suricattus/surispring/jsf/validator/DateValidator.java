package br.com.suricattus.surispring.jsf.validator;

import java.util.Calendar;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import br.com.suricattus.surispring.jsf.util.FacesUtils;

@FacesValidator("br.com.suricattus.surispring.jsf.validator.DateValidator")
public class DateValidator implements Validator{

	private static final String BEFORE_THEN_ATTR = "beforeThen";
	private static final String BEFORE_THEN_MESSAGE_ATTR = "beforeThenMessage";

	private static final String AFTER_THEN_ATTR = "afterThen";
	private static final String AFTER_THEN_MESSAGE_ATTR = "afterThenMessage";
	
	
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		if(value == null) return;
		
		Date date = (Date)value;
		
		String beforeThen = (String) component.getAttributes().get(BEFORE_THEN_ATTR);
		if(beforeThen != null && !isValid(component, date, beforeThen, true)){
			String message = (String)component.getAttributes().get(BEFORE_THEN_MESSAGE_ATTR);
			if(message != null && !message.trim().equals("")){
				throw new ValidatorException(FacesUtils.createMessage(FacesMessage.SEVERITY_ERROR, message));
			}
			
			String label = (String)component.getAttributes().get("label");
			if(label != null && !label.trim().equals("")){
				throw new ValidatorException(FacesUtils.createMessage(FacesMessage.SEVERITY_ERROR, "javax.faces.validator.Date.detail", label));
			}else{
				throw new ValidatorException(FacesUtils.createMessage(FacesMessage.SEVERITY_ERROR, "javax.faces.validator.Date"));
			}
		}
		
		String afterThen = (String) component.getAttributes().get(AFTER_THEN_ATTR);
		if(afterThen != null && !isValid(component, date, afterThen, false)){
			String message = (String)component.getAttributes().get(AFTER_THEN_MESSAGE_ATTR);
			if(message != null && !message.trim().equals("")){
				throw new ValidatorException(FacesUtils.createMessage(FacesMessage.SEVERITY_ERROR, message));
			}
			
			String label = (String)component.getAttributes().get("label");
			if(label != null && !label.trim().equals("")){
				throw new ValidatorException(FacesUtils.createMessage(FacesMessage.SEVERITY_ERROR, "javax.faces.validator.Date.detail", label));
			}else{
				throw new ValidatorException(FacesUtils.createMessage(FacesMessage.SEVERITY_ERROR, "javax.faces.validator.Date"));
			}
		}
		
		Calendar sqlServerEarlier = Calendar.getInstance();
		Calendar sqlServerAfter = Calendar.getInstance();
		sqlServerEarlier.set(1752,11, 31, 23, 59);
		sqlServerAfter.set(9999, 11, 31, 23, 59);
		if (date.before(sqlServerEarlier.getTime()) || date.after(sqlServerAfter.getTime())) {
			String label = (String)component.getAttributes().get("label");
			if(label != null && !label.trim().equals("")){
				throw new ValidatorException(FacesUtils.createMessage(FacesMessage.SEVERITY_ERROR, "javax.faces.validator.Date.detail", label));
			}else{
				throw new ValidatorException(FacesUtils.createMessage(FacesMessage.SEVERITY_ERROR, "javax.faces.validator.Date"));
			}
		}
	}
	
	private boolean isValid(UIComponent component, Date value, String then, boolean before){
		
		Date other = null;
		
		if(then.equalsIgnoreCase("today") || then.equalsIgnoreCase("tomorrow") || then.equalsIgnoreCase("yesterday")){
			
			Calendar calendar = Calendar.getInstance();
			if(before){
				calendar.set(Calendar.HOUR_OF_DAY, 0);
				calendar.set(Calendar.MINUTE, 0);
				calendar.set(Calendar.SECOND, 0);
				calendar.set(Calendar.MILLISECOND, 0);
			}else{
				calendar.set(Calendar.HOUR_OF_DAY, 23);
				calendar.set(Calendar.MINUTE, 59);
				calendar.set(Calendar.SECOND, 59);
				calendar.set(Calendar.MILLISECOND, 999);
			}
			
			if(then.equalsIgnoreCase("tomorrow")) calendar.add(Calendar.DAY_OF_MONTH, 1);
			else if(then.equalsIgnoreCase("yesterday")) calendar.add(Calendar.DAY_OF_MONTH, -1);
			
			other = calendar.getTime();
			
		}else{
			EditableValueHolder otherComponent = ((EditableValueHolder)component.findComponent(then));
			other = otherComponent.isValid() ? (Date)otherComponent.getLocalValue() : null;
		}
		
		if(other == null) return true;
		
		if(before) return value.before(other);
		else return value.after(other);
	}
	
}
