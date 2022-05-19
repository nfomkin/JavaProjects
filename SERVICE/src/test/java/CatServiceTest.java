import dao.*;
import entity.*;
import java.time.*;
import java.util.*;
import org.junit.jupiter.api.*;
import org.mockito.*;
import services.*;

public class CatServiceTest {

  @Mock
  private CatDao dao;
  private CatService service;
  private Owner owner;
  private List<Cat> cats;


  public CatServiceTest() {
    MockitoAnnotations.openMocks(this);
    service = new CatService(dao);
  }

  @BeforeEach
  void createData() {
    owner = Owner.builder()
        .id(1L).name("Nikita").birthDate(LocalDate.of(2002, 10, 12)).build();
    cats = new ArrayList<>();
    Cat cat1 = Cat.builder()
        .id(1L).name("Sonya").owner(owner).build();
    Cat cat2 = Cat.builder()
        .id(2L).name("Archi").owner(owner).build();
    Cat cat3 = Cat.builder()
        .id(3L).name("Mysya").owner(owner).build();
    Cat cat4 = Cat.builder()
        .id(4L).name("Kosmos").owner(owner).build();
    cats.add(cat1);
    cats.add(cat2);
    cats.add(cat3);
    cats.add(cat4);
  }

  @Test
  void findAllCatsShouldReturnList() {
    Mockito.when(dao.findAll()).thenReturn(cats);

    var result = service.findAll();
    Assertions.assertEquals(cats, result);
    Mockito.verify(dao).findAll();
  }

  @Test
  void findByIdCatShouldReturnCat(){
    Mockito.when(dao.findById(1L)).thenReturn(Optional.ofNullable(cats.get(0)));

    var result = service.findById(1L);
    Assertions.assertEquals(Optional.ofNullable(cats.get(0)), result);
    Mockito.verify(dao).findById(1L);
  }

  @Test
  void saveCatShouldReturnCat() {
    Cat cat = cats.get(0);
    Mockito.when(dao.save(cat)).thenReturn(cat);

    var result = service.save(cat);
    Assertions.assertEquals(cat, result);
  }

  @Test
  void deleteCatShouldReturnTrue() {
    Cat cat = cats.get(0);
    Mockito.when(dao.delete(cat.getId())).thenReturn(true);

    var result = service.delete(cat.getId());
    Assertions.assertTrue(result);
  }

}
