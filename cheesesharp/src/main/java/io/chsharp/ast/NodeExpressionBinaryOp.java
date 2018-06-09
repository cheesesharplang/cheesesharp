package io.chsharp.ast;

import io.chsharp.visitor.Visitor;

public class NodeExpressionBinaryOp extends NodeExpression {
	
	public final NodeExpression left, right;
	
	public NodeExpressionBinaryOp(NodeExpression left, NodeExpression right) {
		this.left = left;
		this.right = right;
	}
	
	@Override
	public <T> T accept(Visitor<T> vis) {
		return vis.visit(this);
	}
	
}
