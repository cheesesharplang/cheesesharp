package io.chsharp.lexpar;

import io.chsharp.ast.NodeCompilationUnit;
import io.chsharp.ast.NodeDecimal;
import io.chsharp.ast.NodeExpression;
import io.chsharp.ast.NodeExpressionDecimal;
import io.chsharp.ast.NodeIdentifier;
import io.chsharp.ast.NodeReference;
import io.chsharp.ast.NodeReferenceIdentifier;
import io.chsharp.ast.NodeStmt;
import io.chsharp.ast.NodeStmtAssignment;
import io.chsharp.lexpar.Token.Type;

public final class Parser {
	
	private Lexer lexer;
	
	public Parser(Lexer lexer) {
		this.lexer = lexer;
	}
	
	public Token expect(Token.Type type) {
		Token token = lexer.nextToken();
		if (token.type != type) {
			throw new ParserException(token.line, token.ch, "Expected " + type + ", got " + token.type + " instead.");
		}
		return token;
	}
	
	public NodeCompilationUnit matchCUnit() {
		NodeCompilationUnit ret = new NodeCompilationUnit();
		while (lexer.peek(1).type != Token.Type.EOF) {
			ret.statements.add(matchStmt());
		}
		return ret;
	}
	
	public NodeStmt matchStmt() {
		NodeStmtAssignment ret = new NodeStmtAssignment();
		
		if(lexer.peek(3).type == Type.IS) {
			// This is a definition, not an assignment.
			ret.type = matchTypename();
			ret.left = matchReference();
			expect(Token.Type.IS);
			ret.right = matchExpression();
		} else {
			// This is an assignment, not a definition.
			ret.left = matchReference();
			expect(Token.Type.IS);
			ret.right = matchExpression();
		}

		return ret;
	}
	
	public NodeReference matchReference() {
		return new NodeReferenceIdentifier(matchIdentifier());
	}
	
	public NodeExpression matchExpression() {
		Token tok = lexer.nextToken();
		switch (tok.type) {
			case DECIMAL:
				return new NodeExpressionDecimal(new NodeDecimal(Double.parseDouble(tok.text)));
			default:
				throw new RuntimeException("Failed to match tok token type when matching an expression.");
		}
	}
	
	public NodeIdentifier matchTypename() {
		return matchIdentifier();
	}
	
	public NodeIdentifier matchIdentifier() {
		return new NodeIdentifier(expect(Token.Type.IDENTIFIER).text);
	}
	
}
