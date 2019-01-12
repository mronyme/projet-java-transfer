import core.CoreGame;
import views.IHM;

public class Main {

	public static void main(String[] args) {

		CoreGame game = new CoreGame();
        IHM window2 = new IHM(game);
        game.setIHM(window2);
		// Syntaxe : nom_classe nom_objet = new nom_constructeur(paramètres) ;
	}

}
