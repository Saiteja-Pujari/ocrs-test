package store;

import java.util.function.Supplier;

public interface EntityHelper<T extends Object, I extends IEntityInput> {

	void setDefaults(T entity);

	void compute(T entity);

	void validate(T entity, EntityValidationContext context);

	void validateOnDelete(T entity, EntityValidationContext deletionContext);

	Boolean onCreate(T obj);

	Boolean onUpdate(T obj, T old);

	Boolean onDelete(T obj, EntityValidationContext deletionContext);

	T clone(T entity);

	T fromInput(I input, InputHelper helper);

	T fromInput(I input, T entity, InputHelper helper);

    T getById(long input);
    
    default Object newInstance() {
		return null;
	}

	default T getOld(long id) {
		return null;
	}

	default boolean union(Supplier<Boolean>... providers) {
		for (Supplier<Boolean> p : providers) {
			if (p.get()) {
				return true;
			}
		}
		return false;
	}

	default boolean intersect(Supplier<Boolean>... providers) {
		for (Supplier<Boolean> p : providers) {
			if (!p.get()) {
				return false;
			}
		}
		return true;
	}

	default boolean exclude(boolean from, boolean what) {
		if (what) {
			return false;
		}
		return from;
	}
}
