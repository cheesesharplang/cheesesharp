package io.chsharp;

import io.chsharp.utils.Utils;

public class ChType {
	
	public static final ChType DECIMAL = new ChType("Gouda", 10);
	
	public String friendlyName;
	public int size;
	
	public ChType(String friendlyName, int size) {
		this.friendlyName = friendlyName;
		this.size = size;
	}
	
	@Override
	public String toString() {
		return friendlyName;
	}
	
	public static class ChMetaType extends ChType {

		public final ChType of;
		
		public ChMetaType(ChType of) {
			super("Class(" + of.friendlyName + ")", 0);
			this.of = of;
		}
		
	}
		
	public static class ChFunc extends ChType {

		public final ChType ret;
		public final ChType[] args;
		
		public ChFunc(ChType ret, ChType... args) {
			super(ret.friendlyName + "(" + Utils.join(", ", args) + ")", 0);
			this.ret = ret;
			this.args = args;
		}
		
	}
	
}
