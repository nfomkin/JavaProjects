package entity;

import java.time.*;
import java.util.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Owner {

  private Long id;
  private String name;
  private LocalDate birthDate;
  @Builder.Default
  private List<Cat> cats = new ArrayList<>();

  @Override
  public String toString() {
    return "Owner{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", birthDate=" + birthDate +
        '}';
  }
}
