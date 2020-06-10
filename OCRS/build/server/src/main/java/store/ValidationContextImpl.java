package store;

import java.util.List;

import d3e.core.ListExt;

public class ValidationContextImpl implements EntityValidationContext {
	private List<String> errors = ListExt.List();

	@Override
	public boolean hasErrors() {
		return ListExt.isNotEmpty(errors);
	}

	@Override
	public List<String> getErrors() {
		return errors;
	}

	@Override
	public void addFieldError(String field, String error) {
		errors.add(field + ": " + error);
	}

	@Override
	public void addEntityError(String error) {
		errors.add(error);
	}

	@Override
	public EntityValidationContext child(String field, String identity, long index) {
		return this;
	}

}
