package inter;

/*
 * AssignElem ist eine Unterklasse von Assignment und beschreibt 
 * Wertzuweisungen mit einem Array-Zugriff auf der linken Seite. 
 * In den Instanzenvariablen array und index wird die linke Seite, 
 * in expr die rechte Seite der Wertzuweisung abgelegt
 */

public class AssignElem extends Assignment {
	Access acc;
	Expr expr;

	public AssignElem(Access x, Expr y) {
		acc = x;
		expr = y;
	}
	
	@Override
	public String toString() {
		return acc.toString()+"="+expr.toString();
	}
	
	public String toString(int k) {
		return super.toString(k) + "\n" +
			   acc.toString(k+1) + "\n" +
			   expr.toString(k+1);
	}

}
