package io.chsharp.lexpar;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public final class Lexer {
	
	LexersInputStream istream;
	
	List<Token> peekStack = new ArrayList<Token>();
	
	public Lexer(InputStream is) {
		istream = new LexersInputStream(is);
	}
	
	public Token nextToken() {
		return nextToken(false);
	}
	
	private Token nextToken(boolean ignorePeekStack) {
		if (!ignorePeekStack && peekStack.size() > 0) {
			return peekStack.remove(0);
		}
		
		int c = istream.peek(1);
		
		Token ret = null;
		
		if (c == -1) {
			ret = new Token(Token.Type.EOF, "EOF", istream.getCurLine(), istream.getCurChar());
		} else if (c == '+') {
			istream.read();
			ret = new Token(Token.Type.PLUS, "+", istream.getCurLine(), istream.getCurChar());
		} else if (c == '"') {
			istream.read(); // consume the quote.
			ret = new Token(Token.Type.STRING, match(Predicates.not('"')), istream.getCurLine(), istream.getCurChar());
			istream.read(); // again.
		} else if (Predicates.digit(c) || (c == '.' && Predicates.digit(istream.peek(2)))) {
			String intPart = match(Predicates.and(Predicates::digit, Predicates.not('.')));
			istream.read(); // consume the dot.
			String fracPart = match(Predicates::digit);
			
			ret = new Token(Token.Type.DECIMAL, intPart + "." + fracPart, istream.getCurLine(), istream.getCurChar());
		} else if (Predicates.whitespace(c)) {
			match(Predicates::whitespace);
			ret = nextToken(ignorePeekStack);
		} else if (Predicates.identifierStart(c)) {
			String ident = match(Predicates::identifier);
			
			switch (ident) {
				case "cheese":
					ret = new Token(Token.Type.CHEESE, "cheese", istream.getCurLine(), istream.getCurChar());
					break;
				case "was":
					ret = new Token(Token.Type.WAS, "was", istream.getCurLine(), istream.getCurChar());
					break;
				case "make":
					ret = new Token(Token.Type.MAKE, "make", istream.getCurLine(), istream.getCurChar());
					break;
				case "operation":
					ret = new Token(Token.Type.OPERATION, "operation", istream.getCurLine(), istream.getCurChar());
					break;
				case "end":
					ret = new Token(Token.Type.END, "end", istream.getCurLine(), istream.getCurChar());
					break;
				case "is":
					ret = new Token(Token.Type.IS, "is", istream.getCurLine(), istream.getCurChar());
					break;
				default:
					ret = new Token(Token.Type.IDENTIFIER, ident, istream.getCurLine(), istream.getCurChar());
					break;
			}
		}
		
		if (ret != null) {
			return ret;
		} else {
			StringBuilder msg = new StringBuilder();
			msg.append("Unexpected character '");
			if (Character.isWhitespace(c)) {
				msg.append("\\x");
				msg.append(Integer.toHexString(c));
			} else {
				msg.appendCodePoint(c);
			}
			msg.append("'.");
			throw new LexerException(istream.getCurLine(), istream.getCurChar(), msg.toString());
		}
	}
	
	public Token peek(int depth) {
		while (peekStack.size() < depth) {
			peekStack.add(nextToken(true));
		}
		return peekStack.get(depth - 1);
	}
	
	private String match(Predicate<Integer> pred) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		while (pred.test(istream.peek(1))) {
			baos.write(istream.read());
		}
		
		return baos.toString();
	}
	
	public static final class Predicates {
		private Predicates() {
		}
		
		public static Predicate<Integer> and(Predicate<Integer> a, Predicate<Integer> b) {
			return (i) -> a.test(i) && b.test(i);
		}
		
		public static final boolean digit(int c) {
			return c >= '0' && c <= '9';
		}
		
		public static Predicate<Integer> not(int c) {
			return (i) -> i != c;
		}
		
		public static final boolean whitespace(int c) {
			return c == ' ' || c == '\t' || c == '\r' || c == '\n';
		}
		
		public static final boolean identifierStart(int c) {
			return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
		}
		
		public static final boolean identifier(int c) {
			return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || (c >= '0' && c <= '9');
		}
	}
	
}
