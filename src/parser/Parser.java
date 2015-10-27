package parser;

import java.io.*;
import lexer.*;

/*
 * Diese Klasse implementiert einen recursive descent Parser für die 
 * Beispielsprache. Die Instanzenvariable lex verweist auf einen lexikalen
 * Scanner für diese Sprache. look enthält das lookahead-Token 
 */

public class Parser {
	private Lexer lex; 	// Lexical Analyser für diesen Parser
	private Token look; // lookahead Token

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

	public void program() throws IOException { 			// program -> block
		block();
	}

	void block() throws IOException { 					// block -> { decls stmts }
		match('{');
		decls();
		stmts();
		match('}');
	}

	void decls() throws IOException {
		while (look.tag == Tag.BASIC) { 				// decls -> type id
			type();
			while (true) {
				match(Tag.ID);
				if (look.tag == ',')
					match(',');
				else
					break;
			}
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

	void stmts() throws IOException { 
		if (look.tag == '}')							// stmts -> epsilon
			return;
		else {											// stmts -> stmt stmts
			stmt();
			stmts();
		}
	}

	void stmt() throws IOException {

		switch (look.tag) {
		case ';':										// stmt -> ;
			move();
			return;
		case Tag.IF:									
			match(Tag.IF);
			match('(');
			bool();
			match(')');
			stmt();
			if (look.tag != Tag.ELSE)				
				return;									// stmt -> if (bool) stmt
			match(Tag.ELSE);							// stmt -> if (bool) stmt else stmt
			stmt();
			return;
		case Tag.WHILE:									// stmt -> while (bool) stmt
			match(Tag.WHILE);
			match('(');
			bool();
			match(')');
			stmt();
			return;
		case Tag.FOR:									// stmt -> for (assign bool assign) stmt
			match(Tag.FOR);
			match('(');
			assign();
			match(';');
			bool();
			match(';');
			assign();
			match(')');
			stmt();
			return;
		case Tag.DO:									// stmt -> do stmt while (bool)
			match(Tag.DO);
			stmt();
			match(Tag.WHILE);
			match('(');
			bool();
			match(')');
			match(';');
			return;
		case Tag.BREAK:									// stmt -> break ;
			match(Tag.BREAK);
			match(';');
			return;
		case '{':										// stmt -> block
			block();
			return;
		default:										// stmt -> assign ;
			assign();
			match(';');
			return;
		}
	}

	void assign() throws IOException {					
		match(Tag.ID);
		if (look.tag == '=') { 							// assign -> id = bool
			move();
			bool();
		} else { 										// assign -> id offset = bool
			offset();
			match('=');
			bool();
		}
		return;
	}

	void bool() throws IOException {					// bool -> bool or join | join
		join();
		while (look.tag == Tag.OR) {
			move();
			join();
		}
		return;
	}

	void join() throws IOException {					// join -> join and equality | equality
		equality();
		while (look.tag == Tag.AND) {
			move();
			equality();
		}
		return;
	}

	void equality() throws IOException {				// equality -> equality eq rel |
		rel();											//			   equality ne rel | rel
		while (look.tag == Tag.EQ || look.tag == Tag.NE) {
			move();
			rel();
		}
		return;
	}

	void rel() throws IOException {						// rel -> expr < expr | expr le expr |
		expr();											//        expr ge expr | expr > expr | expr
		switch (look.tag) {
		case '<':
		case Tag.LE:
		case Tag.GE:
		case '>':
			move();
			expr();
			return;
		default:
			return;
		}
	}

	void expr() throws IOException {					// expr -> expr + term | expr - term | term
		term();
		while (look.tag == '+' || look.tag == '-') {
			move();
			term();
		}
		return;
	}

	void term() throws IOException {					// term -> term * unary | term / unary | unary
		unary();
		while (look.tag == '*' || look.tag == '/') {
			move();
			unary();
		}
		return;
	}

	void unary() throws IOException {						
		if (look.tag == '-') {							// unary -> - unary
			move();
			unary();
			return;
		} else if (look.tag == '!') {					// unary -> ! unary
			move();
			unary();
			return;
		} else {										// unary -> factor
			factor();
			return;
		}

	}

	void factor() throws IOException {
		switch (look.tag) {
		case '(':										// factor -> (bool)
			move();
			bool();
			match(')');
			return;
		case Tag.NUM:									// factor -> num
			move();
			return;
		case Tag.REAL:									// factor -> real
			move();
			return;
		case Tag.TRUE:									// factor -> true
			move();
			return;
		case Tag.FALSE:									// factor -> false
			move();
			return;
		case Tag.ID:									// factor -> id offset
			move();
			if (look.tag != '[')
				return;
			else {
				offset();
				return;
			}
		default:
			error("syntax error");
			return;

		}
	}

	void offset() throws IOException { 					// offset -> [bool] offset | epsilon
		match('[');
		bool();
		match(']');
		while (look.tag == '[') { 						// multi-dimensional array
			match('[');
			bool();
			match(']');

		}
		return;
	}

}
