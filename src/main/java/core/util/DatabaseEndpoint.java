package core.util;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * Created by theo on 03-07-2017.
 * Database connection definition and datasource for Mysql and Postgresql
 */
public class DatabaseEndpoint {
	
	// ConnectionString
	private String url = "";
    private String username = "";
    private String password = "";
    private String database = "";
    private static DatabaseEndpoint destinationInstance = null;
            
    /**
     * Setup the database connection params
     * @param url
     * @param username
     * @param password
     * @param database
     */
    public void setup(String url, String username, String password, String database) {
    	this.url = url;
    	this.username = username;
    	this.password = password;
    	this.database = database;
    }
        

    /**
     * Creates a DataSource for the default _database based on the driver specified in properties
     * @return MysqlDataSource | PGSimpleDataSource
     * @throws IllegalArgumentException
     * @throws SQLException 
     */
    public DataSource getDataSource() throws IllegalArgumentException, SQLException {
        return getDataSource(this.database);
    }

    /**
     * Creates a DataSource for the specified database, based on the driver specified in the properties
     * @param database The name of the database
     * @return MysqlDataSource | PGSimpleDataSource
     * @throws SQLException
     * @throws IllegalArgumentException
     */
    private DataSource getDataSource(String database) throws SQLException, IllegalArgumentException {
        if (this.url.contains("mysql")) {
            MysqlDataSource ds;
            ds = new MysqlDataSource();
            ds.setUrl(this.url + "/" + database);
            ds.setUser(this.username);
            ds.setPassword(this.password);
            return ds;
        } else if (this.url.contains("postgresql")) {
            PGSimpleDataSource ds;
            ds = new PGSimpleDataSource();
            ds.setUrl(this.url + "/" + database);
            ds.setUser(this.username);
            ds.setPassword(this.password);
            return ds;
        } else {
            throw new IllegalArgumentException("Unsupported database connector. Valid options are mysql or postgresql");
        }
    }


	/* Singleton for destination database definition*/
	public static DatabaseEndpoint getDestinationInstance() {
		if(destinationInstance == null){
			destinationInstance = new DatabaseEndpoint();
		}
		return destinationInstance;
	}

}
