import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

public class FormatErrors extends Task {
	 String dbName;
	 
	 public void setDbName(String dbName) {
		this.dbName = dbName;
	}


	 public void execute() {
		 try{
			  
			 log("Formating Error Message in tabular Format");
			 String errorMessage="";
				if(!this.getProject().getProperty("DBerrorMessages").toString().equals("")){
					errorMessage = "<table><tr><th>DB</th><th>APP</th><th>Property</th><th>Service</th><th>Extract</th><th>Error Code</th></tr>";
					String[] fails = this.getProject().getProperty("DBerrorMessages").toString().split("~");
					for(int i=1;i<fails.length;i++)
					{
						errorMessage += ("<tr>" + fails[i].replace("[","<td>").replace("]","</td>") + "</tr>");
					}
					errorMessage +="</table>";
				}
				this.getProject().setProperty("EmailErrorMessage"+dbName,errorMessage);

		 }catch(Exception e){
			 e.printStackTrace(); 
	           throw new BuildException("Error While configuring properties"); 
		 }
	 }
}
