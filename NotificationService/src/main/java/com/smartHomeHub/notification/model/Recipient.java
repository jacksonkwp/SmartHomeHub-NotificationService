package com.smartHomeHub.notification.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class Recipient {
	
	@Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    private long id;
	
	@ManyToMany(fetch=FetchType.EAGER)
	@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
	@JsonIdentityReference(alwaysAsId=true)
	private List<Notification> undeliveredNotifications = new ArrayList<>();
	
	@ManyToMany(fetch=FetchType.EAGER, mappedBy="subscribers")
	@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
	@JsonIdentityReference(alwaysAsId=true)
	private List<Stream> subscriptions = new ArrayList<>();
	
	public String toString() {
		String str = "Recipient(id="+id+", undeliveredNotificationsCount="+
				undeliveredNotifications.size()+", subscriptions=[";
		if (subscriptions.size() > 0) {
			for (int i = 0; i < subscriptions.size()-1; i++) {
				str = str + subscriptions.get(i).getName() + ", ";
			}
			str = str + subscriptions.get(subscriptions.size()-1).getName();
		}
		str = str + "])";
		
		return str;
	}
}
