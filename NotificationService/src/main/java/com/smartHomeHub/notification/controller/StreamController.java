package com.smartHomeHub.notification.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="v1/stream")
public class StreamController {
	
	@PostMapping
	public ResponseEntity<String> createStream(
			@RequestParam(name="name") String name) {
		return ResponseEntity.ok(""); //TODO create stream and return success message (include id)
	}
	
	@DeleteMapping
	public ResponseEntity<String> deleteStream(
			@RequestParam(name="id") long streamId) {
		return ResponseEntity.ok(""); //TODO delete stream and return success message
	}
	
	@PatchMapping
	public ResponseEntity<String> updateStream(
			@RequestParam(name="id") long streamId,
			@RequestParam(name="name") String name) {
		return ResponseEntity.ok(""); //TODO update stream and return success message
	}
	
	@PostMapping("/{streamId}")
	public ResponseEntity<String> getPerson(
			@PathVariable("streamId") long streamId) {
		return ResponseEntity.ok("TODO"); //TODO
	}
}
