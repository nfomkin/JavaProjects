package ru.itmo.nfomkin.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import ru.itmo.nfomkin.domain.Cat;
import ru.itmo.nfomkin.domain.Color;
import ru.itmo.nfomkin.domain.Owner;
import ru.itmo.nfomkin.dto.CatDto;
import ru.itmo.nfomkin.exception.NoEntityException;
import ru.itmo.nfomkin.repository.CatRepository;

@Service
public class CatServiceImpl implements CatService {

  private CatRepository catRepository;
  private OwnerService ownerService;


  public CatServiceImpl(CatRepository catRepository, OwnerService ownerService) {
    this.catRepository = catRepository;
    this.ownerService = ownerService;
  }

  public List<Cat> findAll() {
    return catRepository.findAll();
  }

  public List<Cat> findAllByOwner(String username) throws NoEntityException {
    Optional<Owner> maybeOwner = ownerService.findByUsername(username);
    if (maybeOwner.isEmpty()) {
      throw new NoEntityException(String.format("Owner with username = \"%s\" doesn't exist", username));
    }
    return catRepository.findAllByOwner(maybeOwner.get());
  }

  public Optional<Cat> findByOwnerAndId(String username, Long id) throws NoEntityException {
    Optional<Owner> maybeOwner = ownerService.findByUsername(username);
    if (maybeOwner.isEmpty()) {
      throw new NoEntityException(String.format("Owner with username = \"%s\" doesn't exist", username));
    }

    return catRepository.findByOwnerAndId(maybeOwner.get(), id);
  }

  public List<Cat> findByName(String name) {
    return catRepository.findByName(name);
  }

  public Optional<Cat> findById(Long id) {
    return catRepository.findById(id);
  }

  public void saveOrUpdate(Cat cat) throws NoEntityException {
    catRepository.save(cat);
  }

  public void delete(Long id) throws NoEntityException {
    Optional<Cat> maybeCat = findById(id);
    if (maybeCat.isEmpty()) {
      throw new NoEntityException(String.format("Cat with id %s doesn't exist", id));
    }
    catRepository.delete(maybeCat.get());
  }

  public void addToOwner(String username, Long catId) throws NoEntityException {
    Optional<Owner> maybeOwner = ownerService.findByUsername(username);
    Optional<Cat> maybeCat = catRepository.findById(catId);
    if (maybeOwner.isEmpty()) {
      throw new NoEntityException(String.format("User %s not found", username));
    }
    if (maybeCat.isEmpty()){
      throw new NoEntityException(String.format("Cat with id %s not found", catId));
    }

    Owner owner = maybeOwner.get();
    owner.addCat(maybeCat.get());
    ownerService.saveOrUpdate(owner);
  }

  public void addFriendship(Long catId1, Long catId2) throws NoEntityException {
    Optional<Cat> maybeCat1 = catRepository.findById(catId1);
    Optional<Cat> maybeCat2 = catRepository.findById(catId2);

    if (maybeCat1.isEmpty()) {
      throw new NoEntityException(String.format("Cat with id %s not found", catId1));
    }

    if (maybeCat2.isEmpty()) {
      throw new NoEntityException(String.format("Cat with id %s not found", catId2));
    }

    Cat cat1 = maybeCat1.get();
    Cat cat2 = maybeCat2.get();
    if (!cat1.getFriends().contains(cat2)) {
      cat1.addFriend(cat2);
      this.saveOrUpdate(cat1);
      this.saveOrUpdate(cat2);
    }

  }

  public void deleteFriendship(Long catId1, Long catId2) throws NoEntityException {
    Optional<Cat> maybeCat1 = catRepository.findById(catId1);
    Optional<Cat> maybeCat2 = catRepository.findById(catId2);

    if (maybeCat1.isEmpty()) {
      throw new NoEntityException(String.format("Cat with id %s not found", catId1));
    }

    if (maybeCat2.isEmpty()) {
      throw new NoEntityException(String.format("Cat with id %s not found", catId2));
    }

    Cat cat1 = maybeCat1.get();
    Cat cat2 = maybeCat2.get();
    if (cat1.getFriends().contains(cat2)) {
      cat1.deleteFriend(cat2);
      this.saveOrUpdate(cat1);
      this.saveOrUpdate(cat2);
    }

  }

  public CatDto toDto(Cat cat) {
    return CatDto.builder()
        .name(cat.getName())
        .color(cat.getColor().toString())
        .breed(cat.getBreed())
        .birthDate(cat.getBirthDate().toString())
        .ownerId(cat.getOwner().getId())
        .ownerName(cat.getOwner().getName())
        .friends(cat.getFriends().stream().collect(Collectors.toMap(Cat::getId, Cat::getName)))
        .build();
  }

  public Cat toEntity(CatDto catDto) throws NoEntityException {
    Owner owner = ownerService.findById(catDto.getOwnerId()).orElseThrow(() -> new NoEntityException(String.format("Owner with id = %s not found", catDto.getOwnerId())));
    List<Cat> friends = catDto.getFriends().keySet().stream()
        .map(id -> catRepository.findById(id).orElse(null))
        .filter(Objects::nonNull).toList();
    return Cat.builder()
        .name(catDto.getName())
        .color(Color.valueOf(catDto.getColor()))
        .breed(catDto.getBreed())
        .birthDate(LocalDate.parse(catDto.getBirthDate()))
        .owner(owner)
        .friends(friends)
        .build();
  }

}
