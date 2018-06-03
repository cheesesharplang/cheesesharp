package io.chsharp.ast;

import io.chsharp.visitor.Visitor;

public class NodeStmtAssignment extends NodeStmt {
	
	public NodeIdentifier type; // Defined if definition.
	public NodeReference left;
	public NodeExpression right;
	
	@Override
	public <T> T accept(Visitor<T> vis) {
		return vis.visit(this);
	}
	
}
