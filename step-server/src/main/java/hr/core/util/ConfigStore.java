/**
 * 
 */
package hr.core.util;

import java.io.File;
import java.io.IOException;

/**
 * @author Gavin Yu
 *
 */
public class ConfigStore {
    private File localDirectory;

    private final static String local="javatemp";
    public ConfigStore(){
    	this(local);
    }
	public ConfigStore(String local){
		File dir = new File(System.getProperty("user.home"));
		localDirectory = new File(dir.getAbsolutePath()+File.separator+local);
		if(!localDirectory.exists()){
			localDirectory.mkdir();
		}
	}
	

	 public File getFile(String file){
		return new File(localDirectory.getAbsoluteFile()+File.separator+file);
	 }
	 
	 public void deleteFile(String file){
		 File f = new File(localDirectory.getAbsoluteFile()+File.pathSeparator+file);
		 if(f.exists()){
			 f.delete();
		 }
	     
	 } 
	 
	 public File getDirectory(){
		 return localDirectory;
	 }
	 
	 public void deleteDirectory(){
		 if(localDirectory.exists()){
			 localDirectory.delete();
		 }
		 
	 }
	 
	 public void clearDirectory(){
		 File[] files = this.localDirectory.listFiles();
		 for(int i=0;i<files.length;i++){
			 if(files[i].exists()){
				 files[i].delete();
			 }
		 }
	 }
	 

	 
	 public File getChildDirectory(String dirName){
		 File childDir = new File(localDirectory.getAbsolutePath()+File.separator+dirName);
		 if(!childDir.exists()){
			 childDir.mkdir();
		 }
		 return childDir;
	 }
	 
	 public void deleteChildDirectory(String dirName){
		 File childDir = new File(localDirectory.getAbsolutePath()+File.separator+dirName);
		 if(childDir.exists()){
			 childDir.delete();
		 }
		 
	 }
	 
	 public void clearChildDirectory(String dirName){
		 File childDir = new File(localDirectory.getAbsolutePath()+File.separator+dirName);
		 if(childDir.exists()&&childDir.isDirectory()){
			 File[] files = childDir.listFiles();
			 for(int i=0;i<files.length;i++){
				 if(files[i].exists()){
					 files[i].delete();
				 }
			 }
		 }
		 
	 }
	
}
