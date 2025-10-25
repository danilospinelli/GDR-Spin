package start;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

// PER AGGIUNGERE UN NUOVO PERSONAGGIO:
// 1. Creare la sua classe che estende Personaggio nel datamodel
// 2. Aggiungere il nome della sua classe a ListaClassi
// 3. Aggiungi la classe a PersonaggioFactory
// 4. Aggiungi la classe a PersonaggioServiceFactory
// 5. Creare la sua implementazione in service.impl con la logica delle sue mosse e le sue animazioni
// 6. Eventuale logica per Umano e Virtuale
// 7. Aggiungerlo nella vista SceltaPg
public class Runner extends Application {
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/viste/title_screen.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("GDR Spin");
        primaryStage.show();
	}
	
	public static void main (String[] args) {
		launch(args);
	}

}
