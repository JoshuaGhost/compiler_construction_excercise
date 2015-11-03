package inter;

/*
 * Seq ist eine Unterklasse von Stmt, die eine Folge von Anweisungen
 * beschreibt. In der Instanzenvariable stmt1 wird die erste, 
 * in stmt2 die restlichen Anweisungen abgelegt.
 */

public class Seq extends Stmt {
	Stmt stmt1;
	Stmt stmt2;

	public Seq(Stmt s1, Stmt s2) {
		stmt1 = s1;
		stmt2 = s2;
	}

}
