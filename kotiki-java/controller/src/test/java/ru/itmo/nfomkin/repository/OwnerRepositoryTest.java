package ru.itmo.nfomkin.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.itmo.nfomkin.domain.Owner;

@DataJpaTest
class OwnerRepositoryTest {

  @Autowired
  private OwnerRepository repository;
  private Owner owner1;
  private Owner owner2;
  private Owner owner3;

  @BeforeEach
  void setUp() {
    owner1 = Owner.builder()
        .username("nfomkin")
        .password("123")
        .name("Nikita")
        .build();
    owner2 = Owner.builder()
        .username("nfomkin2")
        .password("123")
        .name("Nikita")
        .build();
    owner3 = Owner.builder()
        .username("alexander")
        .password("123")
        .name("Sanya")
        .build();

    repository.save(owner1);
    repository.save(owner2);
    repository.save(owner3);
  }

  @Test
  void findFirstByUsername() {

    Optional<Owner> maybeOwner = repository.findFirstByUsername("nfomkin");

    assertThat(maybeOwner).isNotEmpty();
    assertThat(maybeOwner.get().getId()).isEqualTo(owner1.getId());
  }

  @Test
  void findByName() {

    List<Owner> owners = repository.findByName("Nikita");

    assertThat(owners.size()).isEqualTo(2);
  }
}