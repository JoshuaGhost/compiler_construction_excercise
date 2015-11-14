package inter;

/*
 * Die Unterklasse AssignStmt von Stmt definiert beschreibt Wertzuweisungen, 
 * die als Anweisungen auftreten. Wichtigster Punkt ist dabei, dass ein ";"
 * nach der externen Darstellung der Wertzuweisung ausgegeben wird. 

 */

public class AssignStmt extends Stmt {
	Assignment assign;

	public AssignStmt(Assignment x) {
		assign = x;
	}
	
	@Override
	public String toString() {
		return assign.toString()+";";
	}
	
	public String toString(int k) {
		return super.toString(k) + "\n" +
			   assign.toString(k+1);
	}
}
