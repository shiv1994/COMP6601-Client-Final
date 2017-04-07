package system;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by shivr on 3/30/2017.
 */
public class HTMLCode {

    private String candidateCode;

    private String selectCode;

    private ArrayList<CandidateHTMLObject> candidateHTMLObjectArrayList;

    private String htmlCodeA = "<!DOCTYPE html>\n" +
            "<html lang=\"en\">\n" +
            "<head>\n" +
            "  <title>Bootstrap Example</title>\n" +
            "  <meta charset=\"utf-8\">\n" +
            "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
//            "  <link rel=\"stylesheet\" href=\"..Files/bootstrap-4.0.0-alpha.6-dist/css/bootstrap.css\">\n" +
            "<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css\">\n"+
            "  <script src=\"..Files/bootstrap-4.0.0-alpha.6-dist/js/bootstrap.min.js\"> </script>\n"+
            "  <script> function getSelectedElement(){\n" +
            "    return (document.getElementById(\"partyNames\").value);\n" +
            "  }</script>\n" +
            "  <script> function displaySelectedParty(){\n" +
            "    document.getElementById(\"displaySelectedParty\").innerHTML = \"You selected: <b>\" + getSelectedElement() + \"</b>\";\n" +
            "    document.getElementById(\"buttonID\").style.display = \"inline\";\n" +
            "  }</script>\n" +
            "  <script> function callJavaFX(){\n" +
            "    var selectedParty = getSelectedElement();\n" +
            "    app.callFromJavascript(selectedParty);\n" +
            "  }</script>\n" +
            " <script> function displayAlert(){\n" +
            "    alert();\n" +
            "  }</script>\n"+
            "</head>\n" +
            "<body>\n" +
            "  <div class=\"jumbotron text-center\">\n" +
            "    <h2>Voting Systems Inc.</h2>\n" +
            "    <p>Please select your candidate and then click the vote button.</p>\n" +
            "  </div>";

    private String htmlCodeB = "<div class=\"container\">\n" +
            " <div class=\"row\"> \n";

    private String htmlCodeC =
            "</div>\n" +
            " <div class=\"row\"> \n";

    private  String htmlCodeD=
            "  </div>\n" +
            "  <div class=\"row text-center\">\n" +
            "      <hr>\n" +
            "      <p id=\"displaySelectedParty\"> <b> No Party Selected. </b> </p>\n" +
            "      <div id=\"buttonID\" style=\"display:none\";>\n" +
            "        <button type=\"button\" class=\"btn btn-success\" onclick=\"callJavaFX();displayAlert();\">Click to Vote</button>\n" +
            "      </div>\n" +
            "    <br>\n" +
            "  </div>\n" +
            "</div>\n" +
            "</body>\n" +
            "</html>";

    public HTMLCode(){
        candidateCode = "";
        selectCode = "";
        candidateHTMLObjectArrayList = new ArrayList<>();
        loadCandidateDisplayArrayList();
        buildHTMLViewCode();
        buildHTMLSelectCode();
        writeHTMLToFile();
    }

    private void loadCandidateDisplayArrayList(){

        ArrayList<Candidate> candidates = null;

        try{
           candidates =  JavaFXWeb.stub.fetchCandidates();
        }
        catch(Exception e){
            e.printStackTrace();
        }

        for(Candidate c: candidates){
//            System.out.println(c);
            candidateHTMLObjectArrayList.add(new CandidateHTMLObject(c.getName(), c.getImg() , c.getDescription()));
        }

    }

    private void buildHTMLViewCode(){
        for ( CandidateHTMLObject candidateHTMLObject : candidateHTMLObjectArrayList) {
            candidateCode += candidateHTMLObject.getHTMLData();
       }
    }

    private void buildHTMLSelectCode(){
        selectCode = "   <hr>\n     Choose Your Desired Party: " + "\n   <select id=\"partyNames\" onchange=displaySelectedParty();>\n";
        selectCode += "             <option disabled selected value> -- Select an Option -- </option>\n";
        for ( CandidateHTMLObject candidateHTMLObject : candidateHTMLObjectArrayList) {
            selectCode += "         <option value=\""+ candidateHTMLObject.getPartyName()+"\">"+ candidateHTMLObject.getPartyName()+"</option> \n";
        }
        selectCode += "   </select>\n";
    }

    private void writeToFile(String generatedCode){
        try {
            String username = System.getProperty("user.name");
            BufferedWriter out = new BufferedWriter(new FileWriter("C:\\Users\\"+username+"\\Desktop\\voting-voter-client\\src\\Files\\main.html"));
            out.write(generatedCode);
            out.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    private void writeHTMLToFile(){
        String generatedCode = htmlCodeA + htmlCodeB + candidateCode + htmlCodeC + selectCode + htmlCodeD;
        // Implement function to store generated output to html file on disk. This is necessary for loading the template again from the hyperlinks in the program.
        writeToFile(generatedCode);
    }
}
