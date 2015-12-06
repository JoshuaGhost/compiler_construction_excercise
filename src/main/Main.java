package main;

import java.io.*;
import lexer.*;
import parser.*;
import treewalker.LinTreeWalkerType;
import treewalker.SemanticWalker;
import treewalker.CountNodesWalker;
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
	 * Nach dem Parsen wird der erzeugte Syntaxbaum ausgegeben.
	 * 
	 * Danach läuft die semantische Analyse, die ausser der Typprüfung 
	 * auch automatische Typanpassungen durchführt.
	 * 
	 * Zur Kontrolle wird der modifizierte Syntaxbaum noch einmal augegeben.
	 */

	public static void main(String[] args) throws IOException {
		Lexer lex = new Lexer();
		Parser parse = new Parser(lex);
		Program root = parse.program();
		System.out.println("\nParsing erfolgreich beendet\n");
				
		System.out.println("\nnun der Syntaxbaum\n"); 
		LinTreeWalkerType ltwt = new LinTreeWalkerType();
		ltwt.walk(root, "");
		
		CountNodesWalker cnw = new CountNodesWalker();
		System.out.println ("\nmit Knotenzahl: " + cnw.walk(root,  null));
		
		System.out.println("\nnun die Semantische Analyse");
		SemanticWalker semw = new SemanticWalker();
		semw.walk(root, null);
		System.out.println("\nnun der Syntaxbaum noch einmal\n"); 
		ltwt.walk(root, "");
		
		System.out.println ("\nmit Knotenzahl: " + cnw.walk(root,  null));

		System.out.println("\nUebersetzung beendet");

	}

}
