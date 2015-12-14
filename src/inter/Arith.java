package inter;

import lexer.*;
import symbols.Type;
import treewalker.TreeWalker;

/*
 * Arith ist eine Unterklasse von Op und beschreibt arithmetische Ausdrücke. 
 * In den Instanzenvariablen x1 und x2 werden die beiden Operanden 
 * abgelegt
 */

public class Arith extends Op {
	Expr expr1, expr2;

	public Expr getExpr1() {
		return expr1;
	}
	public Expr getExpr2() {
		return expr2;
	}
	public void setExpr1(Expr expr1) {
		this.expr1 = expr1;
	}
	public void setExpr2(Expr expr2) {
		this.expr2 = expr2;
	}
	
	public Arith(Token tok, Expr x1, Expr x2) {
		this(tok, x1, x2, null);
	}
	
	public Arith(Token tok, Expr x1, Expr x2, Type p) {
		super(tok);
		expr1 = x1;
		expr2 = x2;
		type = p;	
	}
	
	public <ReturnType, ArgumentType> ReturnType walk(TreeWalker<ReturnType, ArgumentType> walker, ArgumentType arg) {
		return walker.walkArithNode(this, arg);
	}

}
