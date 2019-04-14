/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package journal.km4lvw;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author Lucas
 */



public class JournalGui extends Application {
    private Stage window;
    private Journal journal = Journal.ReadEntries();
    
    @Override
    public void start(Stage mainWindow) {
	   mainWindow.setTitle("Journal 0.0.0");
	   GridPane gridPane = new GridPane();
	   TextField titleField = new TextField("Title");
	   titleField.setPrefWidth(1000000);
	   gridPane.add(titleField, 0,0,5,1);
	   TextArea entryField = new TextArea();
	   gridPane.add(entryField, 1, 5, 20, 20);
	   
	   Button entrySubmit = new Button();
	   entrySubmit.setText("Complete Entry");
	   entrySubmit.setOnAction(new EventHandler<ActionEvent>() {
		  
		  @Override
		  public void handle(ActionEvent event) {
			 String title = titleField.getText();
			 String entry = entryField.getText();
			 
		  }
	   });
	   gridPane.add(entrySubmit, 6, 0);
	   StackPane root = new StackPane();
	   //root.getChildren().add(entrySubmit);
	   root.getChildren().add(gridPane);
	   //root.getChildren().add(titleField);
	   
	   Scene scene = new Scene(root, 300, 250);
	   
	   //primaryStage.setTitle("Hello World!");
	   mainWindow.setScene(scene);
	   mainWindow.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
	   launch(args);
    }
    
}
