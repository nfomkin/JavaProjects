package dto;

import java.time.*;

public record CatFilter(int limit, int offset, String name, LocalDate birthDate,
                        String breed, String color, Long ownerId) {

}
