package ru.itmo.nfomkin.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.itmo.nfomkin.domain.Cat;
import ru.itmo.nfomkin.domain.Owner;

@DataJpaTest
class CatRepositoryTest {

  @Autowired
  private CatRepository catRepository;
  @Autowired
  private OwnerRepository ownerRepository;

  @Test
  void findByOwnerAndId() {
    // given
    Owner owner = Owner.builder()
        .username("admin")
        .password("123")
        .name("Nikita Fomkin")
        .build();
    owner = ownerRepository.save(owner);

    Cat cat = Cat.builder()
        .name("Sonya")
        .owner(owner)
        .build();
    cat = catRepository.save(cat);

    // when
    Optional<Cat> foundCat = catRepository.findByOwnerAndId(owner, cat.getId());

    // then
    assertThat(foundCat).isNotEmpty();
    assertThat(foundCat.get().getOwner().getId())
        .isEqualTo(owner.getId());
    assertThat(foundCat.get().getId())
        .isEqualTo(cat.getId());
    assertThat(foundCat.get().getName())
        .isEqualTo(cat.getName());
  }

  @Test
  void findAllByOwner() {
    // given
    Owner owner = Owner.builder()
        .username("admin")
        .password("123")
        .name("Nikita Fomkin")
        .build();
    owner = ownerRepository.save(owner);

    Cat cat1 = Cat.builder()
        .name("Sonya")
        .owner(owner)
        .build();
    Cat cat2 = Cat.builder()
        .name("Sonya2")
        .owner(owner)
        .build();
    Cat cat3 = Cat.builder()
        .name("Sonya3")
        .owner(owner)
        .build();

    List<Cat> expected = new ArrayList<>();
    expected.add(cat1);
    expected.add(cat2);
    expected.add(cat3);
    catRepository.saveAll(expected);
    // when
    List<Cat> foundCats = catRepository.findAllByOwner(owner);

    // then
    assertThat(foundCats).isEqualTo(expected);
  }



}