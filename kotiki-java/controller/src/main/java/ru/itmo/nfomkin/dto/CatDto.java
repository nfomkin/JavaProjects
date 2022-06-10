package ru.itmo.nfomkin.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CatDto {
  private Long id;
  private String name;
  private String breed;
  private String color;
  private String birthDate;
  private Long ownerId;
  private String ownerName;
  @Builder.Default
  private List<String> friends = new ArrayList<>();
}
