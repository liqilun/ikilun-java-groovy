package com.ikilun.groovy;

import java.util.HashMap;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import com.ikilun.groovy.CachedScript.ScriptResult;

public class Test {
	public static void main(String[] args) {
//		ScriptEngineManager factory = new ScriptEngineManager();
//		ScriptEngine engine = factory.getEngineByName("groovy");
//		try {
//			Object obj = engine.eval("2*3");
//			System.out.println(obj);
//		} catch (ScriptException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		CachedScript script = ScriptEngineUtil.buildCachedScript("2*3", true);
		ScriptResult<Integer> result = script.run(new HashMap<String, Object>());
		System.out.println(result.getRetval());
	}
}
