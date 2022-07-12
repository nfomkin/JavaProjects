package ru.itmo.nfomkin.dto;

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
public class OwnerDto {
  @Null
  private Long id;
  @NotBlank(message = "Username can't be empty")
  private String username;
  @NotBlank(message = "Password can't be empty")
  private String password;
  private String name;
  private String birthDate;
}
