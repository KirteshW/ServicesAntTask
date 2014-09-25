import java.io.File;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;


public class validatePaths extends Task {

	public void execute() {
		checkforApp1();
		checkforApp2();
		checkforDB1();
		checkforDB2();
		checkPerifery();
	}

	@SuppressWarnings("deprecation")
	private void checkPerifery() {
		if(project.getProperty("LICDIR").lastIndexOf("/")!=project.getProperty("LICDIR").length()-1)
		{
			BuildException e = new BuildException("Licens Folder location must end with '/'");
			throw e;
		}
	}

	@SuppressWarnings("deprecation")
	private void checkforDB2() {
		if(project.getProperty("DB2").equals("true"))
		{
			log("Database 2 needs to install");
			if(!project.getProperty("DBLIST").toUpperCase().equals("ALL"))
			{
				String[] dblist= project.getProperty("DBLIST").split(",");
				for(String x:dblist)
				{
					if(!FileExist(project.getProperty("DBBANK") + "/" + x + ".zip"))
					{
						BuildException e = new BuildException("Database Zip " + project.getProperty("DBBANK") + "/" + x + ".zip" + " Not Found");
						throw e;
					}
				}
			}
			else
			{
				if(!FolderExist(project.getProperty("DBBANK")))
				{
					BuildException e = new BuildException("DB Bank Location " + project.getProperty("DBBANK") + " Not Found");
					throw e;
				}
			}
			if(!FolderExist(project.getProperty("UPGRADE_LOCATION2")+","+project.getProperty("DBDIR")))
			{
				BuildException e = new BuildException("Upgrade Location or MySQL data Directory " + project.getProperty("UPGRADE_LOCATION1") + "," + project.getProperty("DBDIR") + " Not Found");
				throw e;
			}
		}
		else
		{
			log("Database 2 not to be install");
			if(!project.getProperty("DBLIST").toUpperCase().equals("ALL"))
			{
				String[] dblist= project.getProperty("DBLIST").split(",");
				for(String x:dblist)
				{
					if(!FolderExist(project.getProperty("DBDIR") + "/" + x + "_2"))
					{
						BuildException e = new BuildException("Database " + project.getProperty("DBDIR") + "/" + x + "_2 Not Found");
						throw e;
					}
				}
			}
			else
			{
				File folder = new File(project.getProperty("DBBANK"));
				File[] files = folder.listFiles();
				
				for(File f:files)
				{
					if(!FolderExist(project.getProperty("DBDIR") + "/" + f.getName()+"_2"))
					{
						BuildException e = new BuildException("DB Bank Location " + project.getProperty("DBBANK") + f.getName()+"_2 Not Found");
					throw e;
					}
				}
			}
			if(!FolderExist(project.getProperty("UPGRADE_LOCATION2")+","+project.getProperty("DBDIR")))
			{
				BuildException e = new BuildException("Upgrade Location or MySQL data Directory " + project.getProperty("UPGRADE_LOCATION2") + "," + project.getProperty("DBDIR") + " Not Found");
				throw e;
			}
		}
		
	}

	@SuppressWarnings("deprecation")
	private void checkforDB1() throws BuildException {
		if(project.getProperty("DB1").equals("true"))
		{
			log("Database 1 needs to install");
			if(!project.getProperty("DBLIST").toUpperCase().equals("ALL"))
			{
				String[] dblist= project.getProperty("DBLIST").split(",");
				for(String x:dblist)
				{
					if(!FileExist(project.getProperty("DBBANK") + "/" + x + ".zip"))
					{
						BuildException e = new BuildException("Database Zip " + project.getProperty("DBBANK") + "/" + x + ".zip" + " Not Found");
						throw e;
					}
				}
			}
			else
			{
				if(!FolderExist(project.getProperty("DBBANK")))
				{
					BuildException e = new BuildException("DB Bank Location " + project.getProperty("DBBANK") + " Not Found");
					throw e;
				}
			}
			if(!FolderExist(project.getProperty("UPGRADE_LOCATION1")+","+project.getProperty("DBDIR")))
			{
				BuildException e = new BuildException("Upgrade Location or MySQL data Directory " + project.getProperty("UPGRADE_LOCATION1") + "," + project.getProperty("DBDIR") + " Not Found");
				throw e;
			}
		}
		else
		{
			log("Database 1 not to be install");
			if(!project.getProperty("DBLIST").toUpperCase().equals("ALL"))
			{
				String[] dblist= project.getProperty("DBLIST").split(",");
				for(String x:dblist)
				{
					if(!FolderExist(project.getProperty("DBDIR") + "/" + x + "_1"))
					{
						BuildException e = new BuildException("Database " + project.getProperty("DBDIR") + "/" + x + " Not Found");
						throw e;
					}
				}
			}
			else
			{
				File folder = new File(project.getProperty("DBBANK"));
				File[] files = folder.listFiles();
				
				for(File f:files)
				{
					if(!FolderExist(project.getProperty("DBDIR") + "/" + f.getName()+"_1"))
					{
						BuildException e = new BuildException("DB Bank Location " + project.getProperty("DBBANK") + " Not Found");
					throw e;
					}
				}
			}
			if(!FolderExist(project.getProperty("UPGRADE_LOCATION1")+","+project.getProperty("DBDIR")))
			{
				BuildException e = new BuildException("Upgrade Location or MySQL data Directory " + project.getProperty("UPGRADE_LOCATION1") + "," + project.getProperty("DBDIR") + " Not Found");
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
	
	
	@SuppressWarnings("deprecation")
	private void checkforApp2() {
		if(project.getProperty("APP1").equals("true"))
		{
			log("Application 2 needs to install");
			if(!FileExist(project.getProperty("AAP_LOCATION2")) || !FolderExist(project.getProperty("PATCH_LOCATION2") +","+project.getProperty("TOMCATROOT")+","+project.getProperty("LICDIR")+","+project.getProperty("SHAREDJARS")+","+project.getProperty("SCHEDULEDREPORT")+"_2,"+project.getProperty("EXTRACT_LOCATION")))
			{
				BuildException e = new BuildException("Some Configuration Missing!!!");
				throw e;
			}
		}
		else
		{
			log("Application 2 not to be install");
			if(!FolderExist(project.getProperty("TOMCATROOT")+"/webapps/App_2,"+project.getProperty("LICDIR")+","+project.getProperty("SHAREDJARS")+","+project.getProperty("SCHEDULEDREPORT")+"_2,"+project.getProperty("EXTRACT_LOCATION")))
			{
				BuildException e = new BuildException("Some Configuration Missing!!!");
				throw e;
			}
		}
		
	}

	@SuppressWarnings("deprecation")
	private void checkforApp1() {
		if(project.getProperty("APP1").equals("true"))
		{
			log("Application 1 needs to install");
			if(!FileExist(project.getProperty("AAP_LOCATION1")) || !FolderExist(project.getProperty("PATCH_LOCATION1") +","+project.getProperty("TOMCATROOT")+","+project.getProperty("LICDIR")+","+project.getProperty("SHAREDJARS")+","+project.getProperty("SCHEDULEDREPORT")+"_1,"+project.getProperty("EXTRACT_LOCATION")))
			{
				BuildException e = new BuildException("Some Configuration Missing!!!");
				throw e;
			}
		}
		else
		{
			log("Application 1 not to be install");
			if(!FolderExist(project.getProperty("TOMCATROOT")+"/webapps/App_1,"+project.getProperty("LICDIR")+","+project.getProperty("SHAREDJARS")+","+project.getProperty("SCHEDULEDREPORT")+"_1,"+project.getProperty("EXTRACT_LOCATION")))
			{
				BuildException e = new BuildException("Some Configuration Missing!!!");
				throw e;
			}
		}
	}
	
}
