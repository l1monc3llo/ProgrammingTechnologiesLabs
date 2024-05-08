package users;

import lombok.Getter;
import lombok.Setter;
import owners.entities.Owner;
import roles.Role;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String password;

    @ManyToOne
    private Role role;

    @OneToOne
    private Owner owner;
}