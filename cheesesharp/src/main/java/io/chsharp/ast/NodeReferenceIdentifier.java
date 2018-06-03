package io.chsharp.ast;

import io.chsharp.visitor.Visitor;

public class NodeReferenceIdentifier extends NodeReference {
	
	public NodeIdentifier ident;
	
	public NodeReferenceIdentifier(NodeIdentifier ident) {
		this.ident = ident;
	}
	
	@Override
	public <T> T accept(Visitor<T> vis) {
		return vis.visit(this);
	}
	
	@Override
	public String toString() {
		return ident.toString();
	}
	
}
