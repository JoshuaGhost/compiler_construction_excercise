package parser;

import java.io.*;

import symbols.Array;
import symbols.Env;
import symbols.Type;
import lexer.*;
import inter.*;

/*
 * Diese Klasse implementiert einen rekursive descent Parser für die 
 * Beispielsprache. Die Instanzenvariable lex verweist auf einen lexikalen
 * Scanner für diese Sprache. look enthält das lookahead-Token 
 * Beim Parsen wird ein Syntax-Baum erzeugt, die dazu notwendigen 
 * Klassen befinden sich im Paket inter.
 * Die Klasse ist erweitert worden um Symboltabellen für die semantische
 * Prüfung aufzubauen (siehe Klasse symbols.Env)
 */

public class Parser {
	private Lexer lex; 	// Lexical Analyser für diesen Parser
	private Token look; // lookahead Token
	Env top = null;		// die momentan aktuelle Symboltabelle ist leer
	int used = 0;		// nächste freie Speicheradresse im Datenspeicher

	public Parser(Lexer l) throws IOException {
		lex = l;
		move();
	}

	/* move liest das nächste Token und speichert es in look */
	void move() throws IOException {
		look = lex.scan();
	}

	void error(String s) {
		throw new Error("near line " + Lexer.line + ": " + s);
	}

	void match(int t) throws IOException {
		if (look.tag == t)
			move();
		else
			error("syntax error");
	}

	public Program program() throws IOException { 			// program -> block
		Block b = block();
		return new Program(b);
	}

	Block block() throws IOException { 					// block -> { decls stmts }
		int savedUsed = used;
		match('{');
		Env savedEnv = top;								// momentane Symboltabelle retten
		top = new Env(top);								// neue leere Symboltabelle mit der alten verknüpfen
		decls();
		Stmt s = stmts();
		match('}');
		top = savedEnv;									// Symboltabelle auf den Stand vor dem Block zurücksetzen 
		used = savedUsed;								// Speicher freigeben
		return new Block(s);
	}

	//**********************************************************************************
	// dies ist die alte Version von decls - siehe Aufgabenblatt 6
	void decls() throws IOException {
		while (look.tag == Tag.BASIC) { 				// decls -> type id
			Type t = type();
			do {
				Word var = new Word(look.toString(), Tag.ID);
				Id newId = new Id(var, t, used);
				top.put(look, newId);
				System.out.println("in table: "+
									var.toString()+
								   "("+t.toString()+")  "+
								   "rel.Adr: "+
								   String.valueOf(used));
				used += t.width;
				match(Tag.ID);
				if (look.tag != ',')	// kein weiterer ID zu dieser Typ-Deklaration
					break;
				move();					// Komma überlesen
			} while (true);
			match(';');
		}
	}

	
	// dies ist die alte Version von dims  -  siehe Aufgabenblatt 6
	Array dims(Type p) throws IOException {					// dims -> [num] dims
		match('[');
		int size = Integer.valueOf(look.toString());
		match(Tag.NUM);
		match(']');
		if (look.tag == '[')
			return new Array(size, dims(p));
		return new Array(size, p);
	}
	//**********************************************************************************

	Type type() throws IOException {					// type -> basic dims
		// expect look.tag == Tag.Basic
		Type p = (Type) look;
		match(Tag.BASIC);
		if (look.tag != '[')
			return p; // Type -> basic					// dims -> epsilon
		else
			return dims(p); 							// parse array type
	}
	

	Stmt stmts() throws IOException { 
		if (look.tag == '}')							// stmts -> epsilon
			return EmptyStmt.Null;
		else // stmts -> stmt stmts
			return new Seq(stmt(), stmts());
	}

	Stmt stmt() throws IOException {
		Expr x;
		Stmt s1, s2;
		Assignment a1, a2;

		switch (look.tag) {
		case ';':								// stmt -> ;
			move();
			return EmptyStmt.Null;
			
		case Tag.IF:									
			match(Tag.IF);
			match('(');
			x = bool();
			match(')');
			s1 = stmt();
			if (look.tag != Tag.ELSE)				
				return new If(x,s1);							// stmt -> if (bool) stmt
			match(Tag.ELSE);					// stmt -> if (bool) stmt else stmt
			s2 = stmt();
			return new Else(x, s1, s2);
			
		case Tag.WHILE:							// stmt -> while (bool) stmt
			While whileNode = new While();
			match(Tag.WHILE);
			match('(');
			x = bool();
			match(')');
			s1 = stmt();
			whileNode.init(x, s1);
			return whileNode;
			
		case Tag.DO:							// stmt -> do stmt while (bool)
			Do doNode = new Do();
			match(Tag.DO);
			s1 = stmt();
			match(Tag.WHILE);
			match('(');
			x = bool();
			match(')');
			match(';');
			doNode.init(s1,  x);
			return doNode;
			
		case Tag.FOR: 							// stmt -> for (assign; bool; assign) stmt
			For forNode = new For();
			match(Tag.FOR);
			match('(');
			a1 = assign();	 // erste Komponente ist ein assign
			match(';');
			x = bool();
			match(';');
			a2 = assign();	// dritte Komponente ist ein assign
			match(')'); 
			s1 = stmt();
			forNode.init(a1, x, a2, s1);
			return forNode;
			
		case Tag.BREAK:							// stmt -> break ;
			match(Tag.BREAK);
			match(';');
			return new Break();
			
		case '{':								// stmt -> block
			return block();
			
		default:								// stmt -> assign ;
			a1 = assign();
			match(';');
			return new AssignStmt(a1);
		}
	}

	Assignment assign() throws IOException {					
		Assignment ass;
		Token t = look;
		match(Tag.ID);
		Id id = top.get(t);	
		if (id == null)									// ist der Identifier in der Symboltabelle?
			error(t.toString() + " undeclared"); 		
		if (look.tag == '=') { 							// assign -> id = bool
			move();
			ass = new AssignId(id, bool());
		} else { 										// assign -> id offset = bool
			Access acc = offset(id);
			match('=');
			ass = new AssignElem(acc, bool());
		}
		return ass;
	}

	Expr bool() throws IOException {					// bool -> bool or join | join
		Expr e = join();
		while (look.tag == Tag.OR) {
			Token tok = look;
			move();
			e = new Or(tok, e,join());
		}
		return e;
	}

	Expr join() throws IOException {					// join -> join and equality | equality
		Expr e = equality();
		while (look.tag == Tag.AND) {
			Token tok = look;
			move();
			e = new And(tok, e, equality());
		}
		return e;
	}

	Expr equality() throws IOException {				// equality -> equality eq rel |
		Expr e = rel();											//			   equality ne rel | rel
		while (look.tag == Tag.EQ || look.tag == Tag.NE) {
			Token tok = look;
			move();
			e = new Rel(tok, e, rel());
		}
		return e;
	}

	Expr rel() throws IOException {						// rel -> expr < expr | expr le expr |
		Expr e = expr();											//        expr ge expr | expr > expr | expr
		switch (look.tag) {
		case '<':
		case Tag.LE:
		case Tag.GE:
		case '>':
			Token tok = look;
			move();
			return new Rel(tok, e, expr());
		default:
			return e;
		}
	}

	Expr expr() throws IOException {					// expr -> expr + term | expr - term | term
		Expr e = term();
		while (look.tag == '+' || look.tag == '-') {
			Token tok = look;
			move();
			e = new Arith(tok, e, term());
		}
		return e;
	}

	Expr term() throws IOException {					// term -> term * unary | term / unary | unary
		Expr e = unary();
		while (look.tag == '*' || look.tag == '/') {
			Token tok = look;
			move();
			e = new Arith(tok, e, unary());
		}
		return e;
	}

	Expr unary() throws IOException {	
		if (look.tag == '-') {							// unary -> - unary
			move();
			return new Unary(Word.minus, unary());
		} else if (look.tag == '!') {					// unary -> ! unary
			Token tok = look;
			move();
			return new Not(tok, unary());
		} else {										// unary -> factor
			return factor();
		}
	}

	Expr factor() throws IOException {
		Expr e = null;
		switch (look.tag) {
		case '(':										// factor -> (bool)
			move();
			e = bool();
			match(')');
			return e;
		case Tag.NUM:									// factor -> num
			e = new Constant(look, Type.Int);
			move();
			return e;
		case Tag.REAL:									// factor -> real
			e = new Constant(look, Type.Float);
			move();
			return e;
		case Tag.TRUE:									// factor -> true
			e = Constant.True;
			move();
			return e;
		case Tag.FALSE:									// factor -> false
			e = Constant.False;
			move();
			return e;
		case Tag.ID:									// factor -> id offset
			Id id = top.get(look);
			if (id == null)
				error(look.toString() + " undeclared");
			move();
			if (look.tag != '[')
				return id;
			else {
				return offset(id);
			}
		default:
			error("syntax error");
			return e;

		}
	}

	Access offset(Id a) throws IOException { 			// offset -> [bool] offset | epsilon
		Expr e;
		match('[');
		e = bool();
		match(']');
		Access acc = new Access(a, e);
		while (look.tag == '[') { 						// multi-dimensional array
			match('[');
			e = bool();
			match(']');
			acc = new Access((Expr) acc, e);
		}
		return acc;
	}

}
