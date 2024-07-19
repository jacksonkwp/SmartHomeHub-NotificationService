package com.smartHomeHub.notification.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.smartHomeHub.notification.dto.NotificationDTO;
import com.smartHomeHub.notification.dto.SubscriptionDTO;
import com.smartHomeHub.notification.model.Subscription;
import com.smartHomeHub.notification.service.RecipientService;

import jakarta.annotation.security.RolesAllowed;

@RestController
@RequestMapping(value="v1/recipient")
public class RecipientController {
	
	@Autowired
	RecipientService recipientService;
	
	@PostMapping
    @RolesAllowed({"ADMIN"})
	public ResponseEntity<String> createRecipient() {
		return ResponseEntity.ok(recipientService.createRecipient());
	}
	
	@DeleteMapping
    @RolesAllowed({"ADMIN"})
	public ResponseEntity<String> deleteRecipient(
			@RequestParam(name="id") long recipientId) {
		return ResponseEntity.ok(recipientService.deleteRecipient(recipientId));
	}
	
	@GetMapping("/{recipientId}")
    @RolesAllowed({"USER", "ADMIN"})
	public ResponseEntity<List<NotificationDTO>> getNotifications(
			@PathVariable("recipientId") long recipientId,
			@RequestParam(name="urgency", defaultValue="LOW") Subscription.Urgency urgency,
			@RequestParam(name="count", defaultValue="1") int count) {
		return ResponseEntity.ok(recipientService.getNotifications(recipientId, urgency, count));
	}
	
	@GetMapping("/{recipientId}/subscriptions")
    @RolesAllowed({"USER", "ADMIN"})
	public ResponseEntity<List<SubscriptionDTO>> getSubscriptions(
			@PathVariable("recipientId") long recipientId) {
		return ResponseEntity.ok(recipientService.getSubscriptions(recipientId));
	}
	
	@PostMapping("/{recipientId}/subscribe")
    @RolesAllowed({"USER", "ADMIN"})
	public ResponseEntity<String> subscribe(
			@PathVariable("recipientId") long recipientId,
			@RequestParam(name="stream") long streamId,
			@RequestParam(name="urgency") Subscription.Urgency urgency) {
		return ResponseEntity.ok(recipientService.subscribe(recipientId, streamId, urgency));
	}
	
	@PostMapping("/{recipientId}/unsubscribe")
    @RolesAllowed({"USER", "ADMIN"})
	public ResponseEntity<String> unsubscribe(
			@PathVariable("recipientId") long recipientId,
			@RequestParam(name="stream") long streamId) {
		return ResponseEntity.ok(recipientService.unsubscribe(recipientId, streamId));
	}
}
