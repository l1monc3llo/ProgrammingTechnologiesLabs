package cats.dto;

import cats.models.CatColor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
public class CatDto {
    private long id;
    private String name;
    private LocalDate birthDate;
    private String breed;
    private CatColor color;
    private List<Long> friendsIds = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CatDto catDto = (CatDto) o;
        return id == catDto.id &&
                Objects.equals(name, catDto.name) &&
                Objects.equals(birthDate, catDto.birthDate) &&
                Objects.equals(breed, catDto.breed) &&
                color == catDto.color &&
                Objects.equals(friendsIds, catDto.friendsIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, birthDate, breed, color, friendsIds);
    }

    public void setFriendsIds(List<Long> friendsIds) {
        this.friendsIds = friendsIds;
    }
}
