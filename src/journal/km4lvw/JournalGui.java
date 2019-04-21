/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package journal.km4lvw;

import java.util.AbstractList;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.event.Event;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.FlowPane;
import javafx.stage.StageStyle;

/**
 *
 * @author Lucas
 */

public class JournalGui extends Application {
    private static final String titlePrompt = "Title";
    private final Journal journal = Journal.getInstance();
    private Scene MainJournalScene, PopupScene;
    private Stage mainStage;
    
    private String passingString;
    
    @Override
    public void start(Stage mainWindow) {
        mainStage = mainWindow;
        displayJournalEntryScene();
        
    }

    
    void displayJournalEntryScene()
    {
        mainStage.setTitle("Journal 0.0.0");
        GridPane gridPane = new GridPane();
        TextField titleField = new TextField(titlePrompt);
        titleField.setPrefWidth(1000000);
        gridPane.add(titleField, 0,0,5,1);
        TextArea entryField = new TextArea();
        gridPane.add(entryField, 1, 5, 20, 20);

        Button entrySubmit = new Button();
        entrySubmit.setText("Complete Entry");
        entrySubmit.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) 
            {
                String title = titleField.getText();
                String entry = entryField.getText();
                if ( !entry.equals("") && !title.equals(""))
                {
                    journal.addEntry(title, entry);
                    
                    titleField.clear();
                    entryField.clear();
                    titleField.promptTextProperty().set(titlePrompt);
                }
                else
                {
                    getResult("Please enter a non-empty title and entry", "Okay");
                }
            }
       });
       gridPane.add(entrySubmit, 6, 0);
	  Button listEntries = new Button("List Entries");
	  listEntries.setOnAction(new EventHandler<ActionEvent>() {
		  @Override
		  public void handle(ActionEvent event) {
			 showEntries();
		  }
	   });
	  gridPane.add(listEntries, 7, 0);

       StackPane root = new StackPane();
       //root.getChildren().add(entrySubmit);
       root.getChildren().add(gridPane);
       //root.getChildren().add(titleField);

       MainJournalScene = new Scene(root, 300, 250);

       //primaryStage.setTitle("Hello World!");
       mainStage.setScene(MainJournalScene);
       mainStage.show();
    }
    

    
    private void getResult(String prompt, String ... buttonString)
    {
        Stage window = new Stage(StageStyle.UTILITY);
        GridPane popRoot = new GridPane();
        Scene popScene = new Scene(popRoot);        
        popRoot.add(new Label(prompt), 0, 0);
        int buttonOffset = 0;
        for (String s : buttonString)
        {
            Button b = new Button(s);
            b.setOnMouseClicked(new EventHandler() {
                @Override
                public void handle(Event t) {
                    passingString = b.getText();
                    window.close();
                }
            });
            popRoot.add(b, buttonOffset++, 1);
        }
        window.setScene(popScene);
        window.showAndWait();
    }
    
    void showEntries()
    {
        Stage entriesWindow = new Stage(StageStyle.UTILITY);
        GridPane entriesRoot = new GridPane();
        Scene entriesScene = new Scene(entriesRoot); 
        ListView listview = new ListView();
        ObservableList<String> list = FXCollections.observableArrayList();
        AbstractList<String> titles = journal.getTitles();
        String [] colName = {"Title"};
        for (String s : titles)
        {
            listview.getItems().add(s);
        }
        Label l = new Label("Titles");
        entriesRoot.add(l, 0,0);
        entriesRoot.add(listview, 0,1);
        entriesWindow.setScene(entriesScene);
        entriesWindow.showAndWait();
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       launch(args);
    }
}
