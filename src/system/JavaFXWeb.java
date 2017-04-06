package system; /**
 * Created by shivr on 4/1/2017.
 */

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.net.SocketFactory;
import javax.net.ssl.SSLSocketFactory;
import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.RMIClientSocketFactory;

import ClientVoting.*;

public class JavaFXWeb extends Application implements RMIClientSocketFactory, Serializable {
    private Scene scene;
    private HTMLBrowser myBrowser;
    private PopupWindow popupWindow;
    public static ClientVotingInterface stub = null;

    public static void main(String[] args) {
        try{
            stub = (ClientVotingInterface) Naming.lookup("rmi://localhost:5000/voting");
        }
        catch(Exception e){
            e.printStackTrace();
        }
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Voting Application v1.0");

        if(stub==null){
            popupWindow = new PopupWindow("There was an error connecting to the server. Please close the program.");
            primaryStage.setScene(popupWindow.setupScene());
            primaryStage.show();
        }
        else {
            // Determine if the client can vote.
            Boolean getServerStatus = null;
            try {
                getServerStatus = stub.canVote();
            } catch (RemoteException e) {
                e.printStackTrace();
            }

            // Display either a popup or the voting interface.
            if (!getServerStatus) {
                popupWindow = new PopupWindow("The Voting Process has not commenced yet.");
                primaryStage.setScene(popupWindow.setupScene());
                primaryStage.show();
            } else {
                myBrowser = new HTMLBrowser();
                scene = new Scene(myBrowser, 850, 700);
                primaryStage.setScene(scene);
                scene.getStylesheets().add("Files/BrowserToolbar.css");
                primaryStage.show();
            }
        }
    }

    public Socket createSocket(String host, int port) throws IOException {
        SocketFactory factory = SSLSocketFactory.getDefault();
        Socket socket = factory.createSocket(host, port);
        return socket;
    }

}