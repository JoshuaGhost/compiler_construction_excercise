package inter;

/*
 * AssignId ist eine Unterklasse von Assignment und beschreibt 
 * Wertzuweisungen mit einer Variablen auf der linken Seite. 
 * In den Instanzenvariablen id wird die linke Seite, in expr die 
 * rechte Seite der Wertzuweisung abgelegt
 */

public class AssignId extends Assignment {
	Id ident;
	Expr expr;

	public AssignId(Id i, Expr x) {
		ident = i;
		expr = x;
	}
	
	@Override
	public String toString() {
		return (ident.toString()+"="+expr.toString());
	}
	
	public String toString(int k) {
		return super.toString(k) + "\n" +
			   ident.toString(k+1) + "\n" +
			   expr.toString(k+1);
	}
}
