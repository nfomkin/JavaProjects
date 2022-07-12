package ru.itmo.nfomkin;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
public class RegistrationForm {
  @NotNull
  @Size(min = 2, max = 30)
  private String username;
  @NotNull(message = "please, enter the password")
  private String password;
  @NotNull(message = "please, enter the name")
  @Size(min = 2, max = 30)
  private String name;
  @NotNull(message = "please, enter the birthdate")
  private String birthDate;

//  public Owner toOwner(PasswordEncoder encoder) {
//
//    return Owner.builder()
//        .name(name)
//        .username(username)
//        .password(encoder.encode(password))
//        .birthDate(LocalDate.parse(birthDate))
//        .roles(Set.of(Role.USER))
//        .build();
//  }
}
