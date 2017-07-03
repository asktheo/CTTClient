package core;

import org.sql2o.Sql2o;

/**
 * Theo Askov 2016-11-03
 */
public abstract class BaseDAO {
	// sql2o setup

	protected boolean transactionMode = false;
	protected org.sql2o.Connection transactionConnection;

	protected void startTransaction(Sql2o sql2o){
		transactionConnection = sql2o.beginTransaction();
		transactionMode = true;
	}

	protected void commitTransaction(){
		transactionConnection.commit();
	}

	protected void endTransaction(){
		transactionConnection.close();
		transactionMode = false;
	}

}
