package ru.itmo.nfomkin.mapper.owner;

import java.time.LocalDate;
import java.util.List;
import ru.itmo.nfomkin.dto.owner.OwnerCreationDto;
import ru.itmo.nfomkin.dto.owner.OwnerDto;
import ru.itmo.nfomkin.entity.Cat;
import ru.itmo.nfomkin.entity.Owner;

public class OwnerMapper {
  public OwnerDto toDto(Owner owner) {
    List<String> cats = owner.getCats().stream()
        .map(Cat::getName)
        .toList();
    return new OwnerDto(owner.getName(), owner.getBirthDate().toString(), cats);
  }

  public Owner toOwner(OwnerCreationDto ownerCreationDto) {
    return Owner.builder()
        .name(ownerCreationDto.getName())
        .birthDate(LocalDate.parse(ownerCreationDto.getBirthDate()))
        .build();
  }

}
