package io.chsharp.ast;

import io.chsharp.visitor.Visitor;

public class NodeExpressionDecimal extends NodeExpression {
	
	public NodeDecimal decimal;
	
	public NodeExpressionDecimal(NodeDecimal decimal) {
		this.decimal = decimal;
	}
	
	@Override
	public <T> T accept(Visitor<T> vis) {
		return vis.visit(this);
	}
	
}
