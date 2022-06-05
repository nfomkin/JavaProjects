package ru.itmo.nfomkin.dto.cat;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CatDto {
  private Long id;
  private String name;
  private String birthDate;
  private String breed;
  private String color;
  private String ownerName;
  private List<String> friends;
}
