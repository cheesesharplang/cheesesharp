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
	
	// Basically like metaclasses in Python, super easy to implement.
	public static class ChTypeType extends ChType {
		
		public final ChType of;
		
		public ChTypeType(ChType type) {
			super(type.friendlyName);
			this.of = type;
		}
		
	}
	
}
