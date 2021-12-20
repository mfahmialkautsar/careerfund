package id.careerfund.api.services;

import id.careerfund.api.domains.entities.Notification;
import id.careerfund.api.domains.entities.User;
import id.careerfund.api.domains.models.NotificationMyClassStatus;

import java.security.Principal;
import java.util.List;

public interface NotificationService {
    List<Notification> getNotifications(Principal principal);
    void sendMyClassNotification(NotificationMyClassStatus myClassStatus, Long id, User user);
}
