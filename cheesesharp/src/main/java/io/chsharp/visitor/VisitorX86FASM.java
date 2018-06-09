package io.chsharp.visitor;

import java.util.HashMap;

import io.chsharp.Scope;
import io.chsharp.ast.NodeCompilationUnit;
import io.chsharp.ast.NodeExpressionBinaryOp;
import io.chsharp.ast.NodeExpressionDecimal;
import io.chsharp.ast.NodeReferenceIdentifier;
import io.chsharp.ast.NodeStmt;
import io.chsharp.ast.NodeStmtAssignment;
import io.chsharp.utils.FASM;

public class VisitorX86FASM extends Visitor<Void> {
	
	private final VisitorSemanticAnalyzer typeChecker;
	
	public final FASM text = new FASM();
	public final FASM data = new FASM();
	
	public Scope<Integer> scope = new Scope<>(null);
	public int stackDepth = 0;
	
	public HashMap<Double, String> doubleLiterals = new HashMap<>();
	
	public VisitorX86FASM(VisitorSemanticAnalyzer typeChecker) {
		this.typeChecker = typeChecker;
	}
	
	@Override
	public Void visit(NodeCompilationUnit cunit) {
		for (NodeStmt stmt : cunit.statements) {
			stmt.accept(this);
		}
		return null;
	}
	
	@Override
	public Void visit(NodeStmtAssignment stmtassign) {
		String ident = ((NodeReferenceIdentifier) stmtassign.left).ident.text;
		
		// Is definition?
		if (stmtassign.type != null) {
			text.i("sub", "esp", 16);
			scope.set(ident, stackDepth);
		}

		stmtassign.right.accept(this);
		
		int addr = stackDepth - scope.get(ident);
		
		// Is definition?
		if (stmtassign.type != null) {
			stackDepth += 16;
		}
		
		text.i("fstp", "tword [esp + " + addr + "]");

		return null;
	}
	
	public Void visit(NodeExpressionBinaryOp binop) {
		binop.left.accept(this);
		binop.right.accept(this);
		text.i("faddp", "st1", "st0");
		
		return null;
	}
	
	public Void visit(NodeExpressionDecimal decimalExpr) {
		double val = decimalExpr.decimal.value;
		
		if (!doubleLiterals.containsKey(val)) {
			String label = "dec" + Integer.toString(doubleLiterals.size(), 36);
			
			doubleLiterals.put(val, label);
			data.val(label, "dt", val);
		}
		
		text.i("fld", "tword [" + doubleLiterals.get(val) + "]");
		
		return null;
	}
	
	public Void visit(NodeReferenceIdentifier refident) {
		int stackDepth = this.stackDepth - scope.get(refident.ident.text);
		text.i("fld", "tword [esp + " + stackDepth + "]");
		return null;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("format ELF executable 3\n");
		
		builder.append("\nsegment readable executable\n\n");
		builder.append(text);
		
		builder.append("\nsegment readable writable\n\n");
		builder.append(data);
		
		return builder.toString();
	}
	
}
