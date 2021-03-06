package main;

import java.io.*;
import lexer.*;
import parser.*;
import inter.*;

public class Main {

	/*
	 * Ein Parser für die Beispielsprache aus dem Buch von Aho, Lem, Sethi und
	 * Ullmann
	 * 
	 * Die main-Methode erzeugt zunächst eine Instanz eines lexikalen Scanners,
	 * der dann zum Erzeugen einer Instanz eines Parsers benutzt wird. Dieser
	 * Parser liest die Eingabe von der Console und wirft im Fall eines
	 * Syntaxfehlers eine IOException. 
	 * Parallel dazu wird ein Syntaxbaum mit Wurzel root erzeugt.
	 */

	public static void main(String[] args) throws IOException {
		Lexer lex = new Lexer();
		Parser parse = new Parser(lex);
		Program root = parse.program();
		System.out.println("\nProgramm erfolgreich beendet\n");

	}

}
