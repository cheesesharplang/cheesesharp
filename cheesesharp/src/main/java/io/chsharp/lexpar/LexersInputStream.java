package io.chsharp.lexpar;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

public class LexersInputStream extends BufferedInputStream {
	
	private int curLine = 0, curChar = -1;
	
	public LexersInputStream(InputStream in) {
		super(in);
	}
	
	public int getCurLine() {
		return this.curLine;
	}
	
	public int getCurChar() {
		return this.curChar;
	}
	
	@Override
	public synchronized int read() {
		try {
			int ret = super.read();
			
			if(ret == '\n') {
				curLine++;
				curChar = -1;
			}
			curChar++;
			
			return ret;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public int peek(int depth) {
		try {
			mark(depth);
			for (int i = 0; i < depth - 1; i++)
				read();
			int ret = read();
			reset();
			return ret;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
}
