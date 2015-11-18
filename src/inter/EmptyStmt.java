package inter;

import treewalker.TreeWalker;

/*
 * Die Unterklasse EmptyStmt von Stmt definiert eine Klassenvariable
 * Null, die das leere Statement repräsentiert
 */

public final class EmptyStmt extends Stmt {

	public EmptyStmt() {
	}

	public final static Stmt Null = new EmptyStmt();

	public <ReturnType, ArgumentType> ReturnType walk(TreeWalker<ReturnType, ArgumentType> walker, ArgumentType arg) {
		return walker.walkEmptyStmtNode(this, arg);
	}

}
