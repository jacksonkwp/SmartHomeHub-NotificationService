package com.smartHomeHub.notification.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.smartHomeHub.notification.dto.StreamDTO;
import com.smartHomeHub.notification.service.StreamService;

import jakarta.annotation.security.RolesAllowed;

@RestController
@RequestMapping(value="v1/stream")
public class StreamController {
	
	@Autowired
	StreamService streamService;
	
	@GetMapping
    @RolesAllowed({"USER", "ADMIN"})
	public ResponseEntity<List<StreamDTO>> getStreams() {
		return ResponseEntity.ok(streamService.getStreams());
	}
	
	@PostMapping
    @RolesAllowed({"ADMIN"})
	public ResponseEntity<String> createStream(
			@RequestParam(name="name") String name) {
		return ResponseEntity.ok(streamService.createStream(name));
	}
	
	@DeleteMapping
    @RolesAllowed({"ADMIN"})
	public ResponseEntity<String> deleteStream(
			@RequestParam(name="id") long streamId) {
		return ResponseEntity.ok(streamService.deleteStream(streamId));
	}
	
	@PatchMapping
    @RolesAllowed({"ADMIN"})
	public ResponseEntity<String> updateStream(
			@RequestParam(name="id") long streamId,
			@RequestParam(name="name") String name) {
		return ResponseEntity.ok(streamService.updateStream(streamId, name));
	}
	
	@PostMapping("/{streamId}")
    @RolesAllowed({"USER", "ADMIN"})
	public ResponseEntity<String> addNotification(
			@PathVariable("streamId") long streamId,
			@RequestBody String message) {
		return ResponseEntity.ok(streamService.addNotification(streamId, message));
	}
}
