package io.chsharp.lexpar;

import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;

public class LexersInputStream extends PushbackInputStream {
	
	private int curLine = 0, curChar = -1;
	
	public LexersInputStream(InputStream in) {
		super(in, 2048);
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
			
			if (ret == '\n') {
				curLine++;
				curChar = -1;
			}
			curChar++;
			
			return ret;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public int read(byte[] b, int off, int len) {
		try {
			return super.read(b, off, len);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public void unread(byte[] b, int off, int len) {
		try {
			super.unread(b, off, len);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public synchronized void reset() {
		try {
			super.reset();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public int peek(int depth) {
		byte[] b = new byte[depth];
		try {
			read(b, 0, b.length);
			unread(b);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (b[depth - 1] == 0)
			return -1;
		else
			return b[depth - 1];
	}
	
}
