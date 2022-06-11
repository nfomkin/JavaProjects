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
public class OwnerDto {
  private Long id;
  private String name;
  private String birthDate;
  @Builder.Default
  private List<Long> cats = new ArrayList<>();
}
