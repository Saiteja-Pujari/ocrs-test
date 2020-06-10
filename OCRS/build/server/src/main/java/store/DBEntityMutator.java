package store;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.transaction.Transactional;

import org.hibernate.Hibernate;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.event.spi.PostDeleteEvent;
import org.hibernate.event.spi.PostDeleteEventListener;
import org.hibernate.event.spi.PostInsertEvent;
import org.hibernate.event.spi.PostInsertEventListener;
import org.hibernate.event.spi.PostUpdateEvent;
import org.hibernate.event.spi.PostUpdateEventListener;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.persister.entity.EntityPersister;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import models.CreatableObject;

@Component
public class DBEntityMutator
		implements EntityMutator, PostInsertEventListener, PostUpdateEventListener, PostDeleteEventListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private class Context {
		private Set<DatabaseObject> saveQueue;
		private Set<DatabaseObject> deleteQueue;
		private Set<DatabaseObject> saveDuplicates;
		private boolean isInConversion;
		private final Map<DatabaseObject, DatabaseObject> clones = new HashMap<>();
		private ValidationContextImpl context;
	}

	private final EntityManager manager;
	private ObjectFactory<EntityHelperService> helperService;

	@PersistenceUnit
	private EntityManagerFactory emf;

	@PostConstruct
	protected void init() {
		SessionFactoryImpl sessionFactory = emf.unwrap(SessionFactoryImpl.class);
		EventListenerRegistry registry = sessionFactory.getServiceRegistry().getService(EventListenerRegistry.class);
		registry.getEventListenerGroup(EventType.POST_INSERT).appendListener(this);
		registry.getEventListenerGroup(EventType.POST_UPDATE).appendListener(this);
		registry.getEventListenerGroup(EventType.POST_DELETE).appendListener(this);
	}

	@Autowired
	private ApplicationEventPublisher applicationEventPublisher;

	static ThreadLocal<Context> threadLocalMutator = new ThreadLocal<>();

	@Autowired
	public DBEntityMutator(EntityManager manager, ObjectFactory<EntityHelperService> helperService) {
		this.manager = manager;
		this.helperService = helperService;
	}

	@Transactional
	public void save(DatabaseObject obj) {
		saveOrUpdate(obj);
		obj.saveStatus = DBSaveStatus.Saved;
	}

	@Transactional
	public void update(DatabaseObject obj) {
		saveOrUpdate(obj);
	}

	@Transactional
	public void saveOrUpdate(DatabaseObject obj) {
		boolean created = createContextIfNotExist();
		try {
			if (obj.isInConvert() || obj.isDeleted()) {
				return;
			}
			Context ctx = getContext();
			if (ctx.saveQueue.contains(obj)) {
				ctx.saveDuplicates.add(obj);
			} else {
				ctx.saveQueue.add(obj);
				saveInternal(obj);
			}
		} finally {
			if (created) {
				clearContext();
			}
		}
	}

	private void clearContext() {
		threadLocalMutator.set(null);
	}

	private boolean createContextIfNotExist() {
		Context context = threadLocalMutator.get();
		if (context != null) {
			return false;
		}
		Context ctx = new Context();
		ctx.saveQueue = new HashSet<DatabaseObject>();
		ctx.saveDuplicates = new HashSet<DatabaseObject>();
		ctx.deleteQueue = new HashSet<DatabaseObject>();
		ctx.context = new ValidationContextImpl();
		threadLocalMutator.set(ctx);
		return true;
	}

	private Context getContext() {
		Context context = threadLocalMutator.get();
		if (context != null) {
			return context;
		}
		Context ctx = new Context();
		ctx.saveQueue = new HashSet<DatabaseObject>();
		ctx.saveDuplicates = new HashSet<DatabaseObject>();
		ctx.deleteQueue = new HashSet<DatabaseObject>();
		ctx.context = new ValidationContextImpl();
		threadLocalMutator.set(ctx);
		return ctx;
	}

	public boolean finish() {
		Context ctx = getContext();
		Iterator<DatabaseObject> iterator = new HashSet<DatabaseObject>(ctx.saveDuplicates).iterator();
		ctx.saveDuplicates.clear();
		while (iterator.hasNext()) {
			DatabaseObject next = iterator.next();
			if (!saveInternal(next)) {
				return false;
			}
		}
		if (!ctx.saveDuplicates.isEmpty()) {
			return finish();
		}

		Set<DatabaseObject> clone = new HashSet<DatabaseObject>(ctx.deleteQueue);
		ctx.deleteQueue.clear();
		for (DatabaseObject obj : clone) {
			manager.remove(obj);
		}
		if (!ctx.deleteQueue.isEmpty()) {
			return finish();
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	private <T extends DatabaseObject> boolean saveInternal(T entity) {
		if (entity.isDeleted()) {
			return true;
		}
		EntityHelper<T, ?> helper = (EntityHelper<T, ?>) getHelper(Hibernate.getClass(entity).getSimpleName());
		Context ctx = getContext();
		if (helper != null) {
			helper.setDefaults(entity);
			helper.compute(entity);
			helper.validate(entity, ctx.context);
		}
		if (ctx.context.hasErrors()) {
			throw new ValidationFailedException(ctx.context.getErrors());
		}
		manager.persist(entity);
		if (helper != null) {
			if (entity.isNew()) {
				helper.onCreate(entity);
			} else {
				T oldValue = entity;
				if (entity instanceof CreatableObject) {
					oldValue = (T) ((CreatableObject) entity).getOld();
				}
				helper.onUpdate(entity, oldValue);
			}
		}
		return true;
	}

	public <T extends DatabaseObject> boolean delete(T obj) {
		boolean created = createContextIfNotExist();
		try {
			Context ctx = getContext();
			if (ctx.isInConversion) {
				ctx.deleteQueue.add(obj);
				return true;
			}
			return deleteInternal(obj);
		} finally {
			if (created) {
				clearContext();
			}
		}
	}

	public void conversionStart() {
		Context ctx = getContext();
		ctx.isInConversion = true;
	}

	public boolean converstionCompleted() {
		Context ctx = getContext();
		ctx.isInConversion = false;
		for (DatabaseObject obj : ctx.deleteQueue) {
			if (!deleteInternal(obj)) {
				return false;
			}
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	private <T extends DatabaseObject> boolean deleteInternal(T entity) {
		if (entity.isDeleted()) {
			return true;
		}
		entity.setDeleted(true);
		EntityHelper<T, ?> helper = (EntityHelper<T, ?>) getHelper(entity.getClass().getSimpleName());
		Context ctx = getContext();
		if (ctx.context.hasErrors()) {
			throw new ValidationFailedException(ctx.context.getErrors());
		}
		if (helper != null) {
			if (!helper.onDelete(entity, null)) {
				return false;
			}
		}
		manager.remove(entity);
		return true;
	}

	public <T extends DatabaseObject> void peformDeleteOrphan(Collection<T> oldList, Collection<T> newList) {
		List<T> deletedList = new ArrayList<T>();
		for (T t : oldList) {
			if (!newList.contains(t)) {
				deletedList.add(t);
			}
		}
		oldList.clear();
		oldList.addAll(newList);
		for (T t : deletedList) {
			this.delete(t);
		}
	}

	public <T extends DatabaseObject, I extends IEntityInput, H extends EntityHelper<T, I>> H getHelper(
			String fullType) {
		return (H) this.helperService.getObject().get(fullType);
	}

	public <T extends DatabaseObject, I extends IEntityInput, H extends EntityHelper<T, I>> H getHelperByInstance(
			Object fullType) {
		return (H) this.helperService.getObject().get(fullType.getClass().getSimpleName());
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void processOnLoad(Object entity) {
		EntityHelper helper = getHelper(entity.getClass().getName());
		Context ctx = getContext();
		if (helper != null) {
			if (ctx.clones.containsKey(entity)) {
				return;
			}
			Object clone = helper.clone((DatabaseObject) entity);
			ctx.clones.put((DatabaseObject) entity, (DatabaseObject) clone);
		}

	}

	@Override
	public boolean requiresPostCommitHanding(EntityPersister persister) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onPostInsert(PostInsertEvent event) {
		DataStoreEvent dEvent = new DataStoreEvent(this);
		dEvent.setEntity(event.getEntity());
		dEvent.setType(StoreEventType.INSERT);
		applicationEventPublisher.publishEvent(dEvent);
	}

	@Override
	public void onPostDelete(PostDeleteEvent event) {
		DataStoreEvent dEvent = new DataStoreEvent(this);
		dEvent.setEntity(event.getEntity());
		dEvent.setType(StoreEventType.DELETE);
		applicationEventPublisher.publishEvent(dEvent);
	}

	@Override
	public void onPostUpdate(PostUpdateEvent event) {
		DataStoreEvent dEvent = new DataStoreEvent(this);
		dEvent.setEntity(event.getEntity());
		dEvent.setType(StoreEventType.UPDATE);
		applicationEventPublisher.publishEvent(dEvent);
	}

}
