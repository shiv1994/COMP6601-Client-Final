package system;

import java.rmi.RemoteException;

/**
 * Created by shivr on 4/2/2017.
 */
public class JavascriptCalls {

    public void callFromJavascript(String selectedParty) {
        Boolean result = null;
        System.out.println("The Selected Party is: "+selectedParty);
        try {
            result = JavaFXWeb.stub.castVote(selectedParty);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        if(result!=null){
            System.out.println("The Result is: "+result);
        }
    }

}
