package inter;

import lexer.*;
import treewalker.TreeWalker;

/*
 * And ist eine Unterklasse von Logical und beschreibt 
 * die logische und-Operation
 */

public class And extends Logical {

	public And(Token tok, Expr x1, Expr x2) {
		super(tok, x1, x2);
	}

	public <ReturnType, ArgumentType> ReturnType walk(TreeWalker<ReturnType, ArgumentType> walker, ArgumentType arg) {
		return walker.walkAndNode(this, arg);
	}

}
