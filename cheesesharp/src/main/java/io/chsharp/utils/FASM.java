package io.chsharp.utils;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;

/**
 * Expat License, Copyright 2018 lvivtotoro
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 * 
 * @author midnightas
 */
public class FASM extends ByteArrayOutputStream {
	
	public static final String EAX = "eax";
	public static final String EBX = "ebx";
	public static final String ECX = "ecx";
	public static final String EDX = "edx";
	public static final String ESP = "esp";
	public static final String EBP = "ebp";
	
	public static final String BYTE = "db";
	public static final String TWORD = "dt";
	
	public FASM() {
	}
	
	public void i(String i, Object... objs) {
		write(i, "\t", Utils.join(", ", objs), "\n");
	}
	
	public void write(Object... objs) {
		for (Object o : objs) {
			String s = o.toString();
			
			byte[] b = s.getBytes(StandardCharsets.UTF_8);
			write(b, 0, b.length);
		}
	}
	
	public void val(String name, String type, Object... stuffs) {
		write(name, "\t", type, " ", Utils.join(", ", stuffs), "\n");
	}
	
}
