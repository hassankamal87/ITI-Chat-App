package com.whisper.client.presentation.controllers;

import com.whisper.client.HelloApplication;
import com.whisper.client.MyApp;
import com.whisper.client.business.services.ContactService;
import com.whisper.client.business.services.NotificationService;
import com.whisper.client.business.services.UserSearchService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.example.entities.NotifactionType;
import org.example.entities.Notification;
import org.example.entities.Type;
import org.example.entities.User;
import org.example.serverinterfaces.SendContactsInvitationServiceInt;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class notificationController implements Initializable {

    @FXML
    private ListView notificationLayout;

    @FXML
    private ScrollPane scrollPane;

    NotificationService notificationService= new NotificationService();
    HBox hBox;
    notificationItemController nic;
    List<Notification> notifications = new ArrayList<>(notifications());

    ObservableList<HBox> boxes = FXCollections.observableArrayList();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        for(int i=0;i<notifications.size();i++) {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("views/notificationItemView.fxml"));
            try {
                hBox = fxmlLoader.load();
                nic = fxmlLoader.getController();
                nic.setData(notifications.get(i));
                boxes.add(hBox);
                System.out.println(i);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        notificationLayout.setItems(boxes);
        for (int i=0;i<boxes.size();i++){
            Button b = (Button) boxes.get(i).getChildren().get(2);
            int finalI = i;
            b.setOnAction(event->deleteAction(event, finalI));
        }



    }
    private void deleteAction(Event event,int i) {

        Button deleteButton = (Button) event.getSource();
        HBox notificationBox = (HBox) deleteButton.getParent();
        boxes.remove(notificationBox);

        Notification not=notifications.get(i);
        //notifications.remove(not);
        notificationService.deleteNotification(not.getNotificationId());


    }
    private List<Notification> notifications() {
        List<Notification>notifications =notificationService.getNotifications(MyApp.getInstance().getCurrentUser().getUserId());
        return notifications;
    }
}