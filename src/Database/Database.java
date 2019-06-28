package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author lucas
 */
public class Database {

    private static final String sqlUrl = "jdbc:sqlite:test.db";
    protected static Database instance;
    protected static DatabaseMetaData metaData;
    protected Connection sqlConnection;

    private Database() {
	   try {
		  sqlConnection = DriverManager.getConnection(sqlUrl);
		  metaData = sqlConnection.getMetaData();
		  System.out.println("Meta data " + metaData.getDriverName());
		  System.out.println("Connection to database succeeded.");
	   } catch (SQLException e) {
		  System.out.println("Unable to connect to database.");
		  System.out.println(e.getMessage());
		  e.printStackTrace();
	   }

    }

    protected Database(String databaseName) {
	   try {
		  sqlConnection = DriverManager.getConnection("jdbc:sqlite:" + databaseName);
		  metaData = sqlConnection.getMetaData();
		  System.out.println("Meta data " + metaData.getDriverName());
		  System.out.println("Connection to database succeeded.");
	   } catch (SQLException e) {
		  System.out.println("Unable to connect to database.");
		  System.out.println(e.getMessage());
		  e.printStackTrace();
	   }
    }

    public static Database getInstance() {
	   if (instance == null) {
		  instance = new Database();
	   }
	   return instance;
    }

    protected void executeSql(String sqlString) {

	   try (Statement stmnt = sqlConnection.createStatement()) {
		  stmnt.execute(sqlString);
                  stmnt.close();
	   } catch (SQLException e) {
		  System.out.println("Unable to create table");
		  System.out.println(e.getMessage());
		  e.printStackTrace();
	   }
    }

    public ResultSet sqlQueryWResults(String sqlString) {
	   ResultSet ret = null;
	   try (PreparedStatement stmnt1 = sqlConnection.prepareStatement(sqlString)) {
		  ret = stmnt1.executeQuery();
	   } catch (SQLException e) {
		  System.out.println("Unable to query database.");
		  System.out.println(e.getMessage());
		  e.printStackTrace();
	   }
	   return ret;
    }
}
