package io.chsharp.lexpar;

public class LexerException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public final int line, ch;
	
	public LexerException(int line, int ch, String msg) {
		super(msg);
		this.line = line;
		this.ch = ch;
	}
	
}
