package io.chsharp.lexpar;

public class ParserException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private final int line, ch;
	
	public ParserException(int line, int ch, String s) {
		super(s);
		this.line = line;
		this.ch = ch;
	}
	
}
