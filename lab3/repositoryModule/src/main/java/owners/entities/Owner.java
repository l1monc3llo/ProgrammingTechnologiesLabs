package owners.entities;

import cats.entities.Cat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;


@Entity
@Getter
@Setter
public class Owner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private LocalDate birthdate;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.REMOVE)
    private List<Cat> cats;

}