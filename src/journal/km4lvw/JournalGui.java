/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package journal.km4lvw;

import java.awt.FileDialog;
import java.awt.Frame;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.AbstractList;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.event.Event;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.StageStyle;
import sun.security.action.OpenFileInputStreamAction;

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
        mainStage.setTitle("JLite 0.0.0");
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
        
        //ntryField.prefHeightProperty().bind(gridPane.prefHeightProperty());
        entryField.setPrefSize( Double.MAX_VALUE, Double.MAX_VALUE );
        entryField.setWrapText(true);
        gridPane.add(entryField, 1, 5, 25, 25);

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
                if(chosenEntry == null)
                {
                    journal.addEntry(title, entry);
                }
                else
                {
                    journal.appendEntry(chosenEntry, title, entry);
                }

                displayJournalEntry(null);
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
        if (chosenEntry != null)
        {
            Button deleteEntry = new Button("Delete Entry");
            deleteEntry.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    chosenEntry.deleteEntry();
                    displayChosenJournalEntry(null);
                }
            });
            gridPane.add(deleteEntry, 8, 0);
        }
        
        if (chosenEntry != null)
        {
            Button newEntry = new Button("New Entry");
            newEntry.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    getResult("Would you like to save the current Entry?", "Yes", "No", "Cancel");
                    String title = titleField.getText();
                    String entry = entryField.getText();
                    switch (passingString)
                    {
                        case "Cancel":
                            break;
                        case "Yes":
                            if ( !entry.equals("") && !title.equals(""))
                            {                                
                                journal.appendEntry(chosenEntry, title, entry);
                                displayJournalEntry(null);
                            }
                            else
                            {
                                getResult("Please enter a non-empty title and entry", "Okay");
                            }
                        
                        default:
                            displayChosenJournalEntry(null);
                    }
                    
                }
            });
            gridPane.add(newEntry, 9, 0);
        }
        
        
        Button addFile = new Button("Add File");
        addFile.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Open Resource File");
                File chosenFile = fileChooser.showOpenDialog(mainStage);
                if (chosenEntry != null)
                {
                    try {
                        byte[] fileBytes = Files.readAllBytes(chosenFile.toPath());
                        chosenEntry.addBlob(fileBytes);
                        displayChosenJournalEntry(chosenEntry);
                    } catch (IOException ex) {
                        Logger.getLogger(JournalGui.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        gridPane.add(addFile, 10, 0);
        
        if (chosenEntry != null && chosenEntry.hasBlob())
        {
            Button deleteFile = new Button("Delete File");
            deleteFile.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    chosenEntry.deleteBlob(false);
                    displayChosenJournalEntry(null);
                }
            });
            gridPane.add(deleteFile, 11, 0);
        }

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
                    Entry chosenEntry = (Entry) listview.getSelectionModel().getSelectedItem();
                    displayChosenJournalEntry(chosenEntry);
                    entriesWindow.close();
                }
            }
        });
        
        ObservableList<Entry> list = FXCollections.observableArrayList();
        AbstractList<Entry> entries = journal.getParentEntries();
        String [] colName = {"Title"};

        if (entries != null)
        {
            listview.getItems().addAll(entries);
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
