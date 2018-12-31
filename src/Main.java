import core.CoreGame;
import views.Window2;
import views.crashtest;

public class Main {

	public static void main(String[] args) {
		CoreGame game = new CoreGame();
		//Window window = new Window(game);
		Window2 window = new Window2(game);
		crashtest window2 = new crashtest();
		// Syntaxe : nom_classe nom_objet = new nom_constructeur(paramètres) ;
	}

}
