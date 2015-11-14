package inter;

/*
 * If ist eine Unterklasse von Stmt. In der Instanzenvariable expr
 * wird der Ausdruck, in stmt die Anweisung abgelegt.
 */

public class If extends Stmt {
	Expr expr;
	Stmt stmt;

	public If(Expr x, Stmt s) {
		expr = x;
		stmt = s;
	}
	
	@Override
	public String toString() {
		return ("if ("+expr.toString()+")\n"+stmt.toString());	
	}

}
