package inter;

import treewalker.TreeWalker;
/*
 * Break ist eine Unterklasse von Stmt ohne Instanzenvariablen
 */

public class Break extends Stmt {

	public Break() {
	}

	public <ReturnType, ArgumentType> ReturnType walk(TreeWalker<ReturnType, ArgumentType> walker, ArgumentType arg) {
		return walker.walkBreakNode(this, arg);
	}

}
