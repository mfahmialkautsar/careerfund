package id.careerfund.api.services;

import id.careerfund.api.domains.entities.Notification;
import id.careerfund.api.domains.entities.User;
import id.careerfund.api.domains.models.NotificationMyClassStatus;
import id.careerfund.api.domains.models.NotificationTopic;
import id.careerfund.api.repositories.NotificationRepository;
import id.careerfund.api.utils.mappers.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository notificationRepo;

    @Override
    public List<Notification> getNotifications(Principal principal) {
        User user = UserMapper.principalToUser(principal);
        return notificationRepo.findByUser_Id(user.getId());
    }

    @Override
    public void sendMyClassNotification(NotificationMyClassStatus myClassStatus, Long id, User user) {
        Notification notification = new Notification();
        notification.setTopic(NotificationTopic.MY_CLASS.name());
        notification.setEntityStatus(myClassStatus.name());
        notification.setEntityId(id);
        notification.setUser(user);
        notificationRepo.save(notification);
    }
}
