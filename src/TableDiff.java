import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;
public class TableDiff extends Task {
	String server;
	String port;
	String user;
	String password;
	String db1;
	String db2;
	String tablename;
	String columns;
	String property;
	String neglectdatetime;
	String createhtml;
	public void setCreatehtml(String createhtml) {
		this.createhtml = createhtml;}
	public void setColumns(String columns) {
		this.columns = columns;}
	public void setNeglectdatetime(String neglectdatetime) {
		this.neglectdatetime = neglectdatetime;}
	public void setDb1(String db1) {
		this.db1 = db1;}
	public void setDb2(String db2) {
		this.db2 = db2;}
	public void setTablename(String tablename) {
		this.tablename = tablename;}
	public void setServer(String server) {
		this.server = server;}
	public void setPort(String port) {
		this.port = port;}
	public void setUser(String user) {
		this.user = user;}
	public void setPassword(String password) {
		this.password = password;}
	public void setProperty(String property) {
		this.property = property;}
	private Project myProject = this.getProject();
	public void execute() {
       try{
    	   DBCon dbcon = new DBCon(server, port, user, password);
    	   String[] tables = tablename.split(",");
    	   String MyQuery="";
    	   StringBuilder htmlout = new StringBuilder();
    	   String tbl="";
    	   String tempcols="";
    	   for(String x:tables)
    	   {
    		   tbl+=myProject.getProperty(x)+",";
    	   }
    	   tbl = tbl.substring(0,tbl.length()-1);
    	   String tmptables = "(SELECT '" + tbl.replace(",","'  AS a UNION SELECT '") + "' AS a) AS aa";
    	   //log("SELECT DISTINCT(a) FROM" + tmptables);

    	   ResultSet rrs = dbcon.getRecords("SELECT DISTINCT(a) FROM" + tmptables);
    	   log("Prepairing File" + myProject.getProperty("basedir")+ "/TableDiff_"+ myProject.getProperty("currentdb") +".htm");
    	   while(rrs.next())
    	   {
    		  try{
    		  log("\nComparing Table: " + rrs.getString(1));
    		   MyQuery="select min(tbl_name) as tbl_name,$strcol from (select '$dbname_cur.$table' as tbl_name,$strcol from $dbname_cur.$table as new union all select '$dbname_prev.$table' as tbl_name,$strcol from $dbname_prev.$table as old) as alias_table group by $strcol having count(*)=1;";
    		   if(columns.toLowerCase().equals("all") || tables.length>0)
    		   {
	    		   String tempColSQL = "SELECT * FROM "+db1+"."+rrs.getString(1)+" limit 1";
	    		   ResultSet tmpRs= dbcon.getRecords(tempColSQL);
	    		   ResultSetMetaData tmprsmd = tmpRs.getMetaData();
	    		   columns="";
	    		   tempcols="";
	    		   for(int i=1;i<=tmprsmd.getColumnCount();i++)
	    		   {
	    			   if(!tmprsmd.getColumnTypeName(i).equals("DATETIME") || neglectdatetime.toLowerCase().equals("false"))
	    				   columns+=","+tmprsmd.getColumnName(i);
	    			   else
	    				   tempcols += ","+tmprsmd.getColumnName(i);
	    		   }
	    		   columns=columns.replaceFirst(",","");
	    		   tmpRs.close();
		    	}
		    	   MyQuery= MyQuery.replace("$strcol",columns).replace("$dbname_cur", db2).replace("$dbname_prev", db1).replace("$table", rrs.getString(1));
		    	   //log(MyQuery);
		    	   ResultSet rs = dbcon.getRecords(MyQuery);
		    	   ResultSetMetaData rsmd = rs.getMetaData();
		    	   if(!tempcols.equals(""))
		    		   tempcols = "<span style='color:red;font-size:18px;font-weight:bold'>Columns: \"" + tempcols.replaceFirst(",","") + "\" not considered as they are of type DATETIME</span>";
		    	   htmlout.append("<h3>"+ rrs.getString(1) +"</h3>"+tempcols+"<table cellpadding='0px' cellspacing='2px' width='100%'><tr>");
		    	   for(int i=1;i<=rsmd.getColumnCount();i++)
		 		   {
		 			  htmlout.append("<th>"+rsmd.getColumnName(i)+"</th>");
		 		   }
		    	   htmlout.append("</tr>");
		    	   while(rs.next())
		    	   {
		     		   htmlout.append("<tr>");
		     		   for(int i=1;i<=rsmd.getColumnCount();i++)
		     		   {
		     			  htmlout.append("<td>"+rs.getString(i)+"</td>");
		     		   }
		     		  htmlout.append("</tr>");
		    	   }
		    	   rsmd =null;
		    	   rs.close();
		    	   rs = null;
		    	   
		    	   htmlout.append("</table>");
    		  }catch(Exception e){log("No Such Table "+ rrs.getString(1) +" Found on Database " + server + "." +db1);}
    	   }
    	   rrs.close();
    	  // project.setProperty(property, htmlout.toString()); 
    	   if(createhtml.toLowerCase().equals("true"))
    	   {
	    	   File file = new File(myProject.getProperty("basedir")+ "/RESULTS/TableDiff_"+ myProject.getProperty("currentdb") +".html");
	    	   BufferedWriter writer = new BufferedWriter(new FileWriter(file));
	    	  htmlout.insert(0,"<html><head><style>th{background-color:#1096D6;color:#fff;font-family:'Calibri','sans-serif';font-size:16px;}th,td{border:solid 1px #000;font-family:'Calibri','sans-serif';font-size:14px;padding:2px;}h1{font-family:'Calibri','sans-serif';font-size:22px;line-height:25px;text-align:center}.note{font-family:'Calibri','sans-serif';font-size:9px;line-height:12px;font-weight:small;}.Fail{background-color:#FDE4E4}</style></header><body><h1>Services Automation Result for Database <u>"+ myProject.getProperty("currentdb")+"</u></h1><table><tr><th>Run By</th><td>"+myProject.getProperty("user.name")+"</td></tr><tr><th>Database</th><td>"+myProject.getProperty("DBLIST")+"</td></tr><tr><th>Run For Properties</th><td>"+myProject.getProperty("PROPERTYLIST")+"</td></tr><tr><th>Run For Services</th><td>"+myProject.getProperty("SERVICELIST")+"</td></tr><tr><th>Run on Computer</th><td>"+myProject.getProperty("env.COMPUTERNAME")+"</td></tr></table><hr/><h1>Table Differences</h1>");
	    	  htmlout.append("</body></html>");
	    	  writer.write(htmlout.toString());
	    	   writer.close();
    	   }
    	   else
    		   myProject.setProperty(property, htmlout.toString()); 
    	   dbcon.dropConnection();
       } 
       catch(Exception e)
       {
           e.printStackTrace(); 
           throw new BuildException("Error While Compairing Table " + tablename +"."); 
        }
        
    }

}
