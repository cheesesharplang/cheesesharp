package io.chsharp;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

import io.chsharp.ast.NodeCompilationUnit;
import io.chsharp.lexpar.Lexer;
import io.chsharp.lexpar.Parser;
import io.chsharp.visitor.VisitorSemanticAnalyzer;
import io.chsharp.visitor.VisitorX86FASM;

public class ChSharp {
	
	public static void main(String[] args) {
		String testInput = "Gouda a is 9\nGouda b is 1\nGouda c is a + b";
		Lexer l = new Lexer(new ByteArrayInputStream(testInput.getBytes(StandardCharsets.UTF_8)));
		Parser p = new Parser(l);

		NodeCompilationUnit cunit = p.matchCUnit();
		
		VisitorSemanticAnalyzer typechecker = new VisitorSemanticAnalyzer();
		cunit.accept(typechecker);
		
		VisitorX86FASM x86fasm = new VisitorX86FASM(typechecker);
		cunit.accept(x86fasm);
		System.out.println(x86fasm.toString());
	}
	
}
