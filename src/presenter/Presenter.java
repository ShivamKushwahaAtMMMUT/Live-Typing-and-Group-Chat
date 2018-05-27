package presenter;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;
import javafx.scene.control.Button;

import model.ChatReceiver;
import model.Receiver;
import view.ChatView;
import view.View;
import bundle.Layout;

public class Presenter extends Application {
    private View view = null;

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void init() {
    }

    @Override
    public void start(Stage primaryStage) {
        Pane root = new Pane();
        Scene scene = new Scene(root, 600, 600);

        Button publishBtn = new Button("Publish");
        publishBtn.layoutXProperty().bind(scene.widthProperty().divide(3.3));
        publishBtn.layoutYProperty().bind(scene.heightProperty().divide(7));
        publishBtn.minWidthProperty().bind(scene.widthProperty().divide(2.5));
        publishBtn.minHeightProperty().bind(scene.heightProperty().divide(8));
        publishBtn.maxWidthProperty().bind(scene.widthProperty().divide(2.5));
        publishBtn.maxHeightProperty().bind(scene.heightProperty().divide(8));

        Button receiveBtn = new Button("Receive");
        receiveBtn.layoutXProperty().bind(scene.widthProperty().divide(3.3));
        receiveBtn.layoutYProperty().bind(scene.heightProperty().divide(2.5));
        receiveBtn.maxWidthProperty().bind(scene.widthProperty().divide(2.5));
        receiveBtn.maxHeightProperty().bind(scene.heightProperty().divide(8));
        receiveBtn.minWidthProperty().bind(scene.widthProperty().divide(2.5));
        receiveBtn.minHeightProperty().bind(scene.heightProperty().divide(8));

        Button chatBtn = new Button("Group Chat");
        chatBtn.layoutXProperty().bind(scene.widthProperty().divide(3.3));
        chatBtn.layoutYProperty().bind(scene.heightProperty().divide(1.5));
        chatBtn.minWidthProperty().bind(scene.widthProperty().divide(2.5));
        chatBtn.minHeightProperty().bind(scene.heightProperty().divide(8));
        chatBtn.maxWidthProperty().bind(scene.widthProperty().divide(2.5));
        chatBtn.maxHeightProperty().bind(scene.heightProperty().divide(8));

        VBox confBox = new VBox(10);
        confBox.layoutXProperty().bind(scene.widthProperty().divide(3.3));
        confBox.layoutYProperty().bind(scene.heightProperty().divide(1.2));
        confBox.minWidthProperty().bind(scene.widthProperty().divide(2.5));
        chatBtn.minHeightProperty().bind(scene.heightProperty().divide(8));
        confBox.maxWidthProperty().bind(scene.widthProperty().divide(2.5));
        confBox.maxHeightProperty().bind(scene.heightProperty().divide(8));

        TextField nameFld = new TextField();
        nameFld.setPromptText("Enter name");
        nameFld.minWidthProperty().bind(confBox.minWidthProperty());
        nameFld.minHeightProperty().bind(confBox.minHeightProperty());
        nameFld.maxWidthProperty().bind(confBox.maxWidthProperty());
        nameFld.maxHeightProperty().bind(confBox.maxHeightProperty());

        Button okBtn = new Button("Deploy");
        okBtn.minWidthProperty().bind(confBox.minWidthProperty());
        okBtn.minHeightProperty().bind(confBox.minHeightProperty());
        okBtn.maxWidthProperty().bind(confBox.maxWidthProperty());
        okBtn.maxHeightProperty().bind(confBox.maxHeightProperty());

        nameFld.setVisible(false);
        okBtn.setVisible(false);
        confBox.getChildren().addAll(nameFld,okBtn);

        publishBtn.setOnAction(e -> {
            view = new View(Layout.PUBLISHER, scene);
            scene.setRoot(view);
        });

        receiveBtn.setOnAction(e -> {
            view = new View(Layout.RECEIVER, scene);
            Thread receiver = new Thread(new Receiver(view));
            receiver.setDaemon(true);
            receiver.start();
            scene.setRoot(view);
        });

        chatBtn.setOnAction(e -> {
            nameFld.setVisible(true);
            okBtn.setVisible(true);
            //nameFld.requestFocus();
        });

        okBtn.setOnAction(e -> {
            String name = nameFld.getText().trim();
            nameFld.setText(name);
            if(name.length() > 0) {
                ChatView chatView = new ChatView(scene, name);
                scene.setRoot(chatView);
                Thread chatReceiver = new Thread(new ChatReceiver(chatView));
                chatReceiver.setDaemon(true);
                chatReceiver.start();
            }
        });

        nameFld.setOnKeyReleased(e -> {
            if(e.getCode() == KeyCode.ENTER) {
                String name = nameFld.getText().trim();
                nameFld.setText(name);
                if(name.length() > 0) {
                    ChatView chatView = new ChatView(scene, name);
                    scene.setRoot(chatView);
                    Thread chatReceiver = new Thread(new ChatReceiver(chatView));
                    chatReceiver.setDaemon(true);
                    chatReceiver.start();
                }
            }
        });

        root.getChildren().addAll(publishBtn, receiveBtn, chatBtn, confBox);
        chatBtn.requestFocus();

        primaryStage.setScene(scene);
        primaryStage.setTitle("Live Typing");
        primaryStage.show();
    }
}
