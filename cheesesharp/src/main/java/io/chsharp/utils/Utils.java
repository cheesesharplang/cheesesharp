package io.chsharp.utils;

public final class Utils {
	
	private Utils() {
	}
	
	@SafeVarargs
	public static <T> String join(String delim, T... args) {
		StringBuilder builder = new StringBuilder();
		for(T o : args) {
			builder.append(delim);
			builder.append(o);
		}
		return builder.substring(delim.length());
	}
	
}
