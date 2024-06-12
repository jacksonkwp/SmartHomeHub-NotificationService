package com.smartHomeHub.notification.DTO;

import com.smartHomeHub.notification.model.Subscription;

import lombok.Data;

@Data
public class SubscriptionDTO {
	
	private long id;
	private Subscription.Urgency urgency;
	private long recipient;
	private StreamDTO stream;
	
	public SubscriptionDTO(Subscription subscription) {
		this.id = subscription.getId();
		this.urgency = subscription.getUrgency();
		this.recipient = subscription.getRecipient().getId();
		this.stream = new StreamDTO(subscription.getStream());
	}
	
}
