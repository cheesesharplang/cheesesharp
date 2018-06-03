package io.chsharp.ast;

import io.chsharp.visitor.Visitor;

public class NodeDecimal extends Node {

	public double value;
	
	public NodeDecimal(double value) {
		this.value = value;
	}
	
	@Override
	public <T> T accept(Visitor<T> vis) {
		return vis.visit(this);
	}
	
}
