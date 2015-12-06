package inter;

import treewalker.TreeWalker;
/*
 * AssignId ist eine Unterklasse von Assignment und beschreibt 
 * Wertzuweisungen mit einer Variablen auf der linken Seite. 
 * In den Instanzenvariablen id wird die linke Seite, in expr die 
 * rechte Seite der Wertzuweisung abgelegt
 */

public class AssignId extends Assignment {
	Id ident;
	Expr expr;

	public Id getIdent() {
		return ident;
	}

	public Expr getExpr() {
		return expr;
	}

	public void setExpr(Expr expr) {
		this.expr = expr;
	}

	public AssignId(Id i, Expr x) {
		ident = i;
		expr = x;
	}
	
	public <ReturnType, ArgumentType> ReturnType walk(TreeWalker<ReturnType, ArgumentType> walker, ArgumentType arg) {
		return walker.walkAssignIdNode(this, arg);
	}

	
}
