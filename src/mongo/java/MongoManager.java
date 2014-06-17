import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import javax.script.ScriptException;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;


public class MongoManager {
  static final String debugTag = "JAVA-VM MESSAGE: ";
	private MongoClient mongoClient;
  
  
  
  public MongoManager() {
    try {
    //  mongoClient = new MongoClient();
    //  System.out.println(debugTag + "MongoManager created.");
    } catch(Exception e) {
			e.printStackTrace();
		}
  }
  
  
  public void PrintCollection(){
	  System.out.println("PrintCollection started");
	  
	  try {
		mongoClient = new MongoClient();
	} catch (UnknownHostException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	  
	    
	  
	  System.out.println("MongoClient created");
	  
	  DB db = mongoClient.getDB("test");
	  
	  Set<String> colls = db.getCollectionNames();

	  for (String s : colls) {
	      System.out.println(s);
	  }
	  
	  }
}