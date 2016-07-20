import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

public class SetConfigProperties extends Task {
	 public void execute() {
		 try{
			 this.getProject().setProperty("MyOwnVar",this.getProject().getProperty("ACCOUNTID_" + this.getProject().getProperty("currentdb")).toString().split(",")[Integer.parseInt(this.getProject().getProperty("counter"))-1]);
			 this.getProject().setProperty("MySystemToday",this.getProject().getProperty("SYSTEMTODAY_" + this.getProject().getProperty("currentdb")).toString().split(",")[Integer.parseInt(this.getProject().getProperty("counter"))-1]);
			 this.getProject().setProperty("MySystemTodayplus",this.getProject().getProperty("SYSTEMTODAYPLUS_" + this.getProject().getProperty("currentdb")).toString().split(",")[Integer.parseInt(this.getProject().getProperty("counter"))-1]);
			 
		 }catch(Exception e){
			 e.printStackTrace(); 
	           throw new BuildException("Error While configuring properties"); 
		 }
	 }

}
