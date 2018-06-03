package io.chsharp.ast;

import io.chsharp.visitor.Visitor;

public class NodeIdentifier extends Node {
	
	public String text;
	
	public NodeIdentifier(String text) {
		this.text = text;
	}

	@Override
	public <T> T accept(Visitor<T> vis) {
		return vis.visit(this);
	}
	
	@Override
	public String toString() {
		return text;
	}

}
