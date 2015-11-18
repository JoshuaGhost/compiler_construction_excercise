package inter;

import treewalker.TreeWalker;

/*
 * Block ist eine Unterklasse von Stmt. In der Instanzenvariable stmts
 * werden die Anweisungen des Blocks abgelegt.
 */

public class Block extends Stmt {
	Stmt stmts;

	public Stmt getStmts() {
		return stmts;
	}

	public Block(Stmt s) {
		stmts = s;
	}

	public <ReturnType, ArgumentType> ReturnType walk(TreeWalker<ReturnType, ArgumentType> walker, ArgumentType arg) {
		return walker.walkBlockNode(this, arg);
	}

}
