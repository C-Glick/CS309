package com.tc_4.carbon_counter.sockets;

import java.io.IOException;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import com.tc_4.carbon_counter.databases.NotificationDatabase;
import com.tc_4.carbon_counter.models.Notification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller      // this is needed for this to be an endpoint to springboot
@ServerEndpoint(value = "/notify/{username}")  // this is Websocket url
public class NotificationSocket {

  // cannot autowire static directly (instead we do it by the below
  // method
	private static NotificationDatabase notificationDatabase; 

	/*
   * Grabs the MessageRepository singleton from the Spring Application
   * Context.  This works because of the @Controller annotation on this
   * class and because the variable is declared as static.
   * There are other ways to set this. However, this approach is
   * easiest.
	 */
	@Autowired
	public void setNotificationDatabase(NotificationDatabase database) {
		notificationDatabase = database;  // we are setting the static variable
	}

	// Store all socket session and their corresponding username.
	private static Map<Session, String> sessionUsernameMap = new Hashtable<>();
	private static Map<String, Session> usernameSessionMap = new Hashtable<>();

	private final Logger logger = LoggerFactory.getLogger(NotificationSocket.class);

	@OnOpen
	public void onOpen(Session session, @PathParam("username") String username) 
      throws IOException {

		logger.info( username + " has opened a connection");

        // store connecting user information
		sessionUsernameMap.put(session, username);
		usernameSessionMap.put(username, session);

        //Send any unread notifications to the newly connected user
        sendUnreadNotificationsToUser(username);
	}


	@OnMessage
	public void onMessage(Session session, String message) throws IOException {
        String toUsername = "";

        if (message.startsWith("@")) {
            toUsername = message.split(" ")[0].substring(1); 
            message = message.substring(toUsername.length() + 1);
        }

		// Handle new messages
		logger.info("Received Message: " + message);
		String username = sessionUsernameMap.get(session);

        Notification n = new Notification();

        if(toUsername != ""){
            n.setUsername(username);
            n.setIsRead(false);
            n.setMessage(message);

            sendNotificationToUser(n);
        }else { // broadcast
            n.setMessage(message);
            n.setUsername("");
            n.setIsRead(false);

			broadcast(n);
		}

	}


	@OnClose
	public void onClose(Session session) throws IOException {
        String username = sessionUsernameMap.get(session);

        logger.info(username + " has closed the connection");

        // remove the user connection information
		sessionUsernameMap.remove(session);
		usernameSessionMap.remove(username);
	}


	@OnError
	public void onError(Session session, Throwable throwable) {
		// Do error handling here
		logger.info("Error");
		throwable.printStackTrace();
	}


	public void sendNotificationToUser(Notification notification) {
        Session session = usernameSessionMap.get(notification.getUsername());

        if(notification.getUsername() != "" && session == null){
            notification.setIsRead(false);
            notificationDatabase.save(notification);
        }else{
            try {
                session.getBasicRemote().sendText(notification.getMessage());
                notification.setIsRead(true);
                notificationDatabase.save(notification);
            } 
            catch (IOException e) {
                logger.info("Exception: " + e.getMessage().toString());
                e.printStackTrace();
            }
        }
	}


	private void broadcast(Notification notification) {
        notification.setUsername("");
        notificationDatabase.save(notification);
		sessionUsernameMap.forEach((session, username) -> {
			try {
                session.getBasicRemote().sendText(notification.getMessage());
			} 
            catch (IOException e) {
				logger.info("Exception: " + e.getMessage().toString());
				e.printStackTrace();
			}
		});

    }
    
    private void sendUnreadNotificationsToUser(String username){
        List<Notification> notifications = notificationDatabase.findByUsernameAndIsRead(username, false);
        for(Notification n: notifications){
            sendNotificationToUser(n);
        }
    }

}