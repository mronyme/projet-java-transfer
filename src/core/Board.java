package core;

import java.util.Arrays;

import entities.Card;
import entities.Castle;
import entities.Empty;
import entities.Entity;

public class Board {
	Entity[][] coords;
	public Board() {
		// Cr�er un tableau 9*9 remplis d'object "Empty" (case vide)
		this.coords = new Entity[9][9];
		Empty emptyCase = new Empty();
		for (Entity[] row : this.coords)
		    Arrays.fill(row, emptyCase);
	}
	// Retourne l'entit� pr�sente sur ces coordonne�s : Soit une Carte, soit un ch�teaux
	// Pour savoir si on est fa�e un l'un ou l'autre , on compare l'entit�e au �numeration : Exemple -> if(Entity.getType() == EntityEnum.Card)
	// Ne pas oublier d'importer la classe Entity et la classe EntityEnum
	public Entity getEntity(int coordsX,int coordsY)
	{
		return this.coords[coordsX][coordsY];
	}
	// Place la carte sur ces coordonn�es
	public void setCard(int coordsX,int coordsY,Card card)
	{
		this.coords[coordsX][coordsY] = card;
	}
	// Place le ch�teaux sur ces coordonn�es
	public void setCastle(int coordsX,int coordsY,Castle castle)
	{
		this.coords[coordsX][coordsY] = castle;
	}
	// R�cup�re tout le plateaux du joueur
	public Entity[][] getBoard()
	{
		return this.coords;
	}
}
