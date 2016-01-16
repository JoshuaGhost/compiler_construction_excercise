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

public class CodeGenWalker extends TreeWalker<Singleton, LabelPair>{

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
	 * Ausgabe der Sprungbefehle bei einem Vergleich:
	 * test ist die zu prüfende Bedingung, 
	 * args ist das Paar (trueLabel, falseLabel) der Sprungziele. 
	 */
	void emitJumps(String test, LabelPair args) {

			emit("if " + test + " goto L" + args.trueLabel());
			emit("goto L" + args.falseLabel());
	}
	
	/*
	 * Wenn bei einer Wertzuweisung der Ausdruck vom Typ Boolean ist,
	 * muss er speziell verarbeitet werden, da in den 3-Adress-Befehlen
	 * keine booleschen Operationen erlaubt sind.
	 */
	
	Singleton processBooleanExpr(Expr node) {
		
		int t = newLabel();
		int f = newLabel();
		int next = newLabel();
		Temp tmp = new Temp(Type.Bool);
       
        /*
         *  Hier fehlt etwas
         */
		
		return tmp;		
	}


//  ---------------------- Hier jetzt die Methoden des Treewalkers ------------
//   Jede Methode für einen Ausdruck bekommt das Paar (trueLabel, falseLabel) als Argument
//   und liefert ein Singleton als Wert des Attributs place zurück.
	
	public Singleton walkAccessNode(Access node, LabelPair arg) {
		Singleton e = walk (node.getIndex(), arg);
		Temp t = new Temp(node.getType());
		emit(t.toString() + " = " + node.getArray().toString() + "[" + e.toString() + "]");
		if (node.getType() == Type.Bool)
			emitJumps(t.toString(), arg);
		return t;
	}
	
	
	public Singleton walkArithNode(Arith node, LabelPair arg) {	
		Temp t = new Temp(node.getType());
       
        /*
         *  Hier fehlt etwas
         */
		
		return t;
	}
	
	public Singleton walkUnaryNode(Unary node, LabelPair arg) {
		Temp t = new Temp(node.getType());
       
        /*
         *  Hier fehlt etwas
         */
		
		return t;
	}
	
	/*
	 * Boolesche Ausdrücke werden mit der short-cut Methode 
	 * ausgewertet. Die in arg übergebenen Label werden
	 * der Booleschen Funktion entsprechend weitergereicht.
	 */
	
	public Singleton walkAndNode(And node, LabelPair arg) {
       
        /*
         *  Hier fehlt etwas
         */
		
		return null;
	}
	
	public Singleton walkOrNode(Or node, LabelPair arg) {
       
        /*
         *  Hier fehlt etwas
         */
         		
		return null;
	}
	
	public Singleton walkRelNode(Rel node, LabelPair arg) {

       
        /*
         *  Hier fehlt etwas
         */
		
		emitJumps(test, arg);
		return null;
	}
	
	public Singleton walkNotNode(Not node, LabelPair arg) {
       
        /*
         *  Hier fehlt etwas
         */
		
		return null;
	}
	
	public Singleton walkAssignStmtNode(AssignStmt node, LabelPair arg) {
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
	
	public Singleton walkAssignIdNode(AssignId node, LabelPair arg) {
		Singleton eCode;
		if (node.getExpr().getType() == Type.Bool)	
			eCode = processBooleanExpr(node.getExpr());	
		else {
			eCode = walk(node.getExpr(), arg);
		}
       
        /*
         *  Hier fehlt etwas
         */
		
		return null;
	}
	

	public Singleton walkAssignElemNode(AssignElem node, LabelPair arg) {
		Singleton e1;
		if (node.getExpr().getType() == Type.Bool) {
			e1 = processBooleanExpr(node.getExpr());
		}
		else {
			e1 = walk(node.getExpr(), arg);
		}
       
        /*
         *  Hier fehlt etwas
         */
		
		return null;
	}
	
	/*
	 * Steueranweisungen werden wie im Skript beschrieben übersetzt.
	 */
	
	public Singleton walkWhileNode(While node, LabelPair arg) {
       
        /*
         *  Hier fehlt etwas
         */
		
		return null;
		
	}
	
	public Singleton walkDoNode(Do node, LabelPair arg) {
       
        /*
         *  Hier fehlt etwas
         */
		
		return null;		
	}
	
	public Singleton walkForNode(For node, LabelPair arg) {
       
        /*
         *  Hier fehlt etwas
         */
		
		return null;
	}

	public Singleton walkIfNode(If node, LabelPair arg) {
       
        /*
         *  Hier fehlt etwas
         */
		
		return null;
	}
	
	public Singleton walkElseNode(Else node, LabelPair arg) {
       
        /*
         *  Hier fehlt etwas
         */
		
		return null;
	}
	
	public Singleton walkBreakNode(Break node, LabelPair arg) {
		emit("goto L" + node.getStmt().getNext());
		return null;
	}

	public Singleton walkProgramNode(Program node, LabelPair arg) {
		int end = newLabel();
		
		walk(node.getBlock(), new LabelPair(end, 0));
		emitLabel(end);
		return null;
	}

	public Singleton walkBlockNode (Block node, LabelPair arg) {
		walk(node.getStmts(), arg);
		return null;
	}
	
	
	public Singleton walkSeqNode(Seq node, LabelPair arg) {
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
	
	
	public Singleton walkEmptyStmtNode(EmptyStmt node, LabelPair arg) {
		return null;
	}
	
	public Singleton walkIdNode(Id node, LabelPair arg) {
		if (node.getType() == Type.Bool)
			emitJumps(node.getOp().toString(), arg);
		return node;
	}
	

	public Singleton walkConstantNode(Constant node, LabelPair arg) {
		return node;
	}

	public Singleton walkTempNode(Temp node, LabelPair arg) {
		return node;
	}
	


}
