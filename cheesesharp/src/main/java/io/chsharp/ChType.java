package io.chsharp;

public class ChType {

	public static final ChType DECIMAL = new ChType("Gouda");
	
	public String friendlyName;
	
	public ChType(String friendlyName) {
		this.friendlyName = friendlyName;
	}
	
	@Override
	public String toString() {
		return friendlyName;
	}
	
}
