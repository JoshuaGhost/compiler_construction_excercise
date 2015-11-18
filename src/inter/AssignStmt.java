package inter;

import treewalker.TreeWalker;

/*
 * Die Unterklasse AssignStmt von Stmt definiert beschreibt Wertzuweisungen, 
 * die als Anweisungen auftreten. Wichtigster Punkt ist dabei, dass ein ";"
 * nach der externen Darstellung der Wertzuweisung ausgegeben wird. 

 */

public class AssignStmt extends Stmt {
	Assignment assign;

	public Assignment getAssign() {
		return assign;
	}

	public AssignStmt(Assignment x) {
		assign = x;
	}

	public <ReturnType, ArgumentType> ReturnType walk(TreeWalker<ReturnType, ArgumentType> walker, ArgumentType arg) {
		return walker.walkAssignStmtNode(this, arg);
	}

}
