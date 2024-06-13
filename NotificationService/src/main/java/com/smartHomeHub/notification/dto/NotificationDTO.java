package com.smartHomeHub.notification.dto;

import java.util.Date;

import com.smartHomeHub.notification.model.Notification;

import lombok.Data;

@Data
public class NotificationDTO {
	
	private long id;
	private String message;
	private Date timestamp;
	private StreamDTO source;
	
	public NotificationDTO(Notification notification) {
		this.id = notification.getId();
		this.message = notification.getMessage();
		this.timestamp = notification.getTimestamp();
		this.source = new StreamDTO(notification.getSource());
	}
}
