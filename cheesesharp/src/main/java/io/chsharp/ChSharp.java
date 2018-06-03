package io.chsharp;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

import io.chsharp.ast.NodeCompilationUnit;
import io.chsharp.lexpar.Lexer;
import io.chsharp.lexpar.Parser;
import io.chsharp.visitor.VisitorSemanticAnalyzer;

public class ChSharp {
	
	public static void main(String[] args) {
		String testInput = "Gouda j is 9\nj is 3";
		Lexer l = new Lexer(new ByteArrayInputStream(testInput.getBytes(StandardCharsets.UTF_8)));
		Parser p = new Parser(l);
		
		NodeCompilationUnit cunit = p.matchCUnit();
		cunit.accept(new VisitorSemanticAnalyzer());
	}
	
}
