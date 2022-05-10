package runner;

import entities.*;
import enums.*;
import java.time.*;
import java.util.*;
import service.*;

public class CatServiceRunner {

  public static void main(String[] args) {
    CatService catService = new CatService();
    OwnerService ownerService = new OwnerService();
    Owner owner = ownerService.findById(16L);
    Cat cat = Cat.builder().name("Sonya").color(Color.BLACK).owner(owner).build();
    Cat cat2 = Cat.builder().name("Kosmos").color(Color.BLACK).owner(owner).build();
    Cat cat3 = Cat.builder().name("Mysya").color(Color.BLACK).owner(owner).build();
    Cat cat4 = Cat.builder().name("Bonya").color(Color.BLACK).owner(owner).build();
    catService.save(cat, null);
    catService.save(cat2, null);
    catService.save(cat3, null);
    List<Cat> friends = new ArrayList<Cat>();
    friends.add(cat);
    friends.add(cat2);
    friends.add(cat3);
    catService.save(cat4, friends);
  }

}
