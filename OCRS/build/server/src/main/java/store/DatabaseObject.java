package store;

import java.util.Collection;
import java.util.function.Consumer;
import java.util.logging.Logger;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class DatabaseObject {

	@javax.persistence.Id
	@javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.AUTO)
	protected long id;

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	private static Logger logger = Logger.getLogger("DatabaseObject");
	private transient static int MAX_SAVE_COUNT = 20;
	private transient static int OBJ_LOG_COUNT = 5;
	private transient boolean isDeleted;
	public transient boolean isImportingFromXml;
	private transient boolean isDirty;
	private transient boolean isNew = true;
	private transient boolean needsUpdate;
	private transient boolean isDeleteProcessed;
	protected transient boolean needSaveProcess = true;
	protected transient boolean needDeleteProcess = true;
	private transient int onSaveCount;
	private transient boolean isGotId;
	protected DBSaveStatus saveStatus = DBSaveStatus.New;
	private transient boolean isInConvert;

	public DatabaseObject() {
	}

	public boolean isDeleted() {
		return this.isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public boolean isInConvert() {
		return this.isInConvert;
	}

	public void setInConvert(boolean isInConvert) {
		this.isInConvert = isInConvert;
	}

	protected Object toLogStr(Collection<?> col) {
		return col.size();
	}

	protected Object toLogStr(DatabaseObject db) {
		if (db == null) {
			return null;
		}
		return db.getId();
	}

	public String getObjectName() {
		return null;
	}

	protected void setInputs() {

	}
	public void updateFlat(DatabaseObject obj) {
		
	}

	public void setNeedDeleteProcess(boolean needDeleteProcess) {
		this.needDeleteProcess = needDeleteProcess;
	}

	protected boolean isComponent() {
		return false;
	}

	public void setNeedSaveProcess(boolean needSaveProcess) {
		this.needSaveProcess = needSaveProcess;
	}

	public void setDeletedProcessed(boolean isDeleteProcessed) {
		this.isDeleteProcessed = isDeleteProcessed;
	}

	public boolean isDeleteProcessed() {
		return isDeleteProcessed;
	}

	public void recordLog() {
	}

	@Override
	public boolean equals(Object a) {
		boolean isEquals = false;
		if ((a instanceof DatabaseObject)) {
			DatabaseObject aObj = (DatabaseObject) a;
			if ((!aObj.isNew() && !this.isNew())) {
				isEquals = (aObj.getId() == this.getId());
			} else {
				isEquals = (aObj == this);
			}
		}
		return isEquals;
	}

	public DatabaseObject clone() {
		return null;
	}

	public void cloneInstance(DatabaseObject clone) {
		clone.id = this.id;
	}

	public boolean isNew() {
		return this.saveStatus == DBSaveStatus.New;
	}

	public void setNew(boolean isNew) {
		this.isNew = isNew;
	}

	public void updateMasters(Consumer<DatabaseObject> visitor) {
		updateFlat(this);
	}

	public Object _masterObject() {
		return null;
	}
	public boolean transientModel() {
		return false;
	}
}
