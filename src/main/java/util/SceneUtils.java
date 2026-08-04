package util;

import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;

public final class SceneUtils {

    private SceneUtils() {
    }

    public static Scene createScaledScene(Parent root, Stage stage) {
        // Usiamo StackPane al posto di Group: centra automaticamente il contenuto
        StackPane wrapper = new StackPane(root);
        
        Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
        Scene scene = new Scene(wrapper, bounds.getWidth(), bounds.getHeight(), Color.BLACK);
        stage.setScene(scene);

        Runnable fit = () -> {
            double sceneWidth = scene.getWidth();
            double sceneHeight = scene.getHeight();
            if (sceneWidth <= 0 || sceneHeight <= 0) {
                return;
            }

            // Otteniamo le dimensioni ideali del nodo root
            double prefWidth = root.prefWidth(-1);
            double prefHeight = root.prefHeight(-1);

            if (prefWidth <= 0 || prefHeight <= 0) {
                prefWidth = root.getLayoutBounds().getWidth();
                prefHeight = root.getLayoutBounds().getHeight();
            }
            if (prefWidth <= 0 || prefHeight <= 0) {
                prefWidth = 600;
                prefHeight = 400;
            }

            // Usa Math.min per far entrare TUTTO il contenuto nello schermo
            double scale = Math.min(sceneWidth / prefWidth, sceneHeight / prefHeight);
            
            // Scaliamo il nodo (StackPane penserà a mantenerlo al centro)
            root.setScaleX(scale);
            root.setScaleY(scale);
        };

        // Listener per aggiornare lo scaling ad ogni cambio di dimensione
        scene.widthProperty().addListener((obs, oldValue, newValue) -> fit.run());
        scene.heightProperty().addListener((obs, oldValue, newValue) -> fit.run());
        stage.fullScreenProperty().addListener((obs, oldValue, newValue) -> fit.run());

        root.applyCss();
        root.layout();
        fit.run();

        return scene;
    }
}