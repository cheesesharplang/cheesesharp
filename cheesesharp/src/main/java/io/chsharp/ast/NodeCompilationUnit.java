package io.chsharp.ast;

import java.util.ArrayList;
import java.util.List;

import io.chsharp.visitor.Visitor;

public class NodeCompilationUnit extends Node {
	
	public List<NodeStmt> statements = new ArrayList<NodeStmt>();
	
	@Override
	public <T> T accept(Visitor<T> vis) {
		return vis.visit(this);
	}
	
}
