package com.ikilun.groovy;

import java.util.Collection;
import java.util.Map;

import javax.script.Compilable;
import javax.script.CompiledScript;
import javax.script.ScriptContext;
import javax.script.ScriptException;
import javax.script.SimpleScriptContext;

public class CachedScript {
	private static final long serialVersionUID = -5632756060640454376L;
	private CompiledScript compiledScript;
	public CachedScript() {
	}

	public CachedScript(Compilable scriptEngine, String script) {
		try {
			compiledScript = scriptEngine.compile(script);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public boolean isValid(){
		return compiledScript!=null;
	}

	public <T> ScriptResult<T> run(Map<String, Object> context){
		ScriptContext ctx = new SimpleScriptContext();
		if(context!=null){
			put(ctx, context);
		}
		ScriptResult result = new ScriptResult(ctx);
		if(compiledScript==null){
			result.setErrorMsg("script cannot be compiled!!");
			return result;
		}
		try {
			Object retval = compiledScript.eval(ctx);
			result.setRetval(retval);
		} catch (ScriptException e) {
			e.printStackTrace();
			result.setErrorMsg("execption:" + e + "," + e.getMessage());

		}
		return result;
	}

	private static void put(ScriptContext ctx, Map<String, Object> context) {
		for (Map.Entry<String, Object> entry : context.entrySet()) {
			Object value = entry.getValue();
//			if(value!=null && !BeanUtil.isSimpleProperty(value.getClass())){
//				if(value instanceof Collection){
//					ctx.setAttribute(entry.getKey(), BeanUtil.getBeanMapList((Collection) value, true), ScriptContext.ENGINE_SCOPE);
//				}else if(value instanceof Util4Script){
//					ctx.setAttribute(entry.getKey(), value, ScriptContext.ENGINE_SCOPE);
//				}else{
//					ctx.setAttribute(entry.getKey(), BeanUtil.getBeanMap(value), ScriptContext.ENGINE_SCOPE);
//				}
//			}else{
//				ctx.setAttribute(entry.getKey(), value, ScriptContext.ENGINE_SCOPE);
//			}
			ctx.setAttribute(entry.getKey(), value, ScriptContext.ENGINE_SCOPE);
		}
	}
	public static class ScriptResult<T> {
		private ScriptContext ctx;
		private T retval;
		private String errorMsg;
		public ScriptResult(ScriptContext ctx){
			this.ctx = ctx;
		}
		public T getRetval() {
			return retval;
		}
		public Object getAttribute(String name){
			return ctx.getAttribute(name);
		}
		public void setRetval(T retval) {
			this.retval = retval;
		}
		public String getErrorMsg() {
			return errorMsg;
		}
		public void setErrorMsg(String errorMsg) {
			this.errorMsg = errorMsg;
		}
		public boolean hasError(){
			return this.errorMsg!=null;
		}
	}
	public CompiledScript getCompiledScript() {
		return compiledScript;
	}
}
