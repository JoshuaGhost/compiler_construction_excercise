package inter;


/*
 * Die Unterklasse EmptyStmt von Stmt definiert eine Klassenvariable
 * Null, die das leere Statement repräsentiert
 */

public final class EmptyStmt extends Stmt {

	public EmptyStmt() {
	}

	public final static Stmt Null = new EmptyStmt();

}
