package ru.itmo.nfomkin.entity;


import java.time.*;
import java.util.*;
import javax.persistence.*;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "owners", schema = "kotiki")
public class Owner {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  private LocalDate birthDate;
  @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  List<Cat> cats;

  @Override
  public String toString() {
    return "Owner{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", birthDate=" + birthDate +
        '}';
  }

  public void addCat(Cat cat) {
    cats.add(cat);
    cat.setOwner(this);
  }
}
