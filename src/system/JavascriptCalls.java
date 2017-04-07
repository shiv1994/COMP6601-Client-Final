package system;


import javafx.animation.PauseTransition;
import javafx.util.Duration;

import java.rmi.RemoteException;

/**
 * Created by shivr on 4/2/2017.
 */
public class JavascriptCalls {
    private PopupWindow popupWindow;
    public void callFromJavascript(String selectedParty) {
        Boolean result = null;
        System.out.println("The Selected Party is: "+selectedParty);
        try {
            result = JavaFXWeb.stub.castVote(selectedParty);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        if(result!=null){
            if(result){
                displayPopupWindow("Your vote has been successfully submitted. This dialog will disappear after one minute.",60);
            }
            else{
                displayPopupWindow("Your vote was not submitted. Please request assistance.", 10);
            }
        }
        else{
            displayPopupWindow("Your vote was not submitted. Please request assistance.", 10);
        }
    }

    private void displayPopupWindow(String message, Integer time){
        popupWindow = new PopupWindow(message);
        popupWindow.showPopupWindow();
        PauseTransition pause = new PauseTransition(Duration.seconds(time));
        pause.setOnFinished(e -> popupWindow.closePopupWindow());
        pause.play();
    }
}
