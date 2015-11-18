package inter;

/*
 * Else ist eine Unterklasse von Stmt. In der Instanzenvariable expr
 * wird der Ausdruck, in stmt1 und stmt2 die beiden Anweisungen abgelegt.
 */

public class Else extends Stmt {
	Expr expr;
	Stmt stmt1, stmt2;

	public Else(Expr x, Stmt s1, Stmt s2) {
		expr = x;
		stmt1 = s1;
		stmt2 = s2;
	}

}
