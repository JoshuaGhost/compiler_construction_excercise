package inter;

import treewalker.TreeWalker;
/*
 * Break ist eine Unterklasse von Stmt. 
 * stmt verweist auf das umfassende Statement für diese
 * Instanz von Break.
 * 
 */

public class Break extends Stmt {
	Stmt stmt;

	
	public Stmt getStmt() {
		return stmt;
	}

	public Break() {
		if (Stmt.getEnclosing() == null) 
			error("unenclosed break");
		stmt = Stmt.getEnclosing();
	}

	public <ReturnType, ArgumentType> ReturnType walk(TreeWalker<ReturnType, ArgumentType> walker, ArgumentType arg) {
		return walker.walkBreakNode(this, arg);
	}

}
