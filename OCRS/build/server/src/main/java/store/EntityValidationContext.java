package store;

import java.util.List;

public interface EntityValidationContext {

	boolean hasErrors();

	// TODO: Change return type as per further requirement
	List<String> getErrors();

	void addFieldError(String field, String error);

	void addEntityError(String error);

	EntityValidationContext child(String field, String identity, long index);

}
