package io.chsharp;

import java.util.HashMap;

public class Scope<T> {
	
	private Scope<T> parent;
	private HashMap<String, T> map = new HashMap<>();
	
	public Scope(Scope<T> parent) {
		this.parent = parent;
	}
	
	public T get(String key) {
		if (map.containsKey(key))
			return map.get(key);
		else if (parent != null)
			return parent.get(key);
		else
			return null;
	}
	
	public void set(String key, T val) {
		Scope<T> scope = this;
		do {
			if(scope.map.containsKey(key)) {
				break;
			}
		} while ((scope = scope.parent) != null);
		if(scope == null) scope = this;
		
		scope.map.put(key, val);
	}
	
	public boolean contains(String key) {
		if (map.containsKey(key))
			return true;
		else if (parent != null)
			return parent.contains(key);
		else
			return false;
	}
	
}
