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
	
	public void execute(){
		String[] paths = path.split(delimiter);
		
		if(order.equals("0")){
			Arrays.sort(paths);
		}else{
			Arrays.sort(paths,Collections.reverseOrder());
		}
		this.getProject().setProperty(property,doJoin(paths, delimiter));
	}
	private String doJoin(String[] paths, String delimiter) {
		StringBuilder retStr = new StringBuilder();
		if(paths.length>0){
			for(String str:paths){
				retStr.append(delimiter).append(str);
			}
			return retStr.toString().substring(delimiter.length());
		}else{
			return "";
		}
	}

}
