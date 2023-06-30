package generator;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * start it with VM options
 * --module-path "libs\javafx-sdk-11.0.2\lib" --add-modules javafx.controls
 */
public class FXFontChooser extends Application {

    @Override
    public void start(Stage primaryStage) {
        Pane pane = new Pane();
        Scene scene = new Scene(pane, 1000, 500);
        primaryStage.setScene(scene);
        primaryStage.show();

        VBox vbox = new VBox();
        javafx.scene.text.Font.getFamilies().forEach(font -> {
            Label label = new Label("Progress towards next rank [" + font + "]");
            label.setFont(new Font(font, 30));
            vbox.getChildren().add(label);
        });

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(vbox);
        pane.getChildren().add(scrollPane);
        scrollPane.setPrefSize(1000, 500);
    }
}
