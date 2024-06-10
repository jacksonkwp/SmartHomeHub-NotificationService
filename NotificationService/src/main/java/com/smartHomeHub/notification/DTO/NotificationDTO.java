package com.smartHomeHub.notification.DTO;

import java.util.Date;

import com.smartHomeHub.notification.model.Notification;

import lombok.Data;

@Data
public class NotificationDTO {
	
	private long id;
	private String message;
	private Date timestamp;
	
	public NotificationDTO(Notification notification) {
		this.id = notification.getId();
		this.message = notification.getMessage();
		this.timestamp = notification.getTimestamp();
	}
}
