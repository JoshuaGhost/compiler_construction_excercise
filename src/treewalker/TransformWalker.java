package treewalker;
/*
 * Dies ist eine Unterklasse der Klasse TreeWalker, die den Syntaxbaum 
 * durchläuft und Array-Zugriffe transformiert.
 * Es werden mehrdimensionale Arrays zu eindimensionalen umgeformt und
 * es wird die Größe der Arrayelemente berücksichtigt.
 * Ein Access-Knoten verweist danach auf den Namen des Arrays und auf einen 
 * arithmetischen Ausdruck, der das Offset enthält.
 *
 */

import lexer.Token;
import symbols.Array;
import symbols.Type;
import inter.Access;
import inter.And;
import inter.Arith;
import inter.AssignElem;
import inter.AssignStmt;
import inter.AssignId;
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
import inter.Temp;
import inter.Unary;
import inter.Expr;
import inter.While;

public class TransformWalker extends TreeWalker<Expr, Void> {
	
	/*
	 * Die rekursive Methode transfomAccess verarbeitet einen Syntaxbaum,
	 * der als Wurzel einen Knoten der Klasse Access hat. Dabei werden die
	 * Zugriffe über einen Index (oder mehreren Indizes bei mehrdimensionalen
	 * Feldern) in einen Zugriff über Feldbeginn und Offset transformiert,
	 * wobei die Größe der Feldelemente noch nicht berücksichtigt wird.
	 */
	
	Expr transformAccess (Access node) {
		Expr tNode;
		Type type;
		Expr w, t1;

		type = node.getArray().getType();
		if (node.getArray().getClass() == Access.class) {
			
			// mehrdimensionales Feld, rekursiv umwandeln
			tNode = transformAccess((Access)node.getArray());
			
			w = new Constant(((Array)type). getSize(), Type.Int);
			t1 = new Arith(new Token('*'), tNode, w, Type.Int);
			t1 = new Arith(new Token('+'), t1, node.getIndex(), Type.Int);
			return  t1;
		}
		else {
		}	return node.getIndex();		

	}
	
	/*
	 * arrayName extrahiert den Namen des Arrays. 
	 */

	Id arrayName(Access a) {
		if (a.getArray().getClass() == Access.class) {
			return arrayName((Access)a.getArray());
		}
		else return (Id)a.getArray();
	}

	/*
	 * Hier wird jetzt der durch transformAccess umgewandelte Index
	 * noch mit der Länge eines Feldelements multipliziert
	 */
	
	Access transformArray (Access node) {
		Expr rNode;
		Expr w, t;
		Id  aName = arrayName(node);
		Type type = node.getArray().getType();		
		
		rNode = transformAccess(node);
		w = new Constant(((Array)type).getOf().getWidth(), Type.Int);
		t = new Arith(new Token('*'), rNode, w, Type.Int);
		return new Access(aName, t, node.getType());
	}
	

	@Override
	public Expr walkAccessNode(Access node, Void arg) {
		Access tNode = transformArray(node);
		tNode.setIndex(walk (tNode.getIndex(), null));
		return tNode;
		}

	@Override
	public Expr walkArithNode(Arith node, Void arg) {
		node.setExpr1 (walk(node.getExpr1(), null));
		node.setExpr2 (walk(node.getExpr2(), null));
		return node;
	}

	@Override
	public Expr walkUnaryNode(Unary node, Void arg) {
		node.setExpr (walk(node.getExpr(), null));
		return node;
	}

	@Override
	public Expr walkAndNode(And node, Void arg) {
		node.setExpr1 (walk(node.getExpr1(), null));
		node.setExpr2 (walk(node.getExpr2(), null));
		return node;
	}

	@Override
	public Expr walkOrNode(Or node, Void arg) {
		node.setExpr1 (walk(node.getExpr1(), null));
		node.setExpr2 (walk(node.getExpr2(), null));
		return node;
	}

	@Override
	public Expr walkRelNode(Rel node, Void arg) {
		node.setExpr1(walk(node.getExpr1(), null));
		node.setExpr2(walk(node.getExpr2(), null));
		return node;
	}

	@Override
	public Expr walkNotNode(Not node, Void arg) {
		return node;
	}

	@Override
	public Expr walkIdNode(Id node, Void arg) {
		return node;
	}

	@Override
	public Expr walkConstantNode(Constant node, Void arg) {
		return node;
	}

	@Override
	public Expr walkAssignIdNode(AssignId node, Void arg) {
		node.setExpr(walk(node.getExpr(), null));
		return null;
	}

	@Override
	public Expr walkAssignElemNode(AssignElem node, Void arg) {
		node.setAcc((Access)walk(node.getAcc(), null));
		node.setExpr(walk(node.getExpr(), null));
		return null;
	}

	@Override
	public Expr walkAssignStmtNode(AssignStmt node, Void arg) {
		walk(node.getAssign(), null);
		return null;
	}
	@Override
	public Expr walkEmptyStmtNode(EmptyStmt node, Void arg) {
		return null;
	}

	@Override
	public Expr walkWhileNode(While node, Void arg) {
		node.setExpr(walk(node.getExpr(), null));
		walk(node.getStmt(), null);
		return null;
	}

	@Override
	public Expr walkDoNode(Do node, Void arg) {
		node.setExpr(walk(node.getExpr(), null));
		walk(node.getStmt(), null);
		return null;
	}

	@Override
	public Expr walkProgramNode(Program node, Void arg) {
		walk(node.getBlock(), null);
		return null;
	}

	@Override
	public Expr walkBlockNode(Block node, Void arg) {
		walk(node.getStmts(), null);
		return null;
	}

	@Override
	public Expr walkIfNode(If node, Void arg) {
		node.setExpr(walk(node.getExpr(), null));
		walk(node.getStmt(), null);
		return null;
	}

	@Override
	public Expr walkForNode(For node, Void arg) {
		node.setExpr(walk(node.getExpr(), null));
		walk(node.getInit_ass(), null);
		walk(node.getIter_ass(), null);
		walk(node.getStmt(), null);
		return null;
	}

	@Override
	public Expr walkSeqNode(Seq node, Void arg) {
		walk(node.getStmt1(), null);
		walk(node.getStmt2(), null);
		return null;
	}

	@Override
	public Expr walkElseNode(Else node, Void arg) {
		node.setExpr(walk(node.getExpr(), null));
		walk(node.getStmt1(), null);
		walk(node.getStmt2(), null);
		return null;
	}

	@Override
	public Expr walkBreakNode(Break node, Void arg) {
		return null;
	}

	@Override
	public Expr walkTempNode(Temp node, Void arg) {
		return node;
	}

}
