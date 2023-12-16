package org.example.uzumorderservice.notification.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class NotificationRequestDto {
    private String phoneNumber;
    private String message;
    private NotificationType notificationType;

}
