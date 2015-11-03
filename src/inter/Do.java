package inter;

/*
 * Do ist eine Unterklasse von Stmt. In der Instanzenvariable expr
 * wird der Ausdruck, in stmt die Anweisung abgelegt.
 */

public class Do extends Stmt {
	Expr expr;
	Stmt stmt;

	public Do() {
		expr = null;
		stmt = null;
	}

	public void init(Stmt s, Expr x) {
		expr = x;
		stmt = s;
	}

}
