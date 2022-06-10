package ru.itmo.nfomkin.mapper;

import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.itmo.nfomkin.dto.CatDto;
import ru.itmo.nfomkin.entity.Cat;
import ru.itmo.nfomkin.entity.Owner;
import ru.itmo.nfomkin.enums.Color;
import ru.itmo.nfomkin.service.CatService;
import ru.itmo.nfomkin.service.OwnerService;

@Component
public class CatMapper {
  @Autowired
  private OwnerService ownerService;
  @Autowired
  private CatService catService;

  public CatDto toDto(Cat cat) {
    List<String> friends = cat.getFriends().stream().map(Cat::getName).toList();
    return CatDto.builder()
        .id(cat.getId())
        .name(cat.getName())
        .birthDate(cat.getBirthDate().toString())
        .color(cat.getColor().toString())
        .breed(cat.getBreed())
        .ownerId(cat.getOwner().getId())
        .ownerName(cat.getOwner().getName())
        .friends(friends)
        .build();
  }

  public Cat toCat(CatDto catDto) {
    Owner owner = catDto.getOwnerId() != null ? ownerService.findById(catDto.getOwnerId()).orElse(null)
        : ownerService.findByName(catDto.getOwnerName()).stream().findFirst().orElse(null);
    List<Cat> friends = catDto.getFriends().stream().map(name -> catService.findByName(name).get(0)).toList();
    return Cat.builder()
        .id(catDto.getId())
        .name(catDto.getName())
        .breed(catDto.getBreed())
        .color(Color.valueOf(catDto.getColor()))
        .birthDate(LocalDate.parse(catDto.getBirthDate()))
        .owner(owner)
        .friends(friends)
        .build();
  }

}
