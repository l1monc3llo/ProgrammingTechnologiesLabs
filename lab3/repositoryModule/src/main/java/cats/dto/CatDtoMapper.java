package cats.dto;

import cats.entities.Cat;
import cats.repositories.CatRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@Getter
@Setter
public class CatDtoMapper {

    private CatRepository catRepository;
    public CatDtoMapper(CatRepository catRepository) {
        this.catRepository = catRepository;
    }


    public CatDto toDto(Cat cat) {
        CatDto catDto = new CatDto();
        catDto.setId(cat.getId());
        catDto.setName(cat.getName());
        catDto.setBirthDate(cat.getBirthDate());
        catDto.setBreed(cat.getBreed());
        catDto.setColor(cat.getColor());
        catDto.setFriendsIds(cat.getFriends().stream()
                .map(Cat::getId)
                .collect(Collectors.toList()));
        return catDto;
    }

    public List<CatDto> toDtoList(List<Cat> cats) {
        return cats.stream()
                .map(cat -> toDto(cat))
                .collect(Collectors.toList());
    }

    public Cat toEntity(CatDto catDto) {
        Cat cat = new Cat();
        cat.setId(catDto.getId());
        cat.setName(catDto.getName());
        cat.setBirthDate(catDto.getBirthDate());
        cat.setBreed(catDto.getBreed());
        cat.setColor(catDto.getColor());

        List<Cat> friends = catDto.getFriendsIds().stream()
                .map(id -> catRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid friend id: " + id)))
                .collect(Collectors.toList());

        cat.setFriends(friends);

        return cat;
    }

    public List<Cat> toEntityList(List<Long> catIds) {
        return catIds.stream()
                .map(id -> catRepository.findById(id).orElse(null))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}