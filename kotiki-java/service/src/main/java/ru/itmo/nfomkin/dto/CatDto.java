package ru.itmo.nfomkin.dto;

import java.util.HashMap;
import java.util.Map;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
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
  @Null
  private Long id;
  @NotBlank
  private String name;
  private String breed;
  private String color;
  private String birthDate;
  private Long ownerId;
  private String ownerName;
  @Builder.Default
  private Map<Long, String> friends = new HashMap<>();
}
