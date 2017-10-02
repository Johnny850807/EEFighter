package studentsystem.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import static studentsystem.Secret.*;

import javax.naming.InitialContext;

public class SqlCore {
	private static SqlCore instance;
	private Statement statement;
	public SqlCore(){
		try {
			statement = connectToMsSql();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static SqlCore getInstance(){
		if (instance == null)
			instance = new SqlCore();
		return instance;
	}
	
	private Statement connectToMsSql() throws Exception{
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		String connUrl = String.format("jdbc:sqlserver://%s;databaseName=StudentSystem", SERVER_ADDRESS);
		Connection conn = DriverManager.getConnection(connUrl, SQL_NAME, SQL_PWD);
		return conn.createStatement();
	}
	
	public ResultSet executeQuery(String query) throws SQLException{
		return statement.executeQuery(query);
	}
	
	public void execute(String query) throws SQLException{
		statement.execute(query);
	}
	
	public static void main(String[] argv){
		SqlCore core = SqlCore.getInstance();
	}
	
}
