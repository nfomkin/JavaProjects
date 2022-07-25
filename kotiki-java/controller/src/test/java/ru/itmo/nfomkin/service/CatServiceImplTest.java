package ru.itmo.nfomkin.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;

import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.itmo.nfomkin.domain.Cat;
import ru.itmo.nfomkin.domain.Owner;
import ru.itmo.nfomkin.exception.NoEntityException;
import ru.itmo.nfomkin.repository.CatRepository;

@ExtendWith(MockitoExtension.class)
public class CatServiceImplTest {

  @Mock
  private CatRepository catRepository;
  @Mock
  private OwnerService ownerService;
  private CatService catService;

  @BeforeEach
  void setUp() {
    catService = new CatServiceImpl(catRepository, ownerService);
  }

  @Test
  void checkFindAll() {

    catService.findAll();

    Mockito.verify(catRepository).findAll();
  }

  @Test
  void checkFindAllByOwner() throws NoEntityException {
    //have
    String username = "nfomkin";
    Owner owner = Owner.builder()
        .username(username)
        .password("123")
        .name("Nikita Fomkin")
        .build();

    Mockito.when(ownerService.findByUsername(username)).thenReturn(Optional.of(owner));

    // when
    catService.findAllByOwner(username);

    // then
    ArgumentCaptor<Owner> argumentCaptor = ArgumentCaptor.forClass(Owner.class);

    Mockito.verify(catRepository).findAllByOwner(argumentCaptor.capture());

    assertThat(argumentCaptor.getValue()).isEqualTo(owner);
  }

  @Test
  void callFindAllByOwnerWithNotExistingUsernameAndThrowException() {
    // have
    String username = "nfomkin";

    Mockito.when(ownerService.findByUsername(username)).thenReturn(Optional.empty());

    // when and then
    assertThatExceptionOfType(NoEntityException.class)
        .isThrownBy(() -> {
          catService.findAllByOwner(username);
        });

    Mockito.verify(catRepository, Mockito.never()).findAllByOwner(Mockito.any());
  }

  @Test
  void checkFindByOwnerAndId() throws NoEntityException {
    // have
    String username = "nfomkin";
    Owner owner = Owner.builder()
        .id(1L)
        .username(username)
        .password("123")
        .name("Nikita Fomkin")
        .build();


    Mockito.when(ownerService.findByUsername(username)).thenReturn(Optional.of(owner));

    // when
    catService.findByOwnerAndId(username, owner.getId());

    // then
    ArgumentCaptor<Owner> ownerArgumentCaptor = ArgumentCaptor.forClass(Owner.class);
    Mockito.verify(catRepository).findByOwnerAndId(ownerArgumentCaptor.capture(), Mockito.eq(owner.getId()));
    assertThat(ownerArgumentCaptor.getValue()).isEqualTo(owner);
  }


  @Test
  void checkAddToOwner() throws NoEntityException {
    // have
    String username = "nfomkin";
    Owner owner = Owner.builder()
        .id(1L)
        .username(username)
        .password("123")
        .name("Nikita Fomkin")
        .build();

    Long catId = 1L;
    Cat cat = Cat.builder()
        .id(catId)
        .name("Sonya")
        .build();

    Mockito.when(ownerService.findByUsername(username)).thenReturn(Optional.of(owner));
    Mockito.when(catRepository.findById(catId)).thenReturn(Optional.of(cat));

    // when
    catService.addToOwner(username, catId);

    // then
    ArgumentCaptor<Owner> ownerArgumentCaptor = ArgumentCaptor.forClass(Owner.class);
    Mockito.verify(ownerService).saveOrUpdate(ownerArgumentCaptor.capture());

    Owner capturedOwner = ownerArgumentCaptor.getValue();

    assertThat(capturedOwner.getCats().contains(cat)).isTrue();
    assertThat(cat.getOwner()).isEqualTo(capturedOwner);
  }

  @Test
  void checkAddFriendship(){
    // have
    Cat cat1 = Cat.builder()
        .id(1L)
        .name("Sonya")
        .build();
    Cat cat2 = Cat.builder()
        .id(2L)
        .name("Mysya")
        .build();

    Mockito.when(catRepository.findById(cat1.getId())).thenReturn(Optional.of(cat1));
    Mockito.when(catRepository.findById(cat2.getId())).thenReturn(Optional.of(cat2));


  }



}
