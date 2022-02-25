package ivanov.leonid.botdbdatareader;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.io.IOException;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 500);

        //ChoiceBox choiceBox = scene;
        stage.setTitle("BotDatareader:1.0");
        stage.setScene(scene);
        stage.show();
        initapp();
    }

    public static void main(String[] args) {
        launch();
    }

    public void initapp(){
        //welcomeText.setText("WORKED!!!");
        //dbselector.setItems(FXCollections.observableArrayList("localhost:3306", "127.0.0.1:3306", "127.0.0.1:50770"));
        /*dbselector.getItems().add("localhost:3306");
        dbselector.getItems().add("127.0.0.1:3306");
        dbselector.getItems().add("127.0.0.1:50770");
    */
    }

}