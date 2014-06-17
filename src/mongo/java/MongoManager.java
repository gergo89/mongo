import java.util.Set;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

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
  
  
//  public void PrintCollection(String dbName){
//	  System.out.println("PrintCollection started");
//	  
//	  DB db = mongoClient.getDB(dbName);
//	  
//	  Set<String> colls = db.getCollectionNames();
//
//	  for (String s : colls) {
//	      System.out.println(s);
//	  }
//	  
//  }
}