import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;

import java.io.File;


public class validatePaths extends Task {

	private Project myProject = this.getProject();
	public void execute() {
		checkforApp1();
		checkforApp2();
		checkforDB1();
		checkforDB2();
		checkPerifery();
	}

	private void checkPerifery() {
		if(myProject.getProperty("LICDIR").lastIndexOf("/")!=myProject.getProperty("LICDIR").length()-1)
		{
			BuildException e = new BuildException("Licens Folder location must end with '/'");
			throw e;
		}
	}

	private void checkforDB2() {
		if(myProject.getProperty("DB2").equals("true"))
		{
			log("Database 2 needs to install");
			if(!myProject.getProperty("DBLIST").toUpperCase().equals("ALL"))
			{
				String[] dblist= myProject.getProperty("DBLIST").split(",");
				for(String x:dblist)
				{
					if(!FileExist(myProject.getProperty("DBBANK") + "/" + x + ".zip"))
					{
						BuildException e = new BuildException("Database Zip " + myProject.getProperty("DBBANK") + "/" + x + ".zip" + " Not Found");
						throw e;
					}
				}
			}
			else
			{
				if(!FolderExist(myProject.getProperty("DBBANK")))
				{
					BuildException e = new BuildException("DB Bank Location " + myProject.getProperty("DBBANK") + " Not Found");
					throw e;
				}
			}
			if(!FolderExist(myProject.getProperty("UPGRADE_LOCATION2")+","+myProject.getProperty("DBDIR")))
			{
				BuildException e = new BuildException("Upgrade Location or MySQL data Directory " + myProject.getProperty("UPGRADE_LOCATION1") + "," + myProject.getProperty("DBDIR") + " Not Found");
				throw e;
			}
		}
		else
		{
			log("Database 2 not to be install");
			if(!myProject.getProperty("DBLIST").toUpperCase().equals("ALL"))
			{
				String[] dblist= myProject.getProperty("DBLIST").split(",");
				for(String x:dblist)
				{
					if(!FolderExist(myProject.getProperty("DBDIR") + "/" + x + "_2"))
					{
						BuildException e = new BuildException("Database " + myProject.getProperty("DBDIR") + "/" + x + "_2 Not Found");
						throw e;
					}
				}
			}
			else
			{
				File folder = new File(myProject.getProperty("DBBANK"));
				File[] files = folder.listFiles();
				
				for(File f:files)
				{
					if(!FolderExist(myProject.getProperty("DBDIR") + "/" + f.getName()+"_2"))
					{
						BuildException e = new BuildException("DB Bank Location " + myProject.getProperty("DBBANK") + f.getName()+"_2 Not Found");
					throw e;
					}
				}
			}
			if(!FolderExist(myProject.getProperty("UPGRADE_LOCATION2")+","+myProject.getProperty("DBDIR")))
			{
				BuildException e = new BuildException("Upgrade Location or MySQL data Directory " + myProject.getProperty("UPGRADE_LOCATION2") + "," + myProject.getProperty("DBDIR") + " Not Found");
				throw e;
			}
		}
		
	}

	private void checkforDB1() throws BuildException {
		if(myProject.getProperty("DB1").equals("true"))
		{
			log("Database 1 needs to install");
			if(!myProject.getProperty("DBLIST").toUpperCase().equals("ALL"))
			{
				String[] dblist= myProject.getProperty("DBLIST").split(",");
				for(String x:dblist)
				{
					if(!FileExist(myProject.getProperty("DBBANK") + "/" + x + ".zip"))
					{
						BuildException e = new BuildException("Database Zip " + myProject.getProperty("DBBANK") + "/" + x + ".zip" + " Not Found");
						throw e;
					}
				}
			}
			else
			{
				if(!FolderExist(myProject.getProperty("DBBANK")))
				{
					BuildException e = new BuildException("DB Bank Location " + myProject.getProperty("DBBANK") + " Not Found");
					throw e;
				}
			}
			if(!FolderExist(myProject.getProperty("UPGRADE_LOCATION1")+","+myProject.getProperty("DBDIR")))
			{
				BuildException e = new BuildException("Upgrade Location or MySQL data Directory " + myProject.getProperty("UPGRADE_LOCATION1") + "," + myProject.getProperty("DBDIR") + " Not Found");
				throw e;
			}
		}
		else
		{
			log("Database 1 not to be install");
			if(!myProject.getProperty("DBLIST").toUpperCase().equals("ALL"))
			{
				String[] dblist= myProject.getProperty("DBLIST").split(",");
				for(String x:dblist)
				{
					if(!FolderExist(myProject.getProperty("DBDIR") + "/" + x + "_1"))
					{
						BuildException e = new BuildException("Database " + myProject.getProperty("DBDIR") + "/" + x + " Not Found");
						throw e;
					}
				}
			}
			else
			{
				File folder = new File(myProject.getProperty("DBBANK"));
				File[] files = folder.listFiles();
				
				for(File f:files)
				{
					if(!FolderExist(myProject.getProperty("DBDIR") + "/" + f.getName()+"_1"))
					{
						BuildException e = new BuildException("DB Bank Location " + myProject.getProperty("DBBANK") + " Not Found");
					throw e;
					}
				}
			}
			if(!FolderExist(myProject.getProperty("UPGRADE_LOCATION1")+","+myProject.getProperty("DBDIR")))
			{
				BuildException e = new BuildException("Upgrade Location or MySQL data Directory " + myProject.getProperty("UPGRADE_LOCATION1") + "," + myProject.getProperty("DBDIR") + " Not Found");
				throw e;
			}
		}
	}

	private boolean FileExist(String Files)
	{
		String[] Paths = Files.split(",");
		for(String path:Paths)
		{
			File dir = new File(path);
			if(dir.exists() && !dir.isDirectory())
			{
				log("File " + path + " Exists");
				dir=null;
			}
			else
			{
				log("!!!!!! File " + path + " not Exists");
				return false;
			}
		}
		return true;
	}
	private boolean FolderExist(String Folders)
	{
		String[] Paths = Folders.split(",");
		for(String path:Paths)
		{
			File dir = new File(path);
			if(dir.exists()&&dir.isDirectory())
			{
				log("Directory " + path + " Exists");
				dir=null;
			}
			else
			{
				log("!!!!! Directory " + path + " not Exists");
				return false;
			}
		}
		return true;
	}
	
	
	private void checkforApp2() {
		if(myProject.getProperty("APP1").equals("true"))
		{
			log("Application 2 needs to install");
			if(!FileExist(myProject.getProperty("AAP_LOCATION2")) || !FolderExist(myProject.getProperty("PATCH_LOCATION2") +","+myProject.getProperty("TOMCATROOT1")+","+myProject.getProperty("TOMCATROOT2")+","+myProject.getProperty("LICDIR")+","+myProject.getProperty("SHAREDJARS")+","+myProject.getProperty("SCHEDULEDREPORT")+"_2,"+myProject.getProperty("EXTRACT_LOCATION")))
			{
				BuildException e = new BuildException("Some Configuration Missing!!!");
				throw e;
			}
		}
		else
		{
			log("Application 2 not to be install");
			if(!FolderExist(myProject.getProperty("TOMCATROOT2")+"/webapps/App_2,"+myProject.getProperty("LICDIR")+","+myProject.getProperty("SHAREDJARS")+","+myProject.getProperty("SCHEDULEDREPORT")+"_2,"+myProject.getProperty("EXTRACT_LOCATION")))
			{
				BuildException e = new BuildException("Some Configuration Missing!!!");
				throw e;
			}
		}
		
	}

	private void checkforApp1() {
		if(myProject.getProperty("APP1").equals("true"))
		{
			log("Application 1 needs to install");
			if(!FileExist(myProject.getProperty("AAP_LOCATION1")) || !FolderExist(myProject.getProperty("PATCH_LOCATION1") +","+myProject.getProperty("TOMCATROOT1")+","+myProject.getProperty("TOMCATROOT2")+","+myProject.getProperty("LICDIR")+","+myProject.getProperty("SHAREDJARS")+","+myProject.getProperty("SCHEDULEDREPORT")+"_1,"+myProject.getProperty("EXTRACT_LOCATION")))
			{
				BuildException e = new BuildException("Some Configuration Missing!!!");
				throw e;
			}
		}
		else
		{
			log("Application 1 not to be install");
			if(!FolderExist(myProject.getProperty("TOMCATROOT1")+"/webapps/App_1,"+myProject.getProperty("LICDIR")+","+myProject.getProperty("SHAREDJARS")+","+myProject.getProperty("SCHEDULEDREPORT")+"_1,"+myProject.getProperty("EXTRACT_LOCATION")))
			{
				BuildException e = new BuildException("Some Configuration Missing!!!");
				throw e;
			}
		}
	}
	
}
