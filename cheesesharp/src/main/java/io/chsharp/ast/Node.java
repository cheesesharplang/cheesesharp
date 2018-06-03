package io.chsharp.ast;

import io.chsharp.visitor.Visitor;

public abstract class Node {
	
	public abstract <T> T accept(Visitor<T> vis);
	
}
