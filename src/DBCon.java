import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
public class DBCon {
	public static int getNoOfConnection =0;
	private Connection conn=null;
	private List<Statement> statementList = new ArrayList<Statement>();
	public DBCon(String dbServer,String Port,String User,String Password) throws Exception
	{
		getConnection(dbServer,Port,User,Password);
		
	}
	
	/*public ResultSet getRecords(String strSQL,Statement s) throws Exception
	{
		//System.out.println(strSQL);
		try
		{
			s.executeQuery (strSQL);
			return s.getResultSet();
			
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw e;
		}
	}*/


	public ResultSet getRecords(String strSQL) throws Exception
	{
		//Date dt = new Date();
		//System.out.println("[" + dt.toString() + "]  " + strSQL);
		try
		{
			Statement s = conn.createStatement();	
			statementList.add(s);
			s = conn.createStatement();	
			//System.out.println(strSQL);
			return s.executeQuery(strSQL);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			throw e;
		}
	}
	
	private void getConnection(String dbServer,String Port,String User,String Password) throws Exception
	{
		String ConnStr = "jdbc:mysql://"+dbServer+":"+Port;
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			conn= DriverManager.getConnection(ConnStr, User, Password);
			getNoOfConnection++;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}
	}
	
	public void dropConnection()
	{
		
		for (int i = 0; i < statementList.size(); i++) {
			try {
				statementList.get(i).close();
			} catch (SQLException e) {
				//ignore
			}
			
		}
		
		try
		{
			if(!conn.isClosed())
			{
				
				getNoOfConnection--;
				//conn.
				conn.close();
				//System.out.println("Active DB Connections count is: " + getNoOfConnection);
			}
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	public boolean Execute(String strSQL)
	{
		Date dt = new Date();
		System.out.println("[" + dt.toString() + "]  " + strSQL);
		try {
			Statement st = conn.createStatement();
			st.executeUpdate(strSQL);
			return true;
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
		
	}
}