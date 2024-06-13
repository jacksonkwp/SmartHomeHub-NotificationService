package com.smartHomeHub.notification.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smartHomeHub.notification.dto.NotificationDTO;
import com.smartHomeHub.notification.dto.SubscriptionDTO;
import com.smartHomeHub.notification.model.Notification;
import com.smartHomeHub.notification.model.Recipient;
import com.smartHomeHub.notification.model.Stream;
import com.smartHomeHub.notification.model.Subscription;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

@Service
public class RecipientService {
	
	@Autowired
	EntityManager entityManager;
	
	@Transactional
	public String createRecipient(){
		Recipient recipient = new Recipient();
		entityManager.persist(recipient);
		return "Recipient " + recipient.toString() + " was created.";
	}
	
	@Transactional
	public String deleteRecipient(long recipientId){
		Query query = entityManager.createQuery("SELECT r FROM Recipient r WHERE r.id=:recipientId");
		query.setParameter("recipientId", recipientId);
		List<?> result = query.getResultList();
		if (result.size() < 1) {
			return "Unable to locate recipient with id 1";
		}
		Recipient recipient = (Recipient) result.get(0);
		entityManager.remove(recipient);
		
		return "Recipient " + recipient.toString() + " was deleted.";
	}
	
	@Transactional
	public List<NotificationDTO> getNotifications(long recipientId, Subscription.Urgency urgency, int count) {
		
		//get the recipient
		Query query = entityManager.createQuery("SELECT r FROM Recipient r WHERE r.id=:recipientId");
		query.setParameter("recipientId", recipientId);
		List<?> result = query.getResultList();
		if (result.size() < 1) {
			return new ArrayList<NotificationDTO>(); //TODO find a way to warn that id wasn't found (to prevent assumption that there arn't notifications)
		}
		Recipient recipient = (Recipient) result.get(0);
		
		//make DTO list of undelivered notifications and remove them from undeliveredNotifications
		List<NotificationDTO> notifications = new ArrayList<NotificationDTO>();
		Iterator<Notification> it = recipient.getUndeliveredNotifications().iterator();
		while ((notifications.size() < count) && it.hasNext()) {
			Notification notification = it.next();
			
			//get the urgency of notification
			Subscription.Urgency noteUrgency = null;
			for (Subscription sub : recipient.getSubscriptions()) {
				if (sub.getStream().getId() == notification.getSource().getId()) {
					noteUrgency = sub.getUrgency();
					break;
				}
			}
			
			//move notification to the DTO list if it is urgent enough
			if (noteUrgency.value >= urgency.value) {
				if (notification.getWaitingRecipientsCount() > 1) {
					notification.setWaitingRecipientsCount(notification.getWaitingRecipientsCount()-1);
				} else {
					entityManager.remove(notification);
				}
				notifications.add(new NotificationDTO(notification));
				it.remove();
			}
		}
		
		return notifications;
	}
	
	public List<SubscriptionDTO> getSubscriptions(long recipientId) {
		
		//get the recipient
		Query query = entityManager.createQuery("SELECT r FROM Recipient r WHERE r.id=:recipientId");
		query.setParameter("recipientId", recipientId);
		List<?> result = query.getResultList();
		if (result.size() < 1) {
			return new ArrayList<SubscriptionDTO>(); //TODO find a way to warn that id wasn't found (to prevent assumption that there arn't notifications)
		}
		Recipient recipient = (Recipient) result.get(0);
		
		//make DTO list of subscriptions
		List<SubscriptionDTO> subscriptions = new ArrayList<SubscriptionDTO>();
		for (Subscription subscription : recipient.getSubscriptions()) {
			subscriptions.add(new SubscriptionDTO(subscription));
		}
		
		return subscriptions;
	}
	
	@Transactional
	public String subscribe(long recipientId, long streamId, Subscription.Urgency urgency) {
		
		//get the recipient
		Query query = entityManager.createQuery("SELECT r FROM Recipient r WHERE r.id=:recipientId");
		query.setParameter("recipientId", recipientId);
		List<?> result = query.getResultList();
		if (result.size() < 1) {
			return "Unable to locate recipiant with id " + recipientId;
		}
		Recipient recipient = (Recipient) result.get(0);
		
		//get the stream
		query = entityManager.createQuery("SELECT s FROM Stream s WHERE s.id=:streamId");
		query.setParameter("streamId", streamId);
		result = query.getResultList();
		if (result.size() < 1) {
			return "Unable to locate stream with id " + streamId;
		}
		Stream stream = (Stream) result.get(0);
		
		//return early if already subscribed
		for (Subscription subscription : recipient.getSubscriptions()) {
			if (subscription.getStream() == stream) {
				return "Recipient " + recipient.toString() + " is already subscribed to" +
						stream.toString() + ".";
			}
		}
		
		//create a subscription for the recipient and stream
		Subscription subscription = new Subscription();
		subscription.setUrgency(urgency);
		subscription.setRecipient(recipient);
		subscription.setStream(stream);
		recipient.getSubscriptions().add(subscription);
		stream.getSubscriptions().add(subscription);
		entityManager.persist(subscription);
		
		return "Recipient " + recipient.toString() + " is now subscribed to " +
				stream.toString() + " with urgency " + subscription.getUrgency() + ".";
	}
	
	@Transactional
	public String unsubscribe(long recipientId, long streamId) {
		
		//get the subscription
		Query query = entityManager.createQuery("SELECT s FROM Subscription s WHERE " +
				"s.recipient.id=:recipientId AND s.stream.id=:streamId");
		query.setParameter("recipientId", recipientId);
		query.setParameter("streamId", streamId);
		List<?> result = query.getResultList();
		if (result.size() < 1) {
			return "No subscription exists between recipient with id " + recipientId +
					" and stream with id " + streamId + ".";
		}
		Subscription subscription = (Subscription) result.get(0);
		
		//remove the subscription
		subscription.getRecipient().getSubscriptions().remove(subscription);
		subscription.getStream().getSubscriptions().remove(subscription);
		entityManager.remove(subscription);
		
		return "Recipient " + subscription.getRecipient().toString() +
				" is now unsubscribed from " + subscription.getStream().toString() + ".";
	}
}
