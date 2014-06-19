import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;


public class MongoManager {
	static final String debugTag = "JAVA-VM MESSAGE: ";
	private MongoClient mongoClient;
	public DB db;
	public DBCollection coll;
	
	public MongoManager() {
		try {
			mongoClient = new MongoClient();
			System.out.println(debugTag + "MongoClient created");
		} catch (UnknownHostException uh_exc) {
			System.out.println(debugTag + "UnknownHostExceptionError in mongoClient creation: (MongoManager:line27): " + uh_exc.getMessage());
		}
		
		db = mongoClient.getDB("test");
		coll = db.getCollection("test");
	}
	
	
	public void save(Map entry) {
		BasicDBObject doc = new BasicDBObject();
		Iterator entries = entry.entrySet().iterator();
		while(entries.hasNext()) {
			Entry thisEntry = (Entry) entries.next();
			Object key = thisEntry.getKey();
			Object value = thisEntry.getValue();
			//System.out.println(debugTag + key + " -> " + value);
			doc.append(key.toString(), value);
		}
		coll.save(doc);
	}
	
	public DBObject findOne(Map entry) {
		BasicDBObject searchQuery = new BasicDBObject();
		Iterator entries = entry.entrySet().iterator();
		while(entries.hasNext()) {
			Entry thisEntry = (Entry) entries.next();
			Object key = thisEntry.getKey();
			Object value = thisEntry.getValue();
			//System.out.println(debugTag + key + " -> " + value);
			searchQuery.append(key.toString(), value);
		}
		
		DBCursor cursor = coll.find(searchQuery);
		if (cursor.hasNext()) {
			return cursor.next();
		}
		
		System.out.println(debugTag + "findOne() query has no result.");
		return null;
	}
	
	
	public void printCollection() {
		System.out.println(debugTag + "printCollection() starts.");
		
		try {
			mongoClient = new MongoClient();
			System.out.println(debugTag + "MongoClient created");
		} catch (UnknownHostException uh_exc) {
			System.out.println(debugTag + "UnknownHostExceptionError in mongoClient creation: (MongoManager:line76): " + uh_exc.getMessage());
		}
		
		db = mongoClient.getDB("test");
		coll = db.getCollection("test");
		
		
		try {
			Set<String> colls = db.getCollectionNames();
			
			for (String s : colls) {
				System.out.println(debugTag + "collection: " + s);
			}
			
		} catch (MongoException mng_exc) {
			System.out.println(debugTag + "MongoException in getCollectionNames(): (MongoManager:line91): " + mng_exc.getMessage());
		}

		System.out.println(debugTag + "printCollection() done."); 
	}
}