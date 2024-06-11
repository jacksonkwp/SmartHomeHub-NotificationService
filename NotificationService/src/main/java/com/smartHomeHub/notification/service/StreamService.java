package com.smartHomeHub.notification.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smartHomeHub.notification.DTO.StreamDTO;
import com.smartHomeHub.notification.model.Notification;
import com.smartHomeHub.notification.model.Recipient;
import com.smartHomeHub.notification.model.Stream;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

@Service
public class StreamService {
	
	@Autowired
	private EntityManager entityManager;
	
	public List<StreamDTO> getStreams() {
		
		//get all streams
		Query query = entityManager.createQuery("SELECT s FROM Stream s");
		List<?> results = query.getResultList();
		
		//convert List<?> (List<Stream>) to List<StreamDTO>
		List<StreamDTO> streams = new ArrayList<StreamDTO>();
		for (Object stream : results) {
			streams.add(new StreamDTO((Stream) stream));
		}
		
		return streams;
	}
	
	@Transactional
	public String createStream(String name){
		
		//create stream with name name
    	Stream stream = new Stream();
    	stream.setName(name);
		entityManager.persist(stream);

		return "Stream " + stream.toString() + " was created.";
	}
	
	@Transactional
	public String deleteStream(long streamId){
		Query query = entityManager.createQuery("SELECT s FROM Stream s WHERE s.id=:streamId");
		query.setParameter("streamId", streamId);
		List<?> result = query.getResultList();
		if (result.size() < 1) {
			return "Unable to locate stream with id " + streamId;
		}
		Stream stream = (Stream) result.get(0);
		entityManager.remove(stream);
		
		return "Stream " + stream.toString() + " was deleted.";
	}
	
	@Transactional
	public String updateStream(long streamId, String name){
		
		Query query = entityManager.createQuery("SELECT s FROM Stream s WHERE s.id=:streamId");
		query.setParameter("streamId", streamId);
		List<?> result = query.getResultList();
		if (result.size() < 1) {
			return "Unable to locate stream with id " + streamId;
		}
		Stream stream = (Stream) result.get(0);
		stream.setName(name);
		
		return "Stream " + stream.toString() + " was updated.";
	}
	
	@Transactional
	public String addNotification(long streamId, String message) {
		
		//get stream
		Query query = entityManager.createQuery("SELECT s FROM Stream s WHERE s.id=:streamId");
		query.setParameter("streamId", streamId);
		List<?> result = query.getResultList();
		if (result.size() < 1) {
			return "Unable to locate stream with id " + streamId;
		}
		Stream stream = (Stream) result.get(0);
		
		//return early if stream has no subscribers
		if (stream.getSubscribers().size() == 0) {
			return "No notification was created, because stream " + stream.toString() +
					"does not have any subscribers.";
		}
		
		//make a notification
		Notification notification = new Notification();
		notification.setMessage(message);
		notification.setWaitingRecipientsCount(stream.getSubscribers().size());
		entityManager.persist(notification);
		
		//add the notification to each subscribers undeliveredNotifications list
		for (Recipient subscriber : stream.getSubscribers()) {
			subscriber.getUndeliveredNotifications().add(notification);
		}
		
		return "Notification " + notification.toString() + " was created in stream " +
				stream.toString() + ".";
	}
}
