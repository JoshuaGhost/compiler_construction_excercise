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
import inter.Node;
import inter.Not;
import inter.Or;
import inter.Program;
import inter.Rel;
import inter.Seq;
import inter.Unary;
import inter.While;

/*
 * Diese Klasse definiert einen abstrakten TreeWalker, der den Syntaxbaum durchläuft
 * und durch double Dispatch die jeweilig zum Knotentyp passenden Methoden auswählt.
 * In den abgeleiteten Unterklassen müssen für jeden Knotentyp die zugehörigen Methoden
 * implementiert werden. 
 */

public abstract class TreeWalker <ReturnType, ArgumentType>{
	
	public ReturnType walk (Node node, ArgumentType arg) {
		return node.walk(this, arg);
	}

	/*
	 * Hier muss für jeden Knotentyp eine abstrakte Methode eingetragen werden.
	 * In jeder Knoten-Klasse XYZ muss eine Methode walk der Form
	 * 
	 * 	public <ReturnType, ArgumentType> ReturnType 
	 * 			walk(TreeWalker<ReturnType, ArgumentType> walker, ArgumentType arg) {
	 * 					return walker.walkXYZNode(this, arg);
	 * 
	 * definiert werden. Dies gilt natürlich nicht für abstrakte Klassen
	 */
	
	public abstract ReturnType walkAccessNode(Access node, ArgumentType arg);
	public abstract ReturnType walkArithNode(Arith node, ArgumentType arg);
	public abstract ReturnType walkUnaryNode(Unary node, ArgumentType arg);
	public abstract ReturnType walkAndNode(And node, ArgumentType arg);
	public abstract ReturnType walkOrNode(Or node, ArgumentType arg);
	public abstract ReturnType walkRelNode(Rel node, ArgumentType arg);
	public abstract ReturnType walkNotNode(Not node, ArgumentType arg);
	public abstract ReturnType walkIdNode(Id node, ArgumentType arg);
	public abstract ReturnType walkConstantNode(Constant node, ArgumentType arg); 
	public abstract ReturnType walkAssignIdNode(AssignId node, ArgumentType arg);
	public abstract ReturnType walkAssignElemNode(AssignElem node, ArgumentType arg);
	public abstract ReturnType walkEmptyStmtNode(EmptyStmt node, ArgumentType arg); 
	public abstract ReturnType walkWhileNode(While node, ArgumentType arg);
	public abstract ReturnType walkDoNode(Do node, ArgumentType arg);
	public abstract ReturnType walkProgramNode(Program node, ArgumentType arg);
	public abstract ReturnType walkBlockNode(Block node, ArgumentType arg);
	public abstract ReturnType walkIfNode(If node, ArgumentType arg);
	public abstract ReturnType walkAssignStmtNode(AssignStmt node, ArgumentType arg);
	public abstract ReturnType walkForNode(For node, ArgumentType arg);
	public abstract ReturnType walkSeqNode(Seq node, ArgumentType arg);
	public abstract ReturnType walkElseNode(Else node, ArgumentType arg);
	public abstract ReturnType walkBreakNode(Break node, ArgumentType arg);

}
