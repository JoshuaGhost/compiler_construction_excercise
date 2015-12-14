package inter;

import treewalker.TreeWalker;
/*
 * Do ist eine Unterklasse von Stmt. In der Instanzenvariable expr
 * wird der Ausdruck, in stmt die Anweisung abgelegt.
 */

public class Do extends Stmt {
	Expr expr;
	Stmt stmt;

	public Expr getExpr() {
		return expr;
	}

	public void setExpr(Expr expr) {
		this.expr = expr;
	}

	public Stmt getStmt() {
		return stmt;
	}

	public Do() {
		expr = null;
		stmt = null;
	}

	public void init(Stmt s, Expr x) {
		expr = x;
		stmt = s;
	}

	public <ReturnType, ArgumentType> ReturnType walk(TreeWalker<ReturnType, ArgumentType> walker, ArgumentType arg) {
		return walker.walkDoNode(this, arg);
	}

}
