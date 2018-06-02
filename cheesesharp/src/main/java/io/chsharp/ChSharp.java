package io.chsharp;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

import io.chsharp.lexpar.Lexer;
import io.chsharp.lexpar.Token;

public class ChSharp {
	
	public static void main(String[] args) {
		String testInput = "cheese A was Brie make end 0.005 \"AH\"";
		Lexer l = new Lexer(new ByteArrayInputStream(testInput.getBytes(StandardCharsets.UTF_8)));
		
		Token t;
		while ((t = l.nextToken()).type != Token.Type.EOF) {
			System.out.println(t);
		}
	}
	
}
