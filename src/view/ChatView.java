package view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import model.ChatPublisher;

public class ChatView extends BorderPane {
    private Scene scene = null;
    private VBox chatBox = null;
    private ChatPublisher chatPublisher = null;
    private String name = null;

    public ChatView(Scene scene, String name) {
        this.scene = scene;
        this.name = name;
        chatBox = new VBox(25);
        chatPublisher = new ChatPublisher();
        generateView();
    }

    private void generateView() {
        HBox header = new HBox();
        Label headerLbl = new Label("Created By Prominent Group");
        HBox.setMargin(headerLbl, new Insets(0,0,0, 20));
        header.getChildren().add(headerLbl);
        setTop(header);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.minWidthProperty().bind(scene.widthProperty().subtract(20));
        scrollPane.minHeightProperty().bind(scene.heightProperty().divide(1.2));
        scrollPane.maxWidthProperty().bind(scene.widthProperty().subtract(20));
        scrollPane.maxHeightProperty().bind(scene.heightProperty().divide(1.2));

        scrollPane.setContent(chatBox);
        setCenter(scrollPane);

        HBox footer = new HBox(15);
        footer.minWidthProperty().bind(scene.widthProperty().divide(1.1));
        footer.minHeightProperty().bind(scene.heightProperty().divide(10));
        footer.maxWidthProperty().bind(scene.widthProperty().divide(1.1));
        footer.maxHeightProperty().bind(scene.heightProperty().divide(10));

        TextField inputFld = new TextField();
        inputFld.minWidthProperty().bind(scene.widthProperty().divide(1.25));
        inputFld.minHeightProperty().bind(scene.heightProperty().divide(12));
        inputFld.maxWidthProperty().bind(scene.widthProperty().divide(1.25));
        inputFld.maxHeightProperty().bind(scene.heightProperty().divide(12));
        inputFld.requestFocus();
        inputFld.setOnKeyReleased(e -> {
            if(e.getCode() == KeyCode.ENTER) {
                String content = inputFld.getText().trim();
                if(content.length() > 0) {
                    inputFld.setText("");
                    chatPublisher.publish(name, content);
                }
            }
        });

        Button sendBtn = new Button("Send");
        sendBtn.minWidthProperty().bind(scene.widthProperty().divide(7.5));
        sendBtn.minHeightProperty().bind(scene.heightProperty().divide(12));
        sendBtn.maxWidthProperty().bind(scene.widthProperty().divide(7.5));
        sendBtn.maxHeightProperty().bind(scene.heightProperty().divide(12));
        sendBtn.setOnAction(e -> {
            String content = inputFld.getText().trim();
            if(content.length() > 0) {
                inputFld.setText("");
                chatPublisher.publish(name, content);
            }
        });

        HBox.setMargin(inputFld, new Insets(0,0,0,10));
        footer.getChildren().addAll(inputFld, sendBtn);
        setBottom(footer);

//        updateChat("Shivam kushwaha", "Hi there I am using this application for testing purpose. I am a prominent developer.Hi there I am using this application for testing purpose. I am a prominent developer.Hi there I am using this application for testing purpose. I am a prominent developer.Hi there I am using this application for testing purpose. I am a prominent developer.Hi there I am using this application for testing purpose. I am a prominent developer.Hi there I am using this application for testing purpose. I am a prominent developer.");
//        updateChat("Rahul Maurya", "Hi there I am using this application for testing purpose. I am a prominent developer.Hi there I am using this application for testing purpose. I am a prominent developer.Hi there I am using this application for testing purpose. I am a prominent developer.Hi there I am using this application for testing purpose. I am a prominent developer.Hi there I am using this application for testing purpose. I am a prominent developer.Hi there I am using this application for testing purpose. I am a prominent developer.");
//        updateChat("Shivam kushwaha", "Hi there I am using this application for testing purpose. I am a prominent developer.Hi there I am using this application for testing purpose. I am a prominent developer.Hi there I am using this application for testing purpose. I am a prominent developer.Hi there I am using this application for testing purpose. I am a prominent developer.Hi there I am using this application for testing purpose. I am a prominent developer.Hi there I am using this application for testing purpose. I am a prominent developer.");
//        updateChat("Rahul Maurya", "Hi there I am using this application for testing purpose. I am a prominent developer.Hi there I am using this application for testing purpose. I am a prominent developer.Hi there I am using this application for testing purpose. I am a prominent developer.Hi there I am using this application for testing purpose. I am a prominent developer.Hi there I am using this application for testing purpose. I am a prominent developer.Hi there I am using this application for testing purpose. I am a prominent developer.");

    }

    public void updateChat(String name, String content) {
        VBox chatContent = generateChatContent(name, content);
        chatBox.getChildren().add(chatContent);
    }

    private VBox generateChatContent(String name, String content){
        VBox box = new VBox();
        Label nameLbl = new Label(name);
        nameLbl.setStyle("-fx-font-size: 20px;" + "-fx-font-color: blue");
        StringBuilder builder = new StringBuilder();
        for(int i=0; i<content.length(); i+=80) {
            int flag = i+80 < content.length() ? i+80 : content.length();
            builder.append(content.substring(i, flag)).append("\n");
        }
        Label contentLbl = new Label(builder.toString());
        VBox.setMargin(nameLbl, new Insets(0,0,0,10));
        VBox.setMargin(contentLbl, new Insets(0,0,0,10));
        box.getChildren().addAll(nameLbl, contentLbl);
        return box;
    }
}
