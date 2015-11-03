package inter;

/*
 * While ist eine Unterklasse von Stmt. In der Instanzenvariable expr
 * wird der Ausdruck, in stmt die Anweisung abgelegt.
 */

public class While extends Stmt {
	Expr expr;
	Stmt stmt;

	public While() {
		expr = null;
		stmt = null;
	}

	public void init(Expr x, Stmt s) {
		expr = x;
		stmt = s;
	}

}
