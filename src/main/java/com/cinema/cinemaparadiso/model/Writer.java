package com.cinema.cinemaparadiso.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "writers")
@Getter
@Setter
public class Writer extends Person {
	
	@ElementCollection(targetClass = Story.class)
	@Column(name = "stories")
	private List<Story> stories;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "username", referencedColumnName = "username")
	private User user;
  

}
