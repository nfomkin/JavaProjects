package ru.itmo.nfomkin.mapper.cat;

import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.itmo.nfomkin.dto.cat.CatCreationDto;
import ru.itmo.nfomkin.dto.cat.CatDto;
import ru.itmo.nfomkin.entity.Cat;
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
    List<String> friends = cat.getFriends().stream()
        .map(Cat::getName).toList();
    CatDto catDto = new CatDto(cat.getId(), cat.getName(), cat.getBirthDate().toString(), cat.getBreed(),
        cat.getColor().toString(), cat.getOwner().getName(), friends);
    return catDto;
  }

  public CatCreationDto toCreationDto(Cat cat) {
    return new CatCreationDto(cat.getId(), cat.getName(), cat.getBirthDate().toString(), cat.getBreed(),
        cat.getColor().toString(), cat.getOwner().getId());
  }
  public Cat toCat(CatCreationDto cat) {
    Cat returnCat = Cat.builder()
        .id(cat.getId())
        .name(cat.getName())
        .birthDate(LocalDate.parse(cat.getBirthDate()))
        .breed(cat.getBreed())
        .color(Color.valueOf(cat.getColor()))
        .owner(
            ownerService.findById(cat.getOwnerId()).orElse(null))
        .build();
    return returnCat;
  }


}
