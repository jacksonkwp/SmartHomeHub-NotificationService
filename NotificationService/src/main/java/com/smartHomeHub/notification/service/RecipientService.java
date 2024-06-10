package com.smartHomeHub.notification.service;

import com.smartHomeHub.notification.model.Notification;
import com.smartHomeHub.notification.model.Stream;

public class RecipientService {
	
	public Notification[] getNotifications(int count) {
		//TODO
		return new Notification[0];
	}
	
	public void confirmDelivery(Notification[] notifications) {
		//TODO
	}
	
	public Stream[] getSubscriptions() {
		//TODO
		return new Stream[0];
	}
	
	public void subscribe(long streamId, long recipiantId) {
		//TODO
	}
	
	public void unsubscribe(long streamId, long recipiantId) {
		//TODO
	}
}
