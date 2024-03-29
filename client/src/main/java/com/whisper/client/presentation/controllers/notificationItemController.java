package com.whisper.client.presentation.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.example.entities.NotifactionType;
import org.example.entities.Notification;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class notificationItemController implements Initializable {
    @FXML
    private Button deleteButton;

    @FXML
    private Label description;

    @FXML
    private Label header;

    @FXML
    private ImageView notificationIcon;


    public void setData(Notification notification){
        try{

            if(notification.getType()==NotifactionType.msg){
                notificationIcon.setImage( new Image(getClass().getResourceAsStream("/com/whisper/client/images/message.jpg")));
                header.setText("You hava a new message from "+ notification.getFromUserName());
               description.setText("you have a new message in  conversations from "+ notification.getFromUserName());
           }
           else if(notification.getType()==NotifactionType.inv){
                notificationIcon.setImage( new Image(getClass().getResourceAsStream("/com/whisper/client/images/invitation.jpg")));
                header.setText(notification.getBody()+notification.getFromUserName());

           }
            else if(notification.getType()==NotifactionType.board){
                notificationIcon.setImage( new Image(getClass().getResourceAsStream("/com/whisper/client/images/broadcast.png")));
                header.setText("You hava a new Admin Announcement");
            }



        }catch (Exception e){
            e.printStackTrace();
        }

        try{

        }catch (NullPointerException e){
            e.printStackTrace();
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {


    }



}
