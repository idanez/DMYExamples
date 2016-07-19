package my.snippets;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SqliteTest {

	private static SqliteTest instance = null;
	private Connection conn = null;
	String nUrl = "db/teste.db";
	
	public static void main(String args[]) {
		try {
			SqliteTest sql = new SqliteTest();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private SqliteTest() throws ClassNotFoundException, SQLException{
		open();
	}
	
	private  void open() throws ClassNotFoundException, SQLException{
		if(conn == null){
			Log.addLog("Abrindo conexão...");
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:"+nUrl);
			Log.addLog("Conexão realizada com sucesso...");
		}else{
			if(conn.isClosed()){
				Log.addLog("So abrindo a conexao.... objeto já instanciado...");
				conn = DriverManager.getConnection("jdbc:sqlite:"+nUrl);
			}
			Log.addLog("Conexão aberta.");
		}
	}
	
	public  static SqliteTest getInstance() throws ClassNotFoundException, SQLException{
		if(instance == null){
			instance = new SqliteTest();
		}
		return instance;
	}
	
	public  void openCommit() throws SQLException{
		if(this.conn != null){
			this.conn.setAutoCommit(true);
		}
	}
	
	public  void closeCommit() throws SQLException{
		if(this.conn != null){
			this.conn.setAutoCommit(false);		
		}
	}
	
	public  void openConnect() throws SQLException, ClassNotFoundException{
		if(this.conn != null){
			this.open();
		}
	}
	
	public  void closeConnect() throws SQLException{
		if(this.conn != null){
			// caso nao esteja comentado derruba a vm
			this.conn.close();
			conn = null;
		}
	}
	

	public  Connection getConnection() throws ClassNotFoundException, SQLException{
		this.open();
		return conn;
	}

	public static class Log {
		public static void addLog(String log) {
			long timeInMillis = System.currentTimeMillis();
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			Date date = new Date(timeInMillis);
			System.out.println(sdf.format(date)+" - "+log);
		}
	}
	
}
