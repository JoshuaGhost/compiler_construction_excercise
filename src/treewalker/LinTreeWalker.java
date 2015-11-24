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
 * durchläuft und eine graphische Darstellung des Baums erzeugt.
 * Jeder Knoten des Syntaxbaums wird durch eine Zeile in der Ausgabe 
 * repräsentiert, in der zunächst eine Zeichenkette preStr die Tiefe
 * darstellt und dann eine Bezeichnung des Knotens folgt.
 * 
 * Jede Methode gibt null als Rückgabewert zurück.
 */

public class LinTreeWalker extends TreeWalker<String, String> {
	static String indent = "|  ";
	
	public  String walkAccessNode(Access node, String preStr) {
		System.out.println(preStr + "Access");
		walk(node.getArray(), preStr + indent);
		walk(node.getIndex(), preStr + indent);
		return null;
	}
	
	public  String walkArithNode(Arith node, String preStr) {
		System.out.println(preStr + "Arith (" + node.getOp().toString() + ")");
		walk(node.getExpr1(),preStr + indent);
		walk(node.getExpr2(), preStr + indent);
		return null;
	}
	
	public  String walkUnaryNode(Unary node, String preStr) {
		System.out.println(preStr + "Unary(" + node.getOp().toString()  + ")");
		walk (node.getExpr(), preStr + indent);
		return null;
	}
	
	public  String walkAndNode(And node, String preStr) {
		System.out.println(preStr + "And");
		walk (node.getExpr1(), preStr + indent);
		walk( node.getExpr2(), preStr + indent);
		return null;
	}
	
	public  String walkOrNode(Or node, String preStr) {
		System.out.println(preStr + "Or");
		walk(node.getExpr1(), preStr + indent);
		walk(node.getExpr2(), preStr + indent);
		return null;
	}
	
	public  String walkRelNode(Rel node, String preStr) {
		System.out.println(preStr + "Rel (" + node.getOp().toString() + ")");
		walk(node.getExpr1(), preStr + indent);
		walk(node.getExpr2(), preStr + indent);
		return null;
	}
	
	public  String walkNotNode(Not node, String preStr) {
		System.out.println(preStr + "Not");
		walk(node.getExpr1(), preStr + indent);
		return null;
	}
	
	public  String walkIdNode(Id node, String preStr) {
		System.out.println(preStr + "Id (" + node.getOp().toString() + ")");
		return null;
	}
	
	public  String walkConstantNode(Constant node, String preStr) {
		System.out.println(preStr + "Constant (" + node.getOp().toString() + ")");
		return null;
	}
	
	public  String walkAssignIdNode(AssignId node, String preStr) {
		System.out.println(preStr + "AssignId");
		walk(node.getIdent(), preStr + indent);
		walk(node.getExpr(), preStr + indent);
		return null;
	}
	
	public  String walkAssignElemNode(AssignElem node, String preStr) {
		System.out.println(preStr + "AssignElem");
		walk(node.getAcc(), preStr + indent);
		walk(node.getExpr(), preStr + indent);
		return null;
	}
	
	public String walkAssignStmtNode(AssignStmt node, String preStr) {
		System.out.println(preStr + "AssignStmt");
		walk(node.getAssign(), preStr + indent);
		return null;
	}
	
	public  String walkEmptyStmtNode(EmptyStmt node, String preStr) {
		System.out.println(preStr + "EmptyStmt");
		return null; 
	}
	
	public  String walkWhileNode(While node, String preStr) {
		System.out.println(preStr + "While");
		walk(node.getExpr(), preStr + indent);
		walk(node.getStmt(), preStr + indent);
		return null;
	}
	
	public  String walkDoNode(Do node, String preStr) {
		System.out.println(preStr + "Do");
		walk(node.getStmt(), preStr + indent);
		walk(node.getExpr(), preStr + indent);
		return null;
	}
	
	public  String walkBlockNode(Block node, String preStr) {
		System.out.println(preStr + "Block");
		walk(node.getStmts(), preStr + indent);
		return null;
	}
	
	public  String walkIfNode(If node, String preStr) {
		System.out.println(preStr + "If");
		walk(node.getExpr(), preStr + indent);
		walk(node.getStmt(), preStr + indent);
		return null;
	}
	
	
	public  String walkForNode(For node, String preStr) {
		System.out.println(preStr + "For");
		walk(node.getInit_ass(), preStr + indent);
		walk(node.getExpr(), preStr + indent);
		walk(node.getIter_ass(), preStr + indent);
		walk(node.getStmt(), preStr + indent);
		return null;
	}
	
	public  String walkSeqNode(Seq node, String preStr) {
		System.out.println(preStr + "Seq");
		walk(node.getStmt1(), preStr +indent);
		//System.out.println(preStr +indent);	// alle Statements einer Sequenz erscheinen auf gleicher Stufe
		walk(node.getStmt2(), preStr);
		return null;
	}
	
	public  String walkElseNode(Else node, String preStr) {
		System.out.println(preStr + "IfElse");
		walk(node.getExpr(), preStr + indent);
		walk(node.getStmt1(), preStr + indent);
		walk(node.getStmt2(), preStr + indent);
		return null;
	}
	
	public  String walkBreakNode(Break node, String preStr) {
		System.out.println(preStr + "Break");
		return null;
	}


	public String walkProgramNode(Program node, String preStr) {
		System.out.println(preStr + "Program");
		walk(node.getBlock(), preStr);
		return null;
	}


}
