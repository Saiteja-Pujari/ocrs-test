package store;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EntityHelperService {

	@Autowired
	private Map<String, EntityHelper<?, ? extends IEntityInput>> entityHelpers;

	public void setEntityHelpers(Map<String, EntityHelper<?, ? extends IEntityInput>> entityHelpers) {
		this.entityHelpers = entityHelpers;
	}

	public EntityHelper<?, ? extends IEntityInput> get(String name) {
		return entityHelpers.get(name);
	}
	
	public EntityHelper<?, ? extends IEntityInput> getByObject(Object obj) {
		return entityHelpers.get(obj.getClass().getSimpleName());
	}


	public void set(String name, EntityHelper<?, ? extends IEntityInput> helper) {
		entityHelpers.put(name, helper);
	}
}
