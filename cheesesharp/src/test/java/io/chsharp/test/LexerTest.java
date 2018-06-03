package io.chsharp.test;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

import org.junit.Test;

import io.chsharp.lexpar.Lexer;
import io.chsharp.lexpar.Token;
import junit.framework.TestCase;

public class LexerTest extends TestCase {
	
	@Test
	public void testGPTokens() throws Exception {
		String input = "\"AAAA\" .004";
		Lexer lexer = new Lexer(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
		
		assertEquals(lexer.nextToken().type, Token.Type.STRING);
		assertEquals(lexer.nextToken().type, Token.Type.DECIMAL);
	}
	
	@Test
	public void testKeyword() throws Exception {
		String input = "cheese was make operation end end";
		Lexer lexer = new Lexer(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
		
		assertEquals(lexer.nextToken().type, Token.Type.CHEESE);
		assertEquals(lexer.nextToken().type, Token.Type.WAS);
		assertEquals(lexer.nextToken().type, Token.Type.MAKE);
		assertEquals(lexer.nextToken().type, Token.Type.OPERATION);
		assertEquals(lexer.nextToken().type, Token.Type.END);
		assertEquals(lexer.nextToken().type, Token.Type.END);
	}
	
}
