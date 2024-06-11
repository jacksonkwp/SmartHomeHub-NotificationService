package com.smartHomeHub.notification.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smartHomeHub.notification.DTO.NotificationDTO;
import com.smartHomeHub.notification.DTO.StreamDTO;
import com.smartHomeHub.notification.model.Notification;
import com.smartHomeHub.notification.model.Recipient;
import com.smartHomeHub.notification.model.Stream;

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
	public List<NotificationDTO> getNotifications(long recipientId, int count) {
		
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
		for (int i = 0; i < Math.min(count, recipient.getUndeliveredNotifications().size()); i++) {
			Notification notification = recipient.getUndeliveredNotifications().remove(0);
			if (notification.getWaitingRecipientsCount() > 1) {
				notification.setWaitingRecipientsCount(notification.getWaitingRecipientsCount()-1);
			} else {
				entityManager.remove(notification);
			}
			notifications.add(new NotificationDTO(notification));
		}
		
		return notifications;
	}
	
	public List<StreamDTO> getSubscriptions(long recipientId) {
		
		//get the recipient
		Query query = entityManager.createQuery("SELECT r FROM Recipient r WHERE r.id=:recipientId");
		query.setParameter("recipientId", recipientId);
		List<?> result = query.getResultList();
		if (result.size() < 1) {
			return new ArrayList<StreamDTO>(); //TODO find a way to warn that id wasn't found (to prevent assumption that there arn't notifications)
		}
		Recipient recipient = (Recipient) result.get(0);
		
		//make DTO list of subscriptions
		List<StreamDTO> subscriptions = new ArrayList<StreamDTO>();
		for (Stream stream : recipient.getSubscriptions()) {
			subscriptions.add(new StreamDTO(stream));
		}
		
		return subscriptions;
	}
	
	@Transactional
	public String subscribe(long recipientId, long streamId) {
		
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
		
		//subscribe the recipient to the stream
		recipient.getSubscriptions().add(stream);
		stream.getSubscribers().add(recipient);
		
		return "Recipient " + recipient.toString() + " is now subscribed to " +
				stream.toString() + ".";
	}
	
	@Transactional
	public String unsubscribe(long recipientId, long streamId) {
		
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
		
		//unsubscribe the recipient
		recipient.getSubscriptions().remove(stream);
		stream.getSubscribers().remove(recipient);
		
		return "Recipient " + recipient.toString() + " is now unsubscribed from " +
				stream.toString() + ".";
	}
}
