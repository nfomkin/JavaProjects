package entity;


import java.time.*;
import java.util.*;
import java.util.stream.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Cat {

  private Long id;
  private String name;
  private LocalDate birthDate;
  private String breed;
  private String color;
  private Owner owner;
  @Builder.Default
  private List<Long> friends = new ArrayList<>();

  @Override
  public String toString() {
    return "Cat{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", birthDate=" + birthDate +
        ", breed='" + breed + '\'' +
        ", color='" + color + '\'' +
        ", owner=" + owner +
        '}';
  }
}
