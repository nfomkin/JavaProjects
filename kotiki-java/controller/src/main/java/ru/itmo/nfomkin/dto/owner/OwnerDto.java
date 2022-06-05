package ru.itmo.nfomkin.dto.owner;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OwnerDto {
  private String name;
  private String birthDate;
  private List<String> cats;
}
