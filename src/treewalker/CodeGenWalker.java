package treewalker;

/*
 * Dies ist eine Unterklasse der Klasse TreeWalker, die den Syntaxbaum 
 * durchläuft und Drei-Adress Code erzeugt. 
 * Die benutzte Attributierung findet sich im Skript, Kapitel 5.
 */


import code.LabelPair;
import symbols.Type;
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
import inter.Expr;
import inter.For;
import inter.Id;
import inter.If;
import inter.Not;
import inter.Or;
import inter.Program;
import inter.Rel;
import inter.Seq;
import inter.Singleton;
import inter.Temp;
import inter.Unary;
import inter.While;

public class CodeGenWalker extends TreeWalker<Expr, LabelPair>{

	static private int labelNumber = 0; // zur fortlaufenden Nummerierung der Labels
	      
	int newLabel() {
		return ++labelNumber;
	}
	
	// Ausgabe eines Labels
	void emitLabel(int i) {
		System.out.print("L" + i + ":");	
	}
	
	// zur Ausgabe der Drei-Adress-Befehle
	void emit (String s) {
		System.out.println("\t" + s);
		
	}

	/*
	 * test ist die zu prüfende Bedingung, 
	 * args ist das Paar (trueLabel, falseLabel) der Sprungziele. 
	 * Sonderfälle treten auf, wenn eines der Label 0 ist,
	 */
	void emitJumps(String test, LabelPair args) {
		int t = args.trueLabel();
		int f = args.falseLabel();
		if (t != 0 && f != 0) {
			emit("if " + test + " goto L" + t);
			emit("goto L" + f);
		}
		else if (t != 0) {
			emit("if " + test + " goto L" + t);
		}
		else if (f != 0) {
			emit("iffalse " + test + " goto L" + f);
		}
		else ;
	}
	
	/*
	 * Wenn der Ausdruck vom Typ Boolean ist, muss er speziell
	 * verarbeitet werden, da in unserem 3-Adress-Befehlen
	 * keine Booleschen Operationen erlaubt sind.
	 */
	
	Singleton processBooleanExpr(Expr node) {
		
		int t = newLabel();
		int f = newLabel();
		Temp tmp = new Temp(Type.Bool);
		walk(node, new LabelPair(0,f));
		emit (tmp.toString() + " = true");
		emit ("goto L" + t);
		emitLabel (f);
		emit (tmp.toString() + " = false");
		emitLabel(t);
		return tmp;		
	}

	
	/*
	 * reduce gibt den Namen einer Variablen zurück, die nach Auswertung
	 * den Wert von exp enthält. Ist exp keine Konstante oder Variable, dann 
	 * verweist exp auf einen singulären Syntaxknoten, der mittels toString 
	 * die rechte Seite eines 3-Adress-Befehls mit einer neuen temporären
	 * Variablen auf der linken Seite bildet, 
	 */
	Expr reduce (Expr exp) {
		if (exp.getClass()  == Constant.class || exp.getClass() == Id.class)
			return exp;
		Temp t = new Temp(exp.getType());
		emit (t.toString() + " = " + exp.toString());
		return t;
	}
	


//  ---------------------- Hier jetzt die Methoden des Treewalkers ------------
//   Jede Methode für einen Ausdruck bekommt das Paar (trueLabel, falseLabel) als Argument
//   und liefert ein Singleton als Wert des Attributs place zurück.
//   In Hinblick auf Aufgabe 2 des 10. Aufgabenblatts liefern die walk-Methoden
//   jetzt ein Objekt der Klasse Expr zurück.
	
	
	public Expr walkAccessNode(Access node, LabelPair arg) {
		Expr e = walk (node.getIndex(), arg);
		Temp t = new Temp(node.getType());
		emit(t.toString() + " = " + node.getArray().toString() + "[" + e.toString() + "]");
		if (node.getType() == Type.Bool)
			emitJumps(t.toString(), arg);
		return t;
	}
	
	
	public Expr walkArithNode(Arith node, LabelPair arg) {
		Expr e1 = walk(node.getExpr1(), arg);
		Expr e2 = walk(node.getExpr2(), arg);	
		Temp t = new Temp(node.getType());
		emit(t.toString() + " = " + e1.toString() + " " + node.getOp().toString() +" " + e2.toString());
		return t;
	}
	
	public Expr walkUnaryNode(Unary node, LabelPair arg) {
		Expr e = walk(node.getExpr(), arg);
		Temp t = new Temp(node.getType());
		emit(t.toString() + " = " + node.getOp().toString() + " " + e.toString());
		return t;
	}
	
	/*
	 * Boolesche Ausdrücke werden mit der short-cut Methode 
	 * ausgewertet. Die in arg übergebenen Label werden
	 * der Booleschen Funktion entsprechend weitergereicht.
	 */
	
	public Expr walkAndNode(And node, LabelPair arg) {
		int t = arg.trueLabel();
		int f = arg.falseLabel();		
		int label = newLabel();
		
		walk (node.getExpr1(), new LabelPair(label,f));
		emitLabel(label);
		walk (node.getExpr2(), new LabelPair(t,f));
		return null;
	}
	
	public Expr walkOrNode(Or node, LabelPair arg) {
		int t = arg.trueLabel();
		int f = arg.falseLabel();		
		int label = newLabel();
		
		walk (node.getExpr1(), new LabelPair(t,label));
		emitLabel(label);
		walk (node.getExpr2(), new LabelPair(t,f));
		return null;
	}
	
	public Expr walkRelNode(Rel node, LabelPair arg) {
		Expr e1 = walk (node.getExpr1(), null);
		Expr e2 = walk (node.getExpr2(), null);
		String test = e1.toString() + node.getOp().toString() + e2.toString();
		emitJumps(test, arg);
		return null;
	}
	
	public Expr walkNotNode(Not node, LabelPair arg) {
		walk(node.getExpr1(), new LabelPair(arg.falseLabel(), arg.trueLabel()));
		return null;
	}
	
	public Expr walkAssignStmtNode(AssignStmt node, LabelPair arg) {
		walk(node.getAssign(), arg);
		return null;
	}
	

	/*
	 * bei den folgenden zwei Methoden muss bei einer Wertzuweisung an 
	 * einen booleschen Identifier an ein Element eines booleschen Arrays
	 * eine spezielle Übersetzung mit der Methode processBooleanExpr
	 * durchgeführt werden
	 * 
	 */
	
	public Expr walkAssignIdNode(AssignId node, LabelPair arg) {
		Expr eCode;
		if (node.getExpr().getType() == Type.Bool)	
			eCode = processBooleanExpr(node.getExpr());	
		else {
			eCode = walk(node.getExpr(), arg);
		}
		emit(node.getIdent().toString() + " = " + eCode.toString());
		return null;
	}
	

	public Expr walkAssignElemNode(AssignElem node, LabelPair arg) {
		Expr e1;
		if (node.getExpr().getType() == Type.Bool) {
			e1 = processBooleanExpr(node.getExpr());
		}
		else {
			e1 = walk(node.getExpr(), arg);
		}
		Access a = node.getAcc();
		Expr e2 = walk(a.getIndex(), arg);
		emit(a.getArray().toString()+"["+e2.toString() + "] = " + e1.toString());
		return null;
	}
	
	/*
	 * Steueranweisungen werden wie im Skript beschrieben übersetzt.
	 */
	
	public Expr walkWhileNode(While node, LabelPair arg) {
		int next = arg.nextLabel();
		int label = newLabel();
		int beginLabel = newLabel();
		
		emitLabel(beginLabel);
		node.setNext(next);
		walk(node.getExpr(), new LabelPair(label,next));
		emitLabel(label);
		walk(node.getStmt(), new LabelPair(beginLabel,0));
		emit("goto L" + beginLabel);
		return null;
		
	}
	
	public Expr walkDoNode(Do node, LabelPair arg) {
		int next = arg.nextLabel();
		int begin = newLabel();
		int label = newLabel();		

		node.setNext(next);
		emitLabel(begin);
		walk(node.getStmt(), new LabelPair(label,0));
		emitLabel(label);
		walk(node.getExpr(), new LabelPair(begin,next));
		return null;		
	}
	
	public Expr walkForNode(For node, LabelPair arg) {
		int beginLabel = newLabel();
		int label1 = newLabel();
		int label2 = newLabel();
		int next = arg.nextLabel();
		
		node.setNext(next);
		walk(node.getInit_ass(), new LabelPair(beginLabel, 0));
		emitLabel(beginLabel);
		walk(node.getExpr(), new LabelPair(label2, next));
		emitLabel(label1);
		walk(node.getIter_ass(), new LabelPair(beginLabel, 0));
		emit("goto L" + beginLabel);	
		emitLabel(label2);
		walk(node.getStmt(), new LabelPair(label1, 0));
		emit("goto L" + label1);
		return null;
	}

	public Expr walkIfNode(If node, LabelPair arg) {
		int label = newLabel();
		int next = arg.nextLabel();
		
		walk(node.getExpr(), new LabelPair (label,next));
		emitLabel(label);
		walk (node.getStmt(), new LabelPair(next,0));		
		return null;
	}
	
	public Expr walkElseNode(Else node, LabelPair arg) {
		int label1 = newLabel();
		int label2 = newLabel();
		int next = arg.nextLabel();
		
		walk(node.getExpr(), new LabelPair (label1,label2));
		emitLabel(label1);
		walk (node.getStmt1(), new LabelPair(next,0));
		emit("goto L" + next);
		emitLabel(label2);
		walk(node.getStmt2(), new LabelPair(next, 0));
		return null;
	}
	
	public Expr walkBreakNode(Break node, LabelPair arg) {
		emit("goto L" + node.getStmt().getNext());
		return null;
	}

	public Expr walkProgramNode(Program node, LabelPair arg) {
		int end = newLabel();
		
		walk(node.getBlock(), new LabelPair(end, 0));
		emitLabel(end);
		return null;
	}

	public Expr walkBlockNode (Block node, LabelPair arg) {
		walk(node.getStmts(), arg);
		return null;
	}
	
	
	public Expr walkSeqNode(Seq node, LabelPair arg) {
		if (node.getStmt1() == EmptyStmt.Null) 
			walk(node.getStmt2(), arg);
		else if (node.getStmt2() == EmptyStmt.Null)
			walk(node.getStmt1(), arg);
		else {
			int label = newLabel();
			walk(node.getStmt1(), new LabelPair(label, 0));
			emitLabel(label);
			walk(node.getStmt2(), new LabelPair(arg. nextLabel(), 0));
		}
		return null;
	}
	
	
	public Expr walkEmptyStmtNode(EmptyStmt node, LabelPair arg) {
		return null;
	}
	
	public Expr walkIdNode(Id node, LabelPair arg) {
		if (node.getType() == Type.Bool)
			emitJumps(node.getOp().toString(), arg);
		return node;
	}
	

	public Expr walkConstantNode(Constant node, LabelPair arg) {
		return node;
	}

	public Expr walkTempNode(Temp node, LabelPair arg) {
		return node;
	}
	


}
