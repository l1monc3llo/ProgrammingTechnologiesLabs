package owners.dto;

import cats.dto.CatDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
public class OwnerDto {
    private Long id;
    private String name;
    private LocalDate birthdate;
    private List<CatDto> cats;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OwnerDto ownerDto = (OwnerDto) o;
        return Objects.equals(id, ownerDto.id) &&
                Objects.equals(name, ownerDto.name) &&
                Objects.equals(birthdate, ownerDto.birthdate) &&
                Objects.equals(cats, ownerDto.cats);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, birthdate, cats);
    }
}