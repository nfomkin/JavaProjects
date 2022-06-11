package ru.itmo.nfomkin.mapper;

import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.itmo.nfomkin.dto.OwnerDto;
import ru.itmo.nfomkin.entity.Cat;
import ru.itmo.nfomkin.entity.Owner;
import ru.itmo.nfomkin.service.CatService;
import ru.itmo.nfomkin.service.OwnerService;

@Component
public class OwnerMapper {

  @Autowired
  private OwnerService ownerService;
  @Autowired
  private CatService catService;

  public Owner toOwner(OwnerDto ownerDto) {
    return Owner.builder()
        .name(ownerDto.getName())
        .birthDate(LocalDate.parse(ownerDto.getBirthDate()))
        .cats(
            ownerDto.getCats().stream().map(id -> catService.findById(id).orElse(null)).toList()
        )
        .build();
  }

  public OwnerDto toDto(Owner owner) {
    return OwnerDto.builder()
        .id(owner.getId())
        .name(owner.getName())
        .birthDate(owner.getBirthDate().toString())
        .cats(
            owner.getCats().stream().map(Cat::getId).toList()
        )
        .build();
  }

}
