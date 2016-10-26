package org.apache.cayenne.examples;


import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.configuration.server.ServerRuntime;
import org.apache.cayenne.examples.testbase.CayenneTestRuntimeFactory;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

public class CompositeKeyTest {

	private ServerRuntime runtime;

	@Before
	public void setup() throws SQLException{
		runtime = CayenneTestRuntimeFactory.getRuntime();
	}

	@Test
	public void compositeKeyInsertPasses(){
		ObjectContext ctx = runtime.newContext();
		Auto a1 = new Auto();
		Auto a2 = new Auto();
		Composite c = ctx.newObject(Composite.class);
		c.addToAuto1(a1);
		c.addToAuto2(a2);
		c.setO(10);
		ctx.commitChanges();
	}

}