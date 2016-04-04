import java.util.Arrays;
import java.util.Collections;

import org.apache.tools.ant.Task;

public class ParsePath extends Task{
	String path;
	String delimiter;
	String order;
	String property;
	public void setProperty(String property) {
		this.property = property;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public void setDelimeter(String delimiter) {
		this.delimiter = delimiter;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	
	@SuppressWarnings("deprecation")
	public void execute(){
		String[] paths = path.split(delimiter);
		
		if(order.equals("0")){
			Arrays.sort(paths);
		}else{
			Arrays.sort(paths,Collections.reverseOrder());
		}
		project.setProperty(property,String.join(delimiter, paths));
	}

}
