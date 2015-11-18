package inter;

import lexer.*;
import treewalker.TreeWalker;

/*
 * Rel ist eine Unterklasse von Logical und beschreibt 
 * relationale Ausdrücke
 */

public class Rel extends Logical {

	public Rel(Token tok, Expr x1, Expr x2) {
		super(tok, x1, x2);
	}

	public <ReturnType, ArgumentType> ReturnType walk(TreeWalker<ReturnType, ArgumentType> walker, ArgumentType arg) {
		return walker.walkRelNode(this, arg);
	}

}
