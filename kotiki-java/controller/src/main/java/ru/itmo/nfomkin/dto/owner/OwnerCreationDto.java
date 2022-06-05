package ru.itmo.nfomkin.dto.owner;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OwnerCreationDto {
  private String name;
  private String birthDate;
}
