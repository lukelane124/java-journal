/*
 * This software package was created by KM4LVW.
 * If you find it, use it wisely
 */

package SimpleGuiTools;

import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Popup;
import javafx.stage.Stage;

/**
 *
 * @author Tommy Lane <km4lvw@km4lvw.com>
 */
public class SPopUp 
{
    public String result = null;
    public SPopUp(Stage brush, String prompt, String ... buttonText)
    {
       StackPane root = new StackPane();
       
        HBox layout = new HBox(10);
        layout.setStyle("-fx-background-color: cornsilk; -fx-padding: 10;");
        Label lbl = new Label(prompt);
        layout.getChildren().addAll(lbl);
        for (String s : buttonText)
        {
            Button b = new Button(s);
            b.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent t) {
                    result = b.getText();
                }
            });
            layout.getChildren().addAll(b);
        }

        Scene scene = new Scene(root, 300, 250);
        //primaryStage.setTitle("Hello World!");
        brush.setScene(scene);
        brush.show();
        brush.setScene(new Scene(layout));
        brush.show();
        
    }
    
    public String getResult()
    {
        return result;
    }
}
