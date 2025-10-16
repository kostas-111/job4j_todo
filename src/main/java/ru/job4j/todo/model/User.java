package ru.job4j.todo.model;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private int id;

	@Column(nullable = false)
	private String name;

	@EqualsAndHashCode.Include
	@Column(nullable = false, unique = true)
	private String login;

	@Column(nullable = false)
	private String password;
}
