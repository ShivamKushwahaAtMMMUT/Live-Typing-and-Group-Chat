package view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import bundle.Layout;
import model.Publisher;

public class View extends BorderPane {
    private Layout layout = null;
    private Scene scene = null;
    private TextArea textArea = null;
    private Publisher publisher = null;

    public View(Layout layout, Scene scene) {
        this.layout = layout;
        this.scene = scene;
        generateView();
    }

    private void generateView() {
        HBox header = new HBox();
        Label headerLbl = new Label("Created By Prominent Group");
        HBox.setMargin(headerLbl, new Insets(0,0,0, 20));
        header.getChildren().add(headerLbl);
        setTop(header);

        textArea = new TextArea();
        textArea.setWrapText(true);
        textArea.minWidthProperty().bind(scene.widthProperty().subtract(20));
        textArea.minHeightProperty().bind(scene.heightProperty().subtract(100));
        textArea.maxWidthProperty().bind(scene.widthProperty().subtract(20));
        textArea.maxHeightProperty().bind(scene.heightProperty().subtract(100));
        setCenter(textArea);

        if(layout == Layout.PUBLISHER) {
            publisher = new Publisher();
            textArea.setOnKeyReleased(e -> {
                String data = textArea.getText();
                publisher.publish(data);
            });
        }

        if(layout == Layout.RECEIVER) {
            textArea.setEditable(false);
        }

        Label footer = new Label("Data is transmitted through multicasting, may cause latency.");
        setBottom(footer);
    }

    public void updateContent(String content) {
        textArea.setText(content);
    }
}
