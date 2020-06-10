package store;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import graphql.input.ObjectRef;
import models.CreatableObject;

public class InputHelperImpl implements InputHelper {

	private Map<String, Object> data;
	private Map<Long, DatabaseObject> inputObjectCache;
	private String currentField;
	private Iterator<Map<String, Object>> currentColl;
	private final EntityMutator mutator;

	public InputHelperImpl(EntityMutator mutator, Map<String, Object> data) {
		this(mutator, data, new HashMap<>());
	}

	public InputHelperImpl(EntityMutator mutator, Map<String, Object> data,
			Map<Long, DatabaseObject> inputObjectCache) {
		this.mutator = mutator;
		this.data = data;
		this.inputObjectCache = inputObjectCache;
	}

	@Override
	public boolean has(String name) {
		return this.data.containsKey(name);
	}

	@Override
	public <T extends DatabaseObject, I extends IEntityInput> T readRef(ObjectRef ref) {
		if (ref == null) {
			return null;
		}
		EntityHelper<T, I> helper = mutator.getHelper(ref.getType());
		long id = ref.getId();
		return readRef(helper, id);
	}

	private <T, I extends IEntityInput> T readRef(EntityHelper<T, I> helper, long id) {
		if (id > 0) {
			return helper.getById(id);
		}
		if (id == 0) {
			return null;
		}
		T obj = (T) inputObjectCache.get(id);
		if (obj == null) {
			obj = (T) helper.newInstance();
			if (obj instanceof DatabaseObject) {
				inputObjectCache.put(id, (DatabaseObject) obj);
			}
		}
		return obj;
	}

	@Override
	public <T, I extends IEntityInput> T readChild(I input, String field) {
		EntityHelper<T, I> helper = (EntityHelper<T, I>) mutator.getHelper(input._type());
		T obj;
		InputHelper sub = sub(field);
		if (sub.has("id")) {
			obj = readRef(helper, input.getId());
			if (obj == null) {
				return null;
			}
		} else {
			obj = (T) helper.newInstance();
		}
		helper.fromInput(input, obj, sub);
		return obj;
	}

	@Override
	public <T, I extends IEntityInput> T readEmbedded(I input, String field) {
		EntityHelper<T, I> helper = (EntityHelper<T, I>) mutator.getHelper(input._type());
		T obj = (T) helper.newInstance();
		helper.fromInput(input, obj, sub(field));
		return obj;
	}

	@Override
	public <T extends DatabaseObject, I extends IEntityInput> T readUnionChild(I input, String field) {
		EntityHelper<T, I> helper = (EntityHelper<T, I>) mutator.getHelper(input._type());
		T obj;
		InputHelper sub = sub(field);
		if (sub.has("id")) {
			obj = readRef(helper, input.getId());
			if (obj == null) {
				return null;
			}
		} else {
			obj = (T) helper.newInstance();
		}
		helper.fromInput(input, obj, sub);
		return obj;
	}

	@Override
	public InputHelper subUnion(String name) {
		InputHelperImpl sub = sub(name);
		return sub.sub("value" + sub.data.get("type").toString());
	}

	@SuppressWarnings("unchecked")
	@Override
	public InputHelperImpl sub(String name) {
		if (name.equals(currentField)) {
			if (currentColl.hasNext()) {
				return new InputHelperImpl(mutator, currentColl.next(), inputObjectCache);
			} else {
				return new InputHelperImpl(mutator, new HashMap<>(), inputObjectCache);
			}
		}
		Object obj = data.get(name);
		if (obj instanceof Collection) {
			currentField = name;
			currentColl = ((Collection) obj).iterator();
			return sub(name);
		} else {
			currentField = null;
			currentColl = null;
		}
		if (obj == null) {
			obj = new HashMap<>();
		}
		return new InputHelperImpl(mutator, (Map<String, Object>) obj, inputObjectCache);
	}

	@Override
	public <T extends DatabaseObject, I extends IEntityInput> T readUpdate(EntityHelper<T, I> helper, long id, I input,
			String field) {
		// Check id
		if (id <= 0) {
			throw new RuntimeException("Invalid id");
		}

		// Get and Check current value
		T current = helper.getById(id);
		if (current == null) {
			throw new RuntimeException("Nothing found for given id.");
		}

		// Set old if creatable
		if (current instanceof CreatableObject) {
			((CreatableObject) current).setOld((CreatableObject) helper.getOld(id));
		}

		// Get new value
		return readChild(input, field);
	}
}
