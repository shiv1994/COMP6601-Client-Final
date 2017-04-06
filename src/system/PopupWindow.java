package system;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Created by shivr on 4/4/2017.
 */
public class PopupWindow {

    private Stage window;
    private String messageToDisplay;

    public PopupWindow(String messageToDisplay){

        this.messageToDisplay = messageToDisplay;

        setupWindow();

    }

    private void setupWindow(){
        window = new Stage();
        window.initStyle(StageStyle.UNDECORATED);
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Notice");
        window.setMinWidth(300);
        window.setScene(setupScene());
    }

    public Scene setupScene(){
        Label label = new Label();
        label.setTextFill(Color.TOMATO);
        label.setText(messageToDisplay);

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, 700, 200);

        return scene;
    }
    public void showPopupWindow(){
        window.show();
    }

    public void closePopupWindow(){
        window.close();
    }
}
