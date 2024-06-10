package com.smartHomeHub.notification.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.smartHomeHub.notification.DTO.NotificationDTO;
import com.smartHomeHub.notification.DTO.StreamDTO;

@RestController
@RequestMapping(value="v1/recipient")
public class RecipientController {
	
	@PostMapping
	public ResponseEntity<String> createRecipient() {
		return ResponseEntity.ok(""); //TODO create recipient and return success message (include id)
	}
	
	@DeleteMapping
	public ResponseEntity<String> deleteRecipient(
			@RequestParam(name="id") long recipientId) {
		return ResponseEntity.ok(""); //TODO delete recipient and return success message
	}
	
	@GetMapping("/{recipientId}")
	public ResponseEntity<List<NotificationDTO>> getNotifications(
			@PathVariable("recipientId") long recipientId,
			@RequestParam(name="count", defaultValue="1") int count) {
		return ResponseEntity.ok(new ArrayList<NotificationDTO>()); //TODO return count notifications for the recipient
	}
	
	@GetMapping("/{recipientId}/subscriptions")
	public ResponseEntity<List<StreamDTO>> getSubscriptions(
			@PathVariable("recipientId") long recipientId) {
		return ResponseEntity.ok(new ArrayList<StreamDTO>()); //TODO return the streams the recipient is subscribed to
	}
	
	@GetMapping("/{recipientId}/streams")
	public ResponseEntity<List<StreamDTO>> getStreams() {
		return ResponseEntity.ok(new ArrayList<StreamDTO>()); //TODO return all streams
	}
	
	@PostMapping("/{recipientId}/subscribe")
	public ResponseEntity<String> subscribe(
			@PathVariable("recipientId") long recipientId,
			@RequestParam(name="stream") long streamId) {
		return ResponseEntity.ok(""); //TODO handle subscription and return success message
	}
	
	@PostMapping("/{recipientId}/unsubscribe")
	public ResponseEntity<String> unsubscribe(
			@PathVariable("recipientId") long recipientId,
			@RequestParam(name="stream") long streamId) {
		return ResponseEntity.ok(""); //TODO handle subscription removal and return success message
	}
}
