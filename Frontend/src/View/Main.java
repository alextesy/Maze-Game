package View;

import Model.Model;
import ViewModel.ViewModel;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.awt.*;

public class Main extends Application {

    @Override
    public void stop() throws Exception {

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Model model = new Model();
        //model.startServers();
        ViewModel viewModel = new ViewModel(model);
        model.addObserver(viewModel);
        model.start();

        //--------------
        primaryStage.setTitle("Mario Maze");
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent root = fxmlLoader.load(getClass().getResource("View.fxml").openStream());
        Scene scene = new Scene(root, 800, 700);
        scene.getStylesheets().add(getClass().getResource("ViewStyle.css").toExternalForm());
        primaryStage.setScene(scene);


        //--------------
        View view = fxmlLoader.getController();
        view.setResizeEvent(scene);
        view.setViewModel(viewModel);
        viewModel.addObserver(view);
        //--------------
        //SetStageCloseEvent(primaryStage);

        primaryStage.show();
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                model.stop();
            }
        });
    }


    public static void main(String[] args) {
        launch(args);
    }
}
