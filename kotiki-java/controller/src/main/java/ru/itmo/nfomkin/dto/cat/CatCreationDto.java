package ru.itmo.nfomkin.dto.cat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CatCreationDto {
  private Long id;
  private String name;
  private String birthDate;
  private String breed;
  private String color;
  private Long ownerId;
}
