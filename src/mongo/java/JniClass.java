import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.swing.JOptionPane;

import com.mongodb.MongoClient;

//import com.mongodb.MongoClient;






public class JniClass {
	static final ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
	static final Invocable invocable = (Invocable) engine;
	static final String debugTag = "JAVA-VM MESSAGE: ";
	static {
		try {
			//engine.eval("var TimerTask =  Java.type('java.util.TimerTask')");
			engine.eval("var Mongo = Java.type('MongoManager')");
			//engine.eval("var Mongo = Java.type('com.mongodb.MongoClient')");
		} catch (ScriptException e) {
			e.printStackTrace();
		}
	}
	
	static {
		try {
			 //MongoClient mongoClient = new MongoClient();
			//db = mongoClient.getDB("test");
			//DBCollection myCollection = db.getCollection("test");
      //System.out.println(debugTag + "DB connection is successfull.");
    } catch(Exception e) {
			e.printStackTrace();
		}
	}

	
	
	//public java.lang.String invokeFunctionNashorn(java.lang.String);
	//descriptor: (Ljava/lang/String;)Ljava/lang/Object;
	public Object invokeFunctionNashorn(String code) {	

		Object result = null;
		JOptionPane.showMessageDialog(null, code, "Code", JOptionPane.OK_CANCEL_OPTION);
		System.out.println(debugTag + "Here we start java method with the following argument: " + code);
		
		System.out.println(debugTag + "engine eval of code starts");
		try {
			
			
			
			engine.eval(code);
			System.out.println(debugTag + "engine eval of code done");
		} catch (ScriptException e1) {
			System.out.println(debugTag + "ScriptExceptionError in engine eval(code): (JniClass:line24): " + e1.getMessage());
			e1.printStackTrace();
		} catch (Exception e2) {
			System.out.println(debugTag + "ExceptionError in engine eval(code): (JniClass:line24): " + e2.getMessage());
			e2.printStackTrace();
		}
		
	
		
		try {
			Object part_res = invocable.invokeFunction("_funcs"); 
			if (part_res != null) { 
				System.out.println(debugTag + "java method returns with result.\n\n"); 
					return part_res.toString();
			}
			
		} catch (NoSuchMethodException e) {
			System.out.println(debugTag + "No such function exception in java");
		} catch (ScriptException e) {
			System.out.println(debugTag + "ScriptExceptionError in invoke _funcs: (JniClass:line53): " + e.getMessage());
			//e.printStackTrace();
		} catch (Exception e2) {
			System.out.println(debugTag + "ExceptionError in invoke _funcs: (JniClass:line53): " + e2.getMessage());
			//e2.printStackTrace();
		}
		
		
		System.out.println(debugTag + "java method returns.\n\n");
		return "__errorInFunctionInvocation";

	}
	
	//descriptor: ([Ljava/lang/String;)Ljava/lang/Object;
	public static Object main(String[] args) {
		
		ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
	
		try {
			engine.eval(args[0]);
		} catch (ScriptException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		Invocable invocable = (Invocable) engine;
		
		JOptionPane.showMessageDialog(null, args[0], "Code", JOptionPane.OK_CANCEL_OPTION);
		
		System.out.println("Here we start JVM");
		System.out.println("Arguments sent to this program:");
		if (args.length == 0) {
			System.out.println("(None)");
		} else {
			for (int i=0; i<args.length; i++) {
				System.out.print(args[i] + " ");
			}
			System.out.println();
		}
		
		Object result = null;
		try {
			result = invocable.invokeFunction("_funcs");
			
			System.out.println("result: ");
			System.out.println(result);
			
			return result.toString();
			
		} catch (NoSuchMethodException e) {
			
			System.out.println("No such function exception in java");
		} catch (ScriptException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "javaFoo";	
	}
}
