package store;

import d3e.core.DFile;
import d3e.core.DFileEntityInput;
import graphql.input.ObjectRef;

public interface InputHelper {
	boolean has(String name);

	InputHelper sub(String name);

	InputHelper subUnion(String name);

	public <T extends DatabaseObject, I extends IEntityInput> T readRef(ObjectRef ref);

	public <T, I extends IEntityInput> T readChild(I input, String field);

	public <T, I extends IEntityInput> T readEmbedded(I input, String field);

	public <T extends DatabaseObject, I extends IEntityInput> T readUnionChild(I input, String field);

	public <T extends DatabaseObject, I extends IEntityInput> T readUpdate(EntityHelper<T, I> helper, long id, I input,
			String field);

	default DFile readDFile(DFileEntityInput input, String field) {
		if (input == null) {
			return null;
		}
		InputHelper helper = sub(field);
		if (helper == null) {
			return null;
		}
		DFile entity = new DFile();
		if (helper.has("size")) {
			entity.setSize(input.getSize());
		}
		if (helper.has("name")) {
			entity.setName(input.getName());
		}
		if (helper.has("id")) {
			entity.setId(input.getId());
		}
		return entity;
	}
}
