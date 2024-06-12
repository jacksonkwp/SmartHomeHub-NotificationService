package com.smartHomeHub.notification.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@Entity
@NoArgsConstructor
public class Stream {
	
	@Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    private long id;
	
	@NonNull
	private String name;
	
	@OneToMany(fetch=FetchType.EAGER, mappedBy="stream")
	@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
	@JsonIdentityReference(alwaysAsId=true)
	private List<Subscription> subscriptions = new ArrayList<>();
	
	public String toString() {
		String str = "Stream(id="+id+", name="+name+", subscribers=[";
		if (subscriptions.size() > 0) {
			Iterator<Subscription> it = subscriptions.iterator();
			Subscription sub;
			for (int i = 0; i < subscriptions.size()-1; i++) {
				sub = it.next();
				str = str + "(" + sub.getRecipient().getId() + ", " + sub.getUrgency() + "), ";
			}
			sub = it.next();
			str = str + "(" + sub.getRecipient().getId() + ", " + sub.getUrgency() + ")";
		}
		str = str + "])";
		
		return str;
	}
}
