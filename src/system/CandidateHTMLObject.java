package system;

/**
 * Created by shivr on 3/30/2017.
 * This class serves the function of appending to HTML text the image as well was the name of the candidate. The generated string will then be
 * used to populate the HTML page that is being displayed
 */
public class CandidateHTMLObject {

    private String partyName;
    private String imageRef;
    private String description;

    //The following string contains the structure for each system.Candidate in the HTML file.
    private String htmlData;

    public CandidateHTMLObject(String partyName, String imageRef, String description){
        this.description = description;
        this.imageRef = imageRef;
        this.partyName = partyName;
        htmlData =
                " <div class=\"col-sm-4 text-center\">\n" +
                "      <h3>" +partyName+ "</h3>\n" +
                "      <h4>" +description+ "</h4>\n" +
                "      <img src=\""+imageRef+"\" class=\"img-responsive\" alt=\"AlternativeImg\">\n" +
                "      <br>\n" +
                " </div>\n";
    }

    public String getPartyName() {
        return partyName;
    }

    public String getHTMLData(){
        return this.htmlData;
    }
}
