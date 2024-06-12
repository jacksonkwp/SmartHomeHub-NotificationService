package com.smartHomeHub.notification.model;

import jakarta.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@Entity
@NoArgsConstructor
public class Subscription {
	
	@Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    private long id;
	
	@NonNull
	private Urgency urgency;
	
	@ManyToOne
	@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
	@JsonIdentityReference(alwaysAsId=true)
	private Recipient recipient;
	
	@ManyToOne
	@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
	@JsonIdentityReference(alwaysAsId=true)
	private Stream stream;
	
	public enum Urgency {
		HIGH(2),
		MEDIUM(1),
		LOW(0);
		
		public final int value;
		
		private Urgency(int value) {
			this.value = value;
		}
	}
}
