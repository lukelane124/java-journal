/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package journal.km4lvw;

import java.awt.event.MouseAdapter;
import java.util.AbstractList;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.StageStyle;
import javafx.stage.Window;

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
        mainStage.setWidth(800.0);
        mainStage.setHeight(400.0);
            
        displayJournalEntryScene();
    }
    
    void displayJournalEntryScene()
    {
        displayJournalEntry(null);
    }

    
    void displayJournalEntry(Entry chosenEntry)
    {
        mainStage.setTitle("Journal 0.0.0");
        GridPane gridPane = new GridPane();
        TextField titleField;
        if(chosenEntry == null)
        {
            titleField = new TextField(titlePrompt);
        }
        else
        {
            titleField = new TextField(chosenEntry.getEntryTitle());
        }
        
        titleField.setPrefWidth(1000000);
        gridPane.add(titleField, 0,0,5,1);
        TextArea entryField = new TextArea();
        if(chosenEntry != null) 
        {
            entryField.setText(chosenEntry.getEntryContent());
        }
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
                    if(entry == null)
                    {
                        journal.addEntry(title, entry);
                    }
                    else
                    {
                        
                    }
                    
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

       MainJournalScene = new Scene(root, mainStage.getWidth(), mainStage.getHeight());

       //primaryStage.setTitle("Hello World!");
       mainStage.setScene(MainJournalScene);
            mainStage.setResizable(true);

            mainStage.widthProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
                    setCurrentWidthToStage(number2); 
                }
            });

            mainStage.heightProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
                    setCurrentHeightToStage(number2);
                }
            });
            
       mainStage.show();
    }
    
    private void setCurrentWidthToStage(Number number2) {
        mainStage.setWidth((double) number2);
    }

    private void setCurrentHeightToStage(Number number2) {
        mainStage.setHeight((double) number2);
    }
    
    private void getResult(String prompt, String ... buttonString)
    {
        Stage window = new Stage(StageStyle.UTILITY);
        window.setTitle("Invalid Input");
        
        VBox popRoot = new VBox();
        
        Label promptLabel = new Label(prompt);
        promptLabel.setAlignment(Pos.CENTER);
        
        popRoot.getChildren().add(promptLabel);
        for (String s : buttonString)
        {
            Button b = new Button(s);
            b.setAlignment(Pos.CENTER);
            b.setOnMouseClicked(new EventHandler() {
                @Override
                public void handle(Event t) {
                    passingString = b.getText();
                    window.close();
                }
            });
            popRoot.getChildren().add(b);
            popRoot.setAlignment(Pos.CENTER);
        }
        
        StackPane container = new StackPane();
        container.getChildren().add(new Group(popRoot));
        
        BorderPane bp = new BorderPane();
        bp.setCenter(container);
        
        Scene popScene = new Scene(bp, 250, 100);
        
        window.setScene(popScene);
        window.showAndWait();
    }
    
    void showEntries()
    {
        Stage entriesWindow = new Stage(StageStyle.UTILITY);
        GridPane entriesRoot = new GridPane();
        Scene entriesScene = new Scene(entriesRoot);
        
        ListView listview = new ListView();
        listview.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.getClickCount() == 2) {
                    // need a method to show specified entry
                    Entry chosenEntry = journal.getEntry(listview.getSelectionModel().getSelectedIndex() + 1);
                    displayChosenJournalEntry(chosenEntry);
                    entriesWindow.close();
                }
            }
        });
        
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
    
    void displayChosenJournalEntry(Entry chosenEntry) {
        displayJournalEntry(chosenEntry);
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       launch(args);
    }
}
