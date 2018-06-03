package io.chsharp.visitor;

import io.chsharp.ast.NodeCompilationUnit;
import io.chsharp.ast.NodeDecimal;
import io.chsharp.ast.NodeExpressionDecimal;
import io.chsharp.ast.NodeIdentifier;
import io.chsharp.ast.NodeReferenceIdentifier;
import io.chsharp.ast.NodeStmtAssignment;

public abstract class Visitor<T> {
	
	public T visit(NodeStmtAssignment stmtassign) {
		return null;
	}
	
	public T visit(NodeExpressionDecimal decimal) {
		return null;
	}
	
	public T visit(NodeDecimal decimal) {
		return null;
	}
	
	public T visit(NodeCompilationUnit cunit) {
		return null;
	}
	
	public T visit(NodeReferenceIdentifier identref) {
		return null;
	}

	public T visit(NodeIdentifier ident) {
		return null;
	}
	
}
