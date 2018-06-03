package io.chsharp.visitor;

import io.chsharp.ChType;
import io.chsharp.Scope;
import io.chsharp.ast.NodeCompilationUnit;
import io.chsharp.ast.NodeDecimal;
import io.chsharp.ast.NodeExpressionDecimal;
import io.chsharp.ast.NodeIdentifier;
import io.chsharp.ast.NodeReferenceIdentifier;
import io.chsharp.ast.NodeStmt;
import io.chsharp.ast.NodeStmtAssignment;

public class VisitorSemanticAnalyzer extends Visitor<ChType> {
	
	public Scope<ChType> scope = new Scope<>(null);
	
	public VisitorSemanticAnalyzer() {
		scope.set("Gouda", ChType.DECIMAL);
	}
	
	public ChType visit(NodeCompilationUnit cunit) {
		for (NodeStmt stmt : cunit.statements) {
			stmt.accept(this);
		}
		return null;
	}
	
	public ChType visit(NodeStmtAssignment stmtassign) {
		ChType left = stmtassign.left.accept(this);
		ChType right = stmtassign.right.accept(this);
		if (stmtassign.type != null) {
			scope.set(stmtassign.left.toString(), right);
			
			if (stmtassign.type.accept(this) != right) {
				throw new SemanticAnalysisException("Definition expression type " + right
						+ " does not match user-specified type " + stmtassign.type + ".");
			}
		} else {
			if (left == null) {
				throw new SemanticAnalysisException(
						"Definition reference " + stmtassign.left + " does not exist in scope.");
			}

			if (left != right) {
				throw new SemanticAnalysisException(
						"Assignment reference type " + left + " does not match expression type " + right + ".");
			}
		}

		return right;
	}
	
	public ChType visit(NodeExpressionDecimal exprdecimal) {
		return visit(exprdecimal.decimal);
	}
	
	public ChType visit(NodeDecimal decimal) {
		return ChType.DECIMAL;
	}
	
	@Override
	public ChType visit(NodeIdentifier ident) {
		return scope.get(ident.text);
	}
	
	public ChType visit(NodeReferenceIdentifier identref) {
		return visit(identref.ident);
	}
	
}
