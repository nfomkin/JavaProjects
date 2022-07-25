package ru.itmo.nfomkin.service;

import java.util.List;
import java.util.Optional;
import ru.itmo.nfomkin.domain.Cat;
import ru.itmo.nfomkin.dto.CatDto;
import ru.itmo.nfomkin.exception.NoEntityException;

public interface CatService {

  public List<Cat> findAll();

  public List<Cat> findAllByOwner(String username) throws NoEntityException;

  public Optional<Cat> findByOwnerAndId(String username, Long id) throws NoEntityException;

  public List<Cat> findByName(String name);

  public Optional<Cat> findById(Long id);

  public void saveOrUpdate(Cat cat) throws NoEntityException;

  public void delete(Long id) throws NoEntityException;

  public void addToOwner(String username, Long catId) throws NoEntityException;

  public void addFriendship(Long catId1, Long catId2) throws NoEntityException;

  public void deleteFriendship(Long catId1, Long catId2) throws NoEntityException;

  public CatDto toDto(Cat cat);

  public Cat toEntity(CatDto catDto) throws NoEntityException;

}
