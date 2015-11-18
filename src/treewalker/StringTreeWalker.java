package treewalker;

import inter.Access;
import inter.And;
import inter.Arith;
import inter.AssignElem;
import inter.AssignId;
import inter.AssignStmt;
import inter.Block;
import inter.Break;
import inter.Constant;
import inter.Do;
import inter.Else;
import inter.EmptyStmt;
import inter.For;
import inter.Id;
import inter.If;
import inter.Not;
import inter.Or;
import inter.Program;
import inter.Rel;
import inter.Seq;
import inter.Unary;
import inter.While;

/*
 * Dies ist eine Unterklasse der Klasse TreeWalker, die den Syntaxbaum 
 * durchläuft und versucht, die Eingabe zu rekonstruieren.
 * Für jeden Knoten des Syntaxbaums wird wird eine externe
 * Darstellung des entsprechenden Teilbaums erzeugt.
 * 
 * Jede Methode hat null als Argument.
 */


public class StringTreeWalker extends TreeWalker<String, String> {


	public String walkAccessNode(Access node, String arg) {
		return walk(node.getArray(), null) + " [ " + walk(node.getIndex(), null) + " ] ";
	}


	public String walkAndNode(And node, String arg) {
		return "(" + walk(node.getExpr1(), null) + " && " + walk(node.getExpr2(), null) + ")";

	}


	public String walkArithNode(Arith node, String arg) {
		return "(" + walk(node.getExpr1(), null) + " " + node.getOp().toString() + " "
				+ walk(node.getExpr2(), null) + ")";
	}


	public String walkAssignElemNode(AssignElem node, String arg) {
		return walk(node.getAcc(), null) + " = "
				+ walk(node.getExpr(), null);
	}


	public String walkAssignIdNode(AssignId node, String arg) {
		return walk(node.getIdent(), null) + " = " + walk(node.getExpr(), null);
	}

	public String walkAssignStmtNode(AssignStmt node, String arg) {
		return walk(node.getAssign(), null) + " ; ";
	}
	
	public String walkBlockNode(Block node, String arg) {
		return "{ " + walk(node.getStmts(), null) + " }";
	}


	public String walkBreakNode(Break node, String arg) {
		return "break;";
	}

	public String walkConstantNode(Constant node, String arg) {
		return node.getOp().toString();
	}

	public String walkDoNode(Do node, String arg) {
		return "do " + walk(node.getStmt(), null) + " while " + walk(node.getExpr(), null) + ";";

	}

	
	public String walkElseNode(Else node, String arg) {
		return "if (" + walk(node.getExpr(), null) + ")\n" + walk(node.getStmt1(), null)
				+ "\nelse " + walk(node.getStmt2(), null);
	}


	public String walkEmptyStmtNode(EmptyStmt node, String arg) {
		return null;
	}


	public String walkForNode(For node, String arg) {
		return "for ( " + walk(node.getInit_ass(), null) + " ; "
				+ walk(node.getExpr(), null) + " ; " + walk(node.getIter_ass(), null)
				+ " )\n" + walk(node.getStmt(), null);
	}


	public String walkIdNode(Id node, String arg) {
		return node.getOp().toString();
	}

	
	public String walkIfNode(If node, String arg) {
		return "if " + walk(node.getExpr(), null) + "\n" + walk(node.getStmt(), null);
	}

	
	public String walkNotNode(Not node, String arg) {
		return "(" + node.getOp().toString() + " " + walk(node.getExpr2(), null) + ")";
	}


	public String walkOrNode(Or node, String arg) {
		return "(" + walk(node.getExpr1(), null) + " || " + walk(node.getExpr2(), null) + ")";
	}


	public String walkRelNode(Rel node, String arg) {
		return "(" + walk(node.getExpr1(), null) + " " + node.getOp().toString() + " "
		+ walk(node.getExpr2(), null) + ")";

	}


	public String walkSeqNode(Seq node, String arg) {
		if (node.getStmt2() == EmptyStmt.Null)
			return walk(node.getStmt1(), null) + "\n";
		else
			return walk(node.getStmt1(), null) + "\n" + walk(node.getStmt2(), null);

	}


	public String walkUnaryNode(Unary node, String arg) {
		return "(" + node.getOp().toString() + " " + walk(node.getExpr(), null) + ")";
	}

	public String walkWhileNode(While node, String arg) {
		return "while (" + walk(node.getExpr(), null) + ")\n" + walk(node.getStmt(), null);

	}

	public String walkProgramNode(Program node, String arg) {
		return walk(node.getBlock(), null);


	}

}
