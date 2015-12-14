package inter;

import lexer.*;
import treewalker.TreeWalker;

/*
 * Unary ist eine Unterklasse von Op und beschreibt unäre arithmetische Ausdrücke. 
 * In der Instanzenvariablen expr ist der Operand abgelegt
 */

public class Unary extends Op {
	Expr expr;

	public Expr getExpr() {
		return expr;
	}

	public void setExpr(Expr expr) {
		this.expr = expr;
	}

	public Unary(Token tok, Expr x) { // behandelt unäres minus, für ! siehe Not
		super(tok);
		expr = x;
	}

	public <ReturnType, ArgumentType> ReturnType walk(TreeWalker<ReturnType, ArgumentType> walker, ArgumentType arg) {
		return walker.walkUnaryNode(this, arg);
	}

}
