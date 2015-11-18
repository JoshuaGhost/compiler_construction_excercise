package inter;


/*
 * Die Unterklasse Assignment von Node definiert Wertzuweisungen
 * mit einem Identifier oder einem Feldzugriff auf der linken Seite.
 * Die Definition wurd von Stmt getrennt, da in einigen Kontexten
 * nur eine Wertzuweisung ohne ein abschließendes ";" stehen
 * muss.
 */

public abstract class Assignment extends Node {
}
