package system;

import javafx.animation.PauseTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Hyperlink;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebEvent;
import javafx.scene.web.WebView;
import javafx.util.Duration;
import netscape.javascript.JSObject;

/**
 * Created by shivr on 4/5/2017.
 */

class MyBrowser extends Region {

    private PopupWindow popupWindow;

    private static String userName = System.getProperty("user.name");

    private String[] urls = new String[]{
            "file:///C:\\Users\\"+userName+"\\Desktop\\voting-voter-client\\src\\Files\\main.html",
            "file:///C:\\Users\\"+userName+"\\Desktop\\voting-voter-client\\src\\Files\\help.html",
    };

    private String[] captions = new String[]{
            "Vote",
            "Help"
    };

    private String[] imageFiles = new String[]{
            "../Files/homeIcon.png",
            "../Files/helpIcon.png"
    };

    private HBox toolbox;

    private WebView webView = new WebView();

    public MyBrowser(){

        HTMLCode htmlCode = new HTMLCode();

        WebEngine webEngine = webView.getEngine();

        getStyleClass().add("browser");

        webEngine.setUserAgent("AppleWebKit/537.44");

        webEngine.load(String.valueOf(this.getClass().getResource("file:///C:\\Users\\shivr\\Desktop\\tester\\main.html")));

        webEngine.getLoadWorker().stateProperty().addListener(
                new ChangeListener<Worker.State>(){
                    @Override
                    public void changed(ObservableValue<? extends Worker.State> ov, Worker.State oldState, Worker.State newState) {
                        if(newState == Worker.State.SUCCEEDED){
                            JSObject window = (JSObject)webEngine.executeScript("window");
                            window.setMember("app", new JavascriptCalls());
                        }
                    }
                });

        webEngine.setOnAlert(new EventHandler<WebEvent<String>>(){
            @Override
            public void handle(WebEvent<String> arg0) {
                popupWindow = new PopupWindow("Vote has been submitted. This dialog will disappear after one minute.");
                popupWindow.showPopupWindow();
                PauseTransition pause = new PauseTransition(Duration.seconds(60));
                pause.setOnFinished(e -> popupWindow.closePopupWindow());
                pause.play();
            }

        });


        JSObject window = (JSObject)webEngine.executeScript("window");
        window.setMember("app", new JavascriptCalls());

        final Hyperlink[] hyperlinks = new Hyperlink[captions.length];
        final Image[] images = new Image[imageFiles.length];

        // Adding hyperlinks to a toolbar which will then be displayed in the window for easy navigation.
        for (int i = 0; i < captions.length; i++) {
            final Hyperlink hpl = hyperlinks[i] = new Hyperlink(captions[i]);
            Image image = images[i] = new Image(getClass().getResourceAsStream(imageFiles[i]));
            hpl.setGraphic(new ImageView(image));
            final String url = urls[i];

            hpl.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    webEngine.load(url);
                }
            });
        }

        // Creation of the toolbar
        toolbox = new HBox();
        toolbox.getStyleClass().add("browser-toolbar");
        toolbox.getChildren().addAll(hyperlinks);

        getChildren().add(toolbox);
        getChildren().add(webView);

    }

    @Override
    protected void layoutChildren(){
        double w = getWidth();
        double h = getHeight();
        double toolboxHeight = toolbox.prefHeight(w);
        layoutInArea(webView, 0, 0, w, h-toolboxHeight, 0, HPos.CENTER, VPos.CENTER);
        layoutInArea(toolbox, 0, h-toolboxHeight, w, toolboxHeight, 0, HPos.CENTER, VPos.CENTER);
    }

}
