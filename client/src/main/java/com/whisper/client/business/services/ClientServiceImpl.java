package com.whisper.client.business.services;

import com.whisper.client.HelloApplication;
import com.whisper.client.MyApp;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.util.Duration;
import org.controlsfx.control.NotificationPane;
import org.controlsfx.control.Notifications;
import org.example.clientinterfaces.ClientServiceInt;
import org.example.entities.NotifactionType;
import org.example.entities.Notification;
import org.example.entities.Status;
import org.example.entities.User;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ClientServiceImpl extends UnicastRemoteObject implements ClientServiceInt {


    private static ClientServiceImpl instance = null;


    public static synchronized ClientServiceImpl getInstance() throws RemoteException {
        if(instance == null)
            instance = new ClientServiceImpl();
        return instance;
    }

    private ClientServiceImpl() throws RemoteException {
        super();
    }

    private int ClientId = MyApp.getInstance().getCurrentUser().getUserId();

    @Override
    public void receiveNotification(Notification notification) throws RemoteException {

        NotificationService notificationService = new NotificationService();
        NotifactionType notifactionType = notification.getType();
        if(notifactionType==NotifactionType.inv){
           notificationService.sendInvitation(notification);
        }
        else if(notifactionType==NotifactionType.msg){
            notificationService.sendMessage(notification);
        }
        else if(notifactionType==NotifactionType.board){
            notificationService.sendBroadCast(notification);
        }
    }

    @Override
    public void ClientStatusAnnounce(User user) throws RemoteException {

        System.out.println(user.getUserName()+" "+user.getUserId());
            Platform.runLater(()->{

                System.out.println("here");
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("views/notificationBodyView.fxml"));
                try {
                    HBox hBox = fxmlLoader.load();

                    Label l =(Label)hBox.getChildren().get(0);
                    l.setText("Your friend "+user.getUserName()+" is now "+user.getStatus());
                    Notifications.create()
                            .title("Status changed")
                            .graphic(hBox)
                            .hideAfter(Duration.seconds(10))
                            .show();
                    System.out.println("send success");
                } catch (IOException e) {
                    System.out.println("Exception is  : "+e.getMessage());
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Sorry there is a problem with connection", ButtonType.OK);
                    alert.showAndWait();
                    Platform.exit();
                    System.exit(0);
                }

            });


    }

    @Override
    public void recieve(String contact) throws RemoteException {
        Platform.runLater(()->{

            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("views/notificationBodyView.fxml"));
            try {
                HBox hBox = fxmlLoader.load();

                Label l =(Label)hBox.getChildren().get(0);
                l.setText("You have accepted invitation from "+contact);
                Notifications.create()
                        .title("Accepted")
                        .graphic(hBox)
                        .hideAfter(Duration.seconds(10))
                        .show();
            } catch (IOException e) {
                System.out.println("Exception is  : "+e.getMessage());

            }

        });
    }

    public int getClientId() throws RemoteException{
        return ClientId;
    }
}
