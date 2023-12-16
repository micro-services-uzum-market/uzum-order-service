package org.example.uzumorderservice.notification;

import org.example.uzumorderservice.notification.dto.NotificationRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "uzum-notification-service")
public interface NotificationService {

    @PostMapping("/api/v1/notification")
    void sendSms(@RequestBody NotificationRequestDto requestDto);

}
