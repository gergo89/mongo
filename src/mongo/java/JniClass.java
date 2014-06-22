
import java.text.DecimalFormat;

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
			engine.eval("var AI = Java.type('java.util.concurrent.atomic.LongAdder');");
			engine.eval("var Random = Java.type('java.util.Random')");
			engine.eval("DoubleStream = Java.type('java.util.stream.DoubleStream')");
			engine.eval("var Array = Java.type('java.util.Arrays')");
			engine.eval("var mongo = new Mongo();");
		} catch (ScriptException e1) {
      System.out.println(debugTag + "ScriptExceptionError in Mongo type creation: (JniClass:line16): " + e1.getMessage());
		} catch (Exception e2) {
			System.out.println(debugTag + "ExceptionError in Mongo type creation: (JniClass:line16): " + e2.getMessage());
		}
	}
	
	/*  
	*	public java.lang.Object invokeFunctionNashorn(java.lang.String);
    *	descriptor: (Ljava/lang/String;)Ljava/lang/Object;
    */
	public Object invokeFunctionNashorn(String code) {	
		System.out.println(debugTag + "invokeFunctionNashorn starts.");
		
		System.out.println(debugTag + "engine.eval() starts.");
		try {
			engine.eval(code);
			System.out.println(debugTag + "engine.eval() is done.");
		} catch (ScriptException e1) {
			System.out.println(debugTag + "ScriptExceptionError in engine eval(code): (JniClass:line33): " + e1.getMessage());
		} catch (Exception e2) {
			System.out.println(debugTag + "ExceptionError in engine eval(code): (JniClass:line33): " + e2.getMessage());
		}
		
		long startTime, endTime, duration = 0;
		try {
			startTime = System.nanoTime();
			Object part_res = invocable.invokeFunction("_funcs");
			endTime = System.nanoTime();
			duration = endTime - startTime;
			if (part_res != null) { 
				JOptionPane.showMessageDialog(null, part_res, "Result of the evaluation", JOptionPane.OK_CANCEL_OPTION);
				System.out.println(debugTag + "invokeFunctionNashorn method returns with valid result. \n" + part_res.toString() + "\n\n");
				double dur_inSeconds = ((double)duration / 1000000000);
				System.out.println(debugTag + "runTime: " + new DecimalFormat("##.####").format(dur_inSeconds) + " sec"); 
				
				return part_res.toString();
			}
		} catch (NoSuchMethodException e) {
			System.out.println(debugTag + "NoSuchMethodExceptionError in invoke _funcs: (JniClass:line42)" + e.getMessage());
		} catch (ScriptException e) {
			System.out.println(debugTag + "ScriptExceptionError in invoke _funcs: (JniClass:line42): " + e.getMessage());
		} catch (Exception e) {
			System.out.println(debugTag + "ExceptionError in invoke _funcs: (JniClass:line42): " + e.getMessage());
		}
		
		double dur_inSeconds = ((double)duration / 1000000000);
		System.out.println(debugTag + "runTime: " + new DecimalFormat("##.####").format(dur_inSeconds) + " sec"); 
		System.out.println(debugTag + "invokeFunctionNashorn method returns with \"" + errorReturn + "\".\n\n");
		return errorReturn;

	}
	
}
