package entities;

import enums.*;
import jakarta.persistence.*;
import java.time.*;
import java.util.*;
import java.util.stream.*;
import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cats", schema = "kotiki")
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
  private List<Cat> friends = new ArrayList<Cat>();

  @Override
  public String toString() {
    return "Cat{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", birthDate=" + birthDate +
        ", breed='" + breed + '\'' +
        ", color='" + color + '\'' +
        ", owner=" + owner.getId() + " " + owner.getName() + '\'' +
        ", friends=" + friends.stream().map(f -> f.id + f.name).collect(Collectors.joining("||")) +
        '}';
  }

  public void addFriend(Cat cat) {
    friends.add(cat);
  }
}
