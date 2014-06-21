import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
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
	
	public void selectCollection(String collName) {
		coll = db.getCollection(collName);
	}
  
	public void getDB(String dbName) {
		db = mongoClient.getDB(dbName);
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
		
		System.out.println(debugTag + "findOne() query has no result with the following query: " + searchQuery);
		return null;
	}
  
	public ArrayList<Object> find(Map entry) {
		BasicDBObject searchQuery = new BasicDBObject();
		Iterator entries = entry.entrySet().iterator();
		while(entries.hasNext()) {
			Entry thisEntry = (Entry) entries.next();
			Object key = thisEntry.getKey();
			Object value = thisEntry.getValue();
			//System.out.println(key + " -> " + value);
			searchQuery.append(key.toString(), value);
		}
		ArrayList<Object> result = new ArrayList<Object>();
		DBCursor cursor = coll.find(searchQuery);
		while (cursor.hasNext()) {
			result.add(cursor.next());
		}

		return result;
	}
	
	public ArrayList<Object> find(Map entry, String coll_name) {
		coll = db.getCollection(coll_name);
		BasicDBObject searchQuery = new BasicDBObject();
		Iterator entries = entry.entrySet().iterator();
		while(entries.hasNext()) {
			Entry thisEntry = (Entry) entries.next();
			Object key = thisEntry.getKey();
			Object value = thisEntry.getValue();
			//System.out.println(key + " -> " + value);
			searchQuery.append(key.toString(), value);
		}
		ArrayList<Object> result = new ArrayList<Object>();
		DBCursor cursor = coll.find(searchQuery);
		while (cursor.hasNext()) {
			result.add(cursor.next());
		}

		return result;
	}
	
	public List<Object> getArrayData(Map entry) {
		coll = db.getCollection("mongoDemo_2");
		BasicDBObject searchQuery = new BasicDBObject();
		Iterator entries = entry.entrySet().iterator();
		while(entries.hasNext()) {
			Entry thisEntry = (Entry) entries.next();
			Object ekey = thisEntry.getKey();
			Object value = thisEntry.getValue();
			//System.out.println(key + " -> " + value);
			searchQuery.append(ekey.toString(), value);
		}
		
		DBCursor cursor = coll.find(searchQuery);
		if (cursor.hasNext()) {
			return Arrays.asList(cursor.next().get("value"));
		}

		return new ArrayList<Object>();
	}
	
	public void printCollectionNames() {
		System.out.println(debugTag + "printCollectionNames() starts.");

		try {
			Set<String> colls = db.getCollectionNames();
			
			for (String s : colls) {
				System.out.println(debugTag + "collection: " + s);
			}
			
		} catch (MongoException mng_exc) {
			System.out.println(debugTag + "MongoException in getCollectionNames(): (MongoManager:line91): " + mng_exc.getMessage());
		}

		System.out.println(debugTag + "printCollectionNames() done."); 
	}
	
	public void createMongoDemo_2_DB(int size) {
		coll = db.getCollection("mongoDemo_2");
		 
		ArrayList<Integer> numbers = new ArrayList<Integer>();
		for(int i = 1; i <= size; i++) {
			numbers.add(i);
		}
		BasicDBObject arrayEntry = new BasicDBObject();
		arrayEntry.append("name", "Numbers");
		arrayEntry.append("value", numbers);
		coll.insert(arrayEntry);
	}
	
	public void createMongoDemo_3_DB() {
		coll = db.getCollection("mongoDemo_3");
		Random randomGenerator = new Random();
		for(int i=0; i < 10; i++) {
			BasicDBObject entry = new BasicDBObject();
			
			entry.append("name", ("name" + i));
			entry.append("age", randomGenerator.nextInt(99)+1);
			String gender = randomGenerator.nextBoolean() ? "male" : "female"; 
			entry.append("gender", gender);
				
			coll.save(entry);
		}
	}
	
}