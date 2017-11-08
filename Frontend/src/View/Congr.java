package View;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by IL984626 on 20/06/2017.
 */
public class Congr {
    public View myView;
    public Stage propStage;

    @FXML
    public Button Exit;
    public Button Cont;


    public Congr(View view){
        myView=view;
        FXMLLoader fxmlLoader = new FXMLLoader();
        try {
           // Parent root = fxmlLoader.load(getClass().getResource("Properties.fxml").openStream());

            Parent root = fxmlLoader.load(getClass().getResource("View/Congr.fxml").openStream());
            propStage = new Stage();
            propStage.setTitle("Congratulations");
            propStage.setScene(new Scene(root,300,300));
            propStage.showAndWait();
            propStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void exit(){
        myView.exitProgram();

    }
}
