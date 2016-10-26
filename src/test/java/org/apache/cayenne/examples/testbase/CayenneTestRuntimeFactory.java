package org.apache.cayenne.examples.testbase;

import org.apache.cayenne.access.dbsync.CreateIfNoSchemaStrategy;
import org.apache.cayenne.access.dbsync.SchemaUpdateStrategy;
import org.apache.cayenne.configuration.server.ServerRuntime;
import org.apache.cayenne.configuration.server.ServerRuntimeBuilder;
import org.apache.cayenne.di.Binder;
import org.apache.cayenne.di.Module;
import org.apache.cayenne.query.SQLSelect;
import org.h2.jdbcx.JdbcConnectionPool;

import javax.sql.DataSource;
import java.sql.SQLException;

public class CayenneTestRuntimeFactory {

	private final DataSource dataSource;
	private static ServerRuntime runtime;

	public CayenneTestRuntimeFactory(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public static ServerRuntime getRuntime() throws SQLException {
		if (runtime == null) {
			DataSource dataSource = JdbcConnectionPool.create("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", "user", "password");

			runtime = new ServerRuntimeBuilder()
					.dataSource(dataSource)
					.addConfig("cayenne-examples-composite-key.xml")
					.addModule(new Module() {
						@Override
						public void configure(Binder binder) {
							binder.bind(SchemaUpdateStrategy.class).to(CreateIfNoSchemaStrategy.class);
						}
					}).build();

			SQLSelect.dataRowQuery("SELECT * from dummy").select(runtime.newContext());
		}
		return runtime;
	}
}
