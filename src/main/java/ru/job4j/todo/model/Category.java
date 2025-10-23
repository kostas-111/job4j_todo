package ru.job4j.todo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "categories")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
public class Category {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @EqualsAndHashCode.Include
  private Integer id;

  @Column(nullable = false, unique = true)
  private String name;
}
