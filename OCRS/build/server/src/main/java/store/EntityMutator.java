package store;

import java.util.Collection;

public interface EntityMutator {

	public void save(DatabaseObject obj);

	public void update(DatabaseObject obj);

	public void saveOrUpdate(DatabaseObject obj);

	public boolean finish();

	public <T extends DatabaseObject> boolean delete(T obj);

	public void conversionStart();

	public boolean converstionCompleted();

	public <T extends DatabaseObject> void peformDeleteOrphan(Collection<T> oldList, Collection<T> newList);

	public <T extends DatabaseObject, I extends IEntityInput, H extends EntityHelper<T, I>> H getHelper(
			String fullType);

	public <T extends DatabaseObject, I extends IEntityInput, H extends EntityHelper<T, I>> H getHelperByInstance(
			Object fullType);

	public void processOnLoad(Object entity);

}
