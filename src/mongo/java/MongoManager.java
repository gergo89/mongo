import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

public class MongoManager {
  static final String debugTag = "JAVA-VM MESSAGE: ";
	private MongoClient mongoClient;
  
  
  public MongoManager() {
    try {
      mongoClient = new MongoClient();
      
    } catch(Exception e) {
			e.printStackTrace();
		}
  }
}