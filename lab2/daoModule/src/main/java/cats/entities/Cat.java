package cats.entities;

import cats.models.CatColor;
import lombok.Getter;
import lombok.Setter;
import owners.entities.Owner;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
public class Cat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private long ownerId;
    private LocalDate birthDate;
    private String breed;
    private CatColor color;

    @ManyToMany(cascade = CascadeType.REMOVE)
    @JoinTable(
            name = "cat_friends",
            joinColumns = @JoinColumn(name = "cat_id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id")
    )
    private List<Cat> friends = new ArrayList<>();
}
