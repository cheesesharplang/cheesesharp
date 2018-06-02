package io.chsharp.lexpar;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.function.Predicate;

public final class Lexer {
	
	private LexersInputStream in;
	
	public Lexer(InputStream is) {
		in = new LexersInputStream(is);
	}
	
	public Token nextToken() {
		int c = in.peek(1);
		
		Token ret = null;
		
		if (c == -1) {
			ret = new Token(Token.Type.EOF, "EOF");
		} else if (c == '"') {
			in.read(); // consume the quote.
			ret = new Token(Token.Type.STRING, match(Predicates.not('"')));
			in.read(); // again.
		} else if (Predicates.digit(c)) {
			String intPart = match(Predicates.and(Predicates::digit, Predicates.not('.')));
			in.read(); // consume the dot.
			String fracPart = match(Predicates::digit);
			
			ret = new Token(Token.Type.DECIMAL, intPart + "." + fracPart);
		} else if (Predicates.whitespace(c)) {
			match(Predicates::whitespace);
			ret = nextToken();
		} else if (Predicates.identifierStart(c)) {
			String ident = match(Predicates::identifier);
			
			switch (ident) {
				case "cheese":
					ret = new Token(Token.Type.CHEESE, "cheese");
					break;
				case "was":
					ret = new Token(Token.Type.WAS, "was");
					break;
				case "make":
					ret = new Token(Token.Type.MAKE, "make");
					break;
				case "operation":
					ret = new Token(Token.Type.OPERATION, "operation");
					break;
				case "end":
					ret = new Token(Token.Type.END, "end");
					break;
				default:
					ret = new Token(Token.Type.IDENTIFIER, ident);
					break;
			}
		}
		
		if (ret != null) {
			return ret;
		} else {
			StringBuilder msg = new StringBuilder();
			msg.append("Unexpected character '");
			msg.appendCodePoint(c);
			msg.append("'.");
			throw new LexerException(in.getCurLine(), in.getCurChar(), msg.toString());
		}
	}
	
	private String match(Predicate<Integer> pred) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		while (pred.test(in.peek(1))) {
			baos.write(in.read());
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
