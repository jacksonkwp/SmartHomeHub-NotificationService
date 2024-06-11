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
	
	@ManyToMany(fetch=FetchType.EAGER)
	@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
	@JsonIdentityReference(alwaysAsId=true)
	private List<Recipient> subscribers = new ArrayList<>();
	
	public String toString() {
		String str = "Stream(id="+id+", name="+name+", subscribers=[";
		if (subscribers.size() > 0) {
			for (int i = 0; i < subscribers.size()-1; i++) {
				str = str + subscribers.get(i).getId() + ", ";
			}
			str = str + subscribers.get(subscribers.size()-1).getId();
		}
		str = str + "])";
		
		return str;
	}
}
