package entities;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@ToString
@Setter
@Getter
@Table(name = "Discipline")
public class Discipline {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @NonNull
    @Column(name = "name")
    private String name;

    @ToString.Exclude
    @OneToMany(targetEntity = User.class, mappedBy = "discipline", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<User> members = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "headOfDiscipline")
    private User headOfDiscipline;

    public void addMembers(User user){
        members.add(user);
        user.setDiscipline(this);
    }
}