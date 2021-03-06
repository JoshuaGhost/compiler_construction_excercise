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

}
