package parser;

import java.io.*;
import lexer.*;
import inter.*;

/*
 * Diese Klasse implementiert einen rekursive descent Parser für die 
 * Beispielsprache. Die Instanzenvariable lex verweist auf einen lexikalen
 * Scanner für diese Sprache. look enthält das lookahead-Token 
 * Beim Parsen wird ein Syntax-Baum erzeugt, die dazu notwendigen 
 * Klassen befinden sich im Paket inter.
 */

public class Parser {
	private Lexer lex; 	// Lexical Analyser für diesen Parser
	private Token look; // lookahead Token

	private int k = -1;
	
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

	private void ps(int k, String name) {
		for (int i = 0; i < k; i++) {
			System.out.print("-");
		}
		System.out.println(name);
	}
	
	public Program program() throws IOException { 			// program -> block
		k += 1;
		ps(k, SynNode.PROGRAM);
		Block b = block();
		k -= 1;
		return new Program(b);
	}

	Block block() throws IOException { 					// block -> { decls stmts }
		match('{');
		decls();
		k+=1;
		Stmt s = stmts();
		k-=1;
		match('}');
		return new Block(s);
	}

	void decls() throws IOException {
		while (look.tag == Tag.BASIC) { 				// decls -> type id
			type();
			do {
				match(Tag.ID);
				if (look.tag != ',')	// kein weiterer ID zu dieser Typ-Deklaration
					break;
				move();					// Komma überlesen
			} while (true);
			match(';');
		}
	}

	void type() throws IOException {					// type -> basic dims
		// expect look.tag == Tag.Basic
		match(Tag.BASIC);
		if (look.tag != '[')
			return; // Type -> basic					// dims -> epsilon
		else
			dims(); // parse array type
	}

	void dims() throws IOException {					// dims -> [num] dims
		match('[');
		match(Tag.NUM);
		match(']');
		if (look.tag == '[')
			dims();
		return;
	}

	Stmt stmts() throws IOException { 
		if (look.tag == '}') {							// stmts -> epsilon
			k+=1;
			ps(k, SynNode.EMPTY);
			k-=1;
			return EmptyStmt.Null;
		}
		else {// stmts -> stmt stmts
			ps(k, SynNode.STMT);
			//k += 1;
			Stmt st = stmt();
			//k-=1;
			Seq seq = new Seq(st, stmts());
			return seq;
		}
	}

	Stmt stmt() throws IOException {
		Expr x;
		Stmt s1, s2;
		Assignment a1, a2;
		
		k+=1;
		switch (look.tag) {
		case ';':								// stmt -> ;
			move();
			ps(k, SynNode.EMPTY);
			k-=1;
			return EmptyStmt.Null;
			
		case Tag.IF:		
			ps(k, SynNode.IF);
			match(Tag.IF);
			match('(');
			x = bool();
			match(')');
			k += 1;
			ps(k, SynNode.STMT);
			s1 = stmt();
			k-=1;
			if (look.tag != Tag.ELSE) {
				k-=1;
				return new If(x,s1);							// stmt -> if (bool) stmt
			}
			match(Tag.ELSE);					// stmt -> if (bool) stmt else stmt
			ps(k, SynNode.ELSE);
			k+=1;
			ps(k, SynNode.STMT);
			s2 = stmt();
			k-=1;
			k-=1;
			return new Else(x, s1, s2);
			
		case Tag.WHILE:							// stmt -> while (bool) stmt
			ps(k, SynNode.WHILE);
			While whileNode = new While();
			match(Tag.WHILE);
			match('(');
			x = bool();
			match(')');
			k+=1;
			ps(k, SynNode.STMT);
			s1 = stmt();
			k = k-1;
			whileNode.init(x, s1);
			k-=1;
			return whileNode;
			
		case Tag.DO:							// stmt -> do stmt while (bool)
			ps(k, SynNode.DO);
			Do doNode = new Do();
			match(Tag.DO);
			k+=1;
			ps(k, SynNode.STMT);
			s1 = stmt();
			k-=1;
			match(Tag.WHILE);
			match('(');
			x = bool();
			match(')');
			match(';');
			doNode.init(s1,  x);
			k-=1;
			return doNode;
			
		case Tag.FOR: 							// stmt -> for (assign; bool; assign) stmt
			ps(k, SynNode.FOR);
			For forNode = new For();
			match(Tag.FOR);
			match('(');
			k+=1;
			ps(k, SynNode.ASSIGNMENT);
			a1 = assign();	 // erste Komponente ist ein assign
			k-=1;
			match(';');
			k+=1;
			//ps(k, SynNode.EXPR);
			x = bool();
			k-=1;
			match(';');
			k+=1;
			ps(k, SynNode.ASSIGNMENT);
			a2 = assign();	// dritte Komponente ist ein assign
			k-=1;
			match(')'); 
			k+=1;
			ps(k, SynNode.STMT);
			s1 = stmt();
			k-=1;
			forNode.init(a1, x, a2, s1);
			k-=1;
			return forNode;
			
		case Tag.BREAK:							// stmt -> break ;
			ps(k, SynNode.BREAK);
			match(Tag.BREAK);
			match(';');
			k-=1;
			return new Break();
			
		case '{':								// stmt -> block
			ps(k, SynNode.BLOCK);
			Block blk = block();
			k-=1;
			return blk;
			
		default:								// stmt -> assign ;
			ps(k, SynNode.ASSIGNMENT);
			a1 = assign();
			match(';');
			k-=1;
			return new AssignStmt(a1);
		}
	}

	Assignment assign() throws IOException {					
		Assignment ass;
		Token t = look;
		match(Tag.ID);
		Id id = new Id((Word) t);
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
			e = new Constant(look);
			move();
			return e;
		case Tag.REAL:									// factor -> real
			e = new Constant(look);
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
			Id id = new Id((Word) look);
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
