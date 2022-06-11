package ru.itmo.nfomkin.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.itmo.nfomkin.enums.Color;


@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cats", schema = "kotiki2")
public class Cat {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private LocalDate birthDate;
  private String breed;
  @Enumerated(EnumType.STRING)
  private Color color;
  @ManyToOne
  @JoinColumn(name="owner_id")
  private Owner owner;
  @Builder.Default
  @ManyToMany
  @JoinTable(name = "friendship", schema = "kotiki",
  joinColumns = @JoinColumn(name = "cat1_id"),
  inverseJoinColumns = @JoinColumn(name = "cat2_id"))
  private List<Cat> friends = new ArrayList<>();

  @Override
  public String toString() {
    return "Cat{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", birthDate=" + birthDate +
        ", breed='" + breed + '\'' +
        ", color='" + color + '\'' +
        ", owner=" + owner.getId() + " " + owner.getName() + '\''+
        '}';
  }

  public void addFriend(Cat cat) {
    friends.add(cat);
    cat.friends.add(this);
  }
}
