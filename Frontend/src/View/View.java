package View;

import Server.ProjectProperties;
import ViewModel.ViewModel;
import ViewModel.ViewModel;
import algorithms.mazeGenerators.Maze;
import com.sun.org.apache.bcel.internal.classfile.Constant;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.io.*;
import java.util.Observable;
import java.util.Observer;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.Executors;

/**
 * Created by IL984626 on 18/06/2017.
 */
public class View implements Observer{
    public static  ViewModel viewModel;
    public Stage propStage;
    public MediaPlayer mainMediaPlayer;
    public MediaPlayer winMediaPlayer;

    Boolean musicOn;



    @FXML
    public MazeDisplayer mazeDisplayer;
    public Button btn_generate;
    public Button btn_solve;
    public TextField field_Rows;
    public TextField field_Columns;
    public MenuItem exit;
    public MenuItem save;
    public MenuItem load;
    public MenuItem newMaze;
    public MenuItem properties;
    public BorderPane borderPane;
    public ChoiceBox<String> generatingAlgorithm;
    public ChoiceBox<String> solvingAlgorithm;
    public ChoiceBox<String> Controls;
    public Button OK;
    public Button Cont;
    public  MenuItem about;
    public MenuItem help;


    public void setViewModel(ViewModel myViewModel) {

        this.viewModel = myViewModel;
        mazeDisplayer.addEventFilter(MouseEvent.ANY, (e) -> mazeDisplayer.requestFocus());
        btn_solve.setDisable(true);
        musicOn(0);
       // musicThread=new Thread(()->musicOn("C:\\Users\\IBM_ADMIN\\ProjectGUI2\\src\\Resources\\mario.mp3"));
       // musicThread.start();

    }
    public void musicOn(int i){
        if(mainMediaPlayer!=null&&i!=0)
            mainMediaPlayer.setMute(true);
        if(winMediaPlayer!=null&&i==0)
            winMediaPlayer.setMute(true);
        if(i==0){
             Media sound = new Media(new File("./src\\Resources\\mario.mp3").toURI().toString());
             if(mainMediaPlayer!=null){
                 mainMediaPlayer.stop();
             }
            mainMediaPlayer = new MediaPlayer(sound);
            mainMediaPlayer.play();
            //mainMediaPlayer=null;
        }
        else{
            Media sound = new Media(new File("./src\\Resources\\endLevel.mp3").toURI().toString());
            if(winMediaPlayer!=null){
                winMediaPlayer.stop();
            }
            winMediaPlayer = new MediaPlayer(sound);
            winMediaPlayer.play();
            //winMediaPlayer=null;
        }

        //mediaPlayer.play();
    }

    @Override
    public void update(Observable o, Object arg) {
        if(o==viewModel){

            int aSwitch=(Integer) arg;
            switch (aSwitch){
                case 1:
                    mazeDisplayer.setMaze(viewModel.getMaze());
                    btn_generate.setDisable(false);
                    btn_solve.setDisable(false);
                    //mazeDisplayer.addEventFilter(MouseEvent.ANY, (e) -> mazeDisplayer.requestFocus());
                    //mazeDisplayer.requestFocus();
                   // mazeDisplayer.addEventFilter(Event.ANY,(e) -> mazeDisplayer.requestFocus());
                    break;
                case 2:
                    if(mazeDisplayer.setCharecter(viewModel.getPos()[0],viewModel.getPos()[1])){
                        Congr();
                        break;
                        //FUNCTION THAT WILL BLOCK EVERYTHING AND POP UP A WINDOW
                     }
                    break;
                case 3:
                    mazeDisplayer.drawSolution(viewModel.getSol());
                    btn_solve.setDisable(false);
                    btn_generate.setDisable(false);
                    //mazeDisplayer.addEventFilter(MouseEvent.ANY, (e) -> mazeDisplayer.requestFocus());

                    break;


            }

        }
    }

    public void exitProgram(){
        viewModel.exitProgram();

    }
    public void generateMaze(){

        try {
            btn_generate.setDisable (true);
            btn_solve.setDisable(true);
            int row = Integer.valueOf(field_Rows.getText());
            int col = Integer.valueOf(field_Columns.getText());
            if(row<10||col<10||row>300||col>300) {
                inputAlert();
                btn_generate.setDisable(false);
                btn_solve.setDisable(false);
            }
            else {
                this.viewModel.generateMaze(row, col);
                setResizeEvent(btn_generate.getScene());

            }


        }catch (Exception e){
            if(e.getClass()== NumberFormatException.class){
                inputAlert();
                btn_generate.setDisable(false);
                btn_solve.setDisable(false);


            }
            else
                this.viewModel.generateMaze(10  ,  10);




        }
    }
    public void inputAlert(){
        Alert alert2=new Alert(Alert.AlertType.ERROR);
        alert2.setTitle("Wrong Input");
        alert2.setHeaderText("The input you entered is Invalid");
        alert2.setContentText("Please use only numbers in the limit between 10 and 300");
        alert2.showAndWait();
    }
    public void generateSol(){
        btn_generate.setDisable (true);
        btn_solve.setDisable(true);
        viewModel.generateSol();
    }


    public void KeyPressed(KeyEvent keyEvent) {
        FileInputStream input;
        try {
            input = new FileInputStream("config.properties");
            Properties prop = new Properties();
            prop.load(input);
            if(prop.getProperty("Controls").equals("Arrows"))
                viewModel.moveCharacter(keyEvent.getCode(),1);
            else if(prop.getProperty("Controls").equals("AWSD"))
                viewModel.moveCharacter(keyEvent.getCode(),2);
            else
                viewModel.moveCharacter(keyEvent.getCode(),3);

            keyEvent.consume();
        } catch (FileNotFoundException e) {
            viewModel.moveCharacter(keyEvent.getCode(),1);
            keyEvent.consume();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void SaveFile(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Image");
       // System.out.println(pic.getId());
        File file2 = fileChooser.showSaveDialog(null);
        if (file2 != null) {
            try {

                ObjectOutputStream output=new ObjectOutputStream(new FileOutputStream(file2));
                output.writeObject(viewModel.getMaze());

            } catch (IOException ex) {

                 System.out.println(ex.getMessage());
            }
        }

    }
    public void OpenFile() {
        FileChooser fc=new FileChooser();
        fc.setTitle("Open Maze file");
        File file3=fc.showOpenDialog(null);
        if(file3!=null){
            try {
                ObjectInputStream oInput=new ObjectInputStream(new FileInputStream(file3));
                Maze maze=(Maze)oInput.readObject();
                viewModel.setMaze(maze);
            } catch (Exception e) {
                Alert alert=new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Wrong File");
                alert.setHeaderText("You tried to load a wrong file");
                alert.setContentText("Please try again or try to generate a new maze");
                alert.showAndWait();
            }

        }

    }
    @FXML
   public void handleCloseButtonAction(ActionEvent event) {
       Stage stage = (Stage) OK.getScene().getWindow();
       stage.close();
   }

    public void setFileProperties(){
        OutputStream output = null;
        try {
            output = new FileOutputStream("config.properties");
            Properties prop =new Properties();
            prop.setProperty("Generator",generatingAlgorithm.getValue());
            prop.setProperty("Search",solvingAlgorithm.getValue());
            prop.setProperty("Controls", Controls.getValue());
            prop.store(output,null);
            //propStage=new Stage();

        } catch (IOException io) {
            io.printStackTrace();
        } finally {
            if (output != null) {
                try {
                    output.close();
                    handleCloseButtonAction(null);
                   // propStage.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }
    @FXML
    public void handleCongCloseButtonAction() {
        Stage stage = (Stage) Cont.getScene().getWindow();
        stage.close();
        generateMaze();
        musicOn(0);

    }
    public void help(){
        FXMLLoader fxmlLoader = new FXMLLoader();
        Stage Help;
        try {
            Parent root = fxmlLoader.load(getClass().getResource("Help.fxml").openStream());
            Help = new Stage();
            Help.setTitle("Help");
            Scene conScene=new Scene(root,600,700);
            Help.setScene(conScene);
            conScene.getStylesheets().add(getClass().getResource("Help.css").toExternalForm());
            Help.showAndWait();
            Help.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void about(){
        FXMLLoader fxmlLoader = new FXMLLoader();
        Stage about;
        try {
            Parent root = fxmlLoader.load(getClass().getResource("About.fxml").openStream());
            about = new Stage();
            about.setTitle("About");
            Scene conScene=new Scene(root,800,450);
            about.setScene(conScene);
            conScene.getStylesheets().add(getClass().getResource("About.css").toExternalForm());
            about.showAndWait();
            about.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void Congr(){
        FXMLLoader fxmlLoader = new FXMLLoader();
        Stage conStage;
        try {
            Parent root = fxmlLoader.load(getClass().getResource("Congr.fxml").openStream());
            conStage = new Stage();
            conStage.setTitle("Congratulations");
            Scene conScene=new Scene(root,300,300);
            conStage.setScene(conScene);
            conScene.getStylesheets().add(getClass().getResource("Congr.css").toExternalForm());

            musicOn(2);

            conStage.showAndWait();
            //generatingAlgorithm.setValue("MyMaze");

            //s.initModality(Modality.APPLICATION_MODAL);
            //save all prope
            conStage.close();
            //generateMaze();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    public void setProperties(){
        FXMLLoader fxmlLoader = new FXMLLoader();
        try {
            Parent root = fxmlLoader.load(getClass().getResource("Properties.fxml").openStream());
            propStage = new Stage();
            propStage.setTitle("Properties");
            Scene scene=new Scene(root,200,300);
            propStage.setScene(scene);
            scene.getStylesheets().add(getClass().getResource("Properties.css").toExternalForm());

            propStage.showAndWait();
            //generatingAlgorithm.setValue("MyMaze");



            //s.initModality(Modality.APPLICATION_MODAL);
            //save all prope
            propStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    public void setResizeEvent(Scene scene) {

        scene.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
                //mazeDisplayer.setWidth(newSceneWidth.doubleValue());
                mazeDisplayer.setWidth(newSceneWidth.longValue()*0.75);
                mazeDisplayer.redraw();

            }
        });
        scene.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight) {
                mazeDisplayer.setHeight(newSceneHeight.longValue()*0.75);
                //mazeDisplayer.setHeight(newSceneHeight.doubleValue());

                mazeDisplayer.redraw();
            }
        });
    }


}
