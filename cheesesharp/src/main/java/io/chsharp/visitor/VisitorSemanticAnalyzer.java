package io.chsharp.visitor;

import java.util.HashMap;

import io.chsharp.ChType;
import io.chsharp.ChType.ChMetaType;
import io.chsharp.Scope;
import io.chsharp.ast.Node;
import io.chsharp.ast.NodeCompilationUnit;
import io.chsharp.ast.NodeDecimal;
import io.chsharp.ast.NodeExpressionBinaryOp;
import io.chsharp.ast.NodeExpressionDecimal;
import io.chsharp.ast.NodeIdentifier;
import io.chsharp.ast.NodeReferenceIdentifier;
import io.chsharp.ast.NodeStmt;
import io.chsharp.ast.NodeStmtAssignment;

public class VisitorSemanticAnalyzer extends Visitor<ChType> {
	
	public Scope<ChType> scope = new Scope<>(null);
	
	public HashMap<Node, ChType> nodeTypes = new HashMap<Node, ChType>();
	
	public VisitorSemanticAnalyzer() {
		scope.set("Gouda", new ChMetaType(ChType.DECIMAL));
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
		
		// If there's a user-specified type, then it's a definition, an not assignment.
		if (stmtassign.type != null) {
			scope.set(stmtassign.left.toString(), right);
			
			ChType userType = stmtassign.type.accept(this);
			
			if(userType instanceof ChMetaType) {
				userType = ((ChMetaType) userType).of;
			}
			
			if (userType != right) {
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
		
		nodeTypes.put(stmtassign, right);
		return right;
	}
	
	public ChType visit(NodeExpressionBinaryOp binop) {
		return ChType.DECIMAL;
	}
	
	public ChType visit(NodeExpressionDecimal exprdecimal) {
		ChType ret = visit(exprdecimal.decimal);
		nodeTypes.putIfAbsent(exprdecimal, ret);
		return ret;
	}
	
	public ChType visit(NodeDecimal decimal) {
		nodeTypes.putIfAbsent(decimal, ChType.DECIMAL);
		return ChType.DECIMAL;
	}
	
	@Override
	public ChType visit(NodeIdentifier ident) {
		ChType ret = scope.get(ident.text);
		nodeTypes.putIfAbsent(ident, ret);
		return ret;
	}
	
	public ChType visit(NodeReferenceIdentifier identref) {
		ChType ret = visit(identref.ident);
		nodeTypes.putIfAbsent(identref, ret);
		return ret;
	}
	
}
