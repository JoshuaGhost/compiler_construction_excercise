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

	@Override 
	public String toString() {
		return ("if ("+expr.toString()+") "+
					stmt1.toString()+
				"else "+
					stmt2.toString());
	}
	
	public String toString(int k) {
		return super.toString(k).replaceAll("Else", "IfElse") + "\n" +
			   expr.toString(k+1) + "\n" +
			   stmt1.toString(k+1)+ "\n" +
			   stmt2.toString(k+1);
	}
}
