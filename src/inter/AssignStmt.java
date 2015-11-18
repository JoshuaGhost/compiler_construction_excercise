package inter;

/*
 * Die Unterklasse AssignStmt von Stmt definiert beschreibt Wertzuweisungen, 
 * die als Anweisungen auftreten. 
 */

public class AssignStmt extends Stmt {
	Assignment assign;

	public AssignStmt(Assignment x) {
		assign = x;
	}
	
}
