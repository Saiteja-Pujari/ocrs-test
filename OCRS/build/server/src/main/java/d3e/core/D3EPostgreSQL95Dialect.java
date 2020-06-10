package d3e.core;

import org.hibernate.dialect.PostgreSQL95Dialect;
import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.type.StringType;

public class D3EPostgreSQL95Dialect extends PostgreSQL95Dialect {

	public D3EPostgreSQL95Dialect() {
		super();
		registerFunction("array_agg", new StandardSQLFunction("array_agg", StringType.INSTANCE));
	}
}
