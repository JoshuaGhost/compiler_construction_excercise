package main;

import java.io.*;
import lexer.*;
import parser.*;
import treewalker.StringTreeWalker;
//import treewalker.CountNodesWalker;
//import treewalker.LinTreeWalker;
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
	 * 
	 * Parallel dazu wird ein Syntaxbaum mit Wurzel root erzeugt.
	 * 
	 * Der Treewalker stw versucht, die Eingabe zu rekonstruieren.
	 * 	 * 
	 */

	public static void main(String[] args) throws IOException {
		Lexer lex = new Lexer();
		Parser parse = new Parser(lex);
		Program root = parse.program();
		System.out.println("\nParsing erfolgreich beendet\n");
		
		System.out.println("nun die Rekonstruktion\n");	// siehe Aufgabe 1 Blatt 5
		StringTreeWalker stw = new StringTreeWalker();
		System.out.print(stw.walk(root, ""));
		
//		System.out.println("\nnun der Syntaxbaum\n");	// siehe Aufgabe 3 Blatt 5 
//		LinTreeWalker ltw = new LinTreeWalker();
//		ltw.walk(root, "");
//		
//		CountNodesWalker cnw = new CountNodesWalker();	// siehe Aufgabe 2 Blatt 5
//		System.out.println ("\nmit Knotenzahl: " + cnw.walk(root,  null));

	}

}
