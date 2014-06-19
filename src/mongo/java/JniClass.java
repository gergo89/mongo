
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.swing.JOptionPane;

public class JniClass {
	static final ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
	static final Invocable invocable = (Invocable) engine;
	static final String debugTag = "JAVA-VM MESSAGE: ";
  static final String errorReturn = "__errorInFunctionInvocation";
	static {
		try {
			engine.eval("var Mongo = Java.type('MongoManager')");
		} catch (ScriptException e1) {
      System.out.println(debugTag + "ScriptExceptionError in Mongo type creation: (JniClass:line17): " + e1.getMessage());
		} catch (Exception e2) {
			System.out.println(debugTag + "ExceptionError in Mongo type creation: (JniClass:line19): " + e2.getMessage());
		}
	}
	
	//public java.lang.String invokeFunctionNashorn(java.lang.String);
	//descriptor: (Ljava/lang/String;)Ljava/lang/Object;
	public Object invokeFunctionNashorn(String code) {	
		Object result = null;
		JOptionPane.showMessageDialog(null, code, "Code", JOptionPane.OK_CANCEL_OPTION);
		System.out.println(debugTag + "invokeFunctionNashorn starts with the following argument: \n" + code +"\n");
		
		System.out.println(debugTag + "engine.eval() starts.");
		try {
			engine.eval(code);
			System.out.println(debugTag + "engine.eval() is done.");
		} catch (ScriptException e1) {
			System.out.println(debugTag + "ScriptExceptionError in engine eval(code): (JniClass:line35): " + e1.getMessage());
		} catch (Exception e2) {
			System.out.println(debugTag + "ExceptionError in engine eval(code): (JniClass:line37): " + e2.getMessage());
		}
		
	
		
		try {
			Object part_res = invocable.invokeFunction("_funcs"); 
			if (part_res != null) { 
				System.out.println(debugTag + "invokeFunctionNashorn method returns with valid result: \n" + part_res.toString() + "\n\n"); 
				return part_res.toString();
			}
			
		} catch (NoSuchMethodException e) {
			System.out.println(debugTag + "NoSuchMethodExceptionError in invoke _funcs: (JniClass:line50)" + e.getMessage());
		} catch (ScriptException e) {
			System.out.println(debugTag + "ScriptExceptionError in invoke _funcs: (JniClass:line52): " + e.getMessage());
		} catch (Exception e2) {
			System.out.println(debugTag + "ExceptionError in invoke _funcs: (JniClass:line54): " + e2.getMessage());
		}
		
		
		System.out.println(debugTag + "invokeFunctionNashorn method returns with \"" + errorReturn + "\".\n\n");
		return errorReturn;

	}
	
}
