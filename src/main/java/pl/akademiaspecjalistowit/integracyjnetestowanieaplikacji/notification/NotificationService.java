package pl.akademiaspecjalistowit.integracyjnetestowanieaplikacji.notification;

import org.springframework.stereotype.Service;
import pl.akademiaspecjalistowit.integracyjnetestowanieaplikacji.Observer;
import pl.akademiaspecjalistowit.integracyjnetestowanieaplikacji.EventCreatingBook;

@Service
public class NotificationService implements Observer {
    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public void accept(EventCreatingBook eventCreatingBook) {
        notificationRepository.save(new NotificationEntity(eventCreatingBook.getClient(), eventCreatingBook.getId()));
    }
}
