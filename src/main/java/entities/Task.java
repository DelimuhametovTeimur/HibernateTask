package entities;

import enums.Status;
import lombok.*;

import javax.persistence.*;
import java.sql.Date;

@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@ToString
@Setter
@Getter
@Table(name = "Task")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id")
    private Long id;

    @NonNull
    @Column(name = "Name")
    private String name;

    @NonNull
    @Column(name = "Description")
    private String description;

    @NonNull
    @Column(name = "StartDate")
    private Date startDate;

    @NonNull
    @Column(name = "EndDate")
    private Date endDate;

    @NonNull
    @Enumerated(EnumType.STRING)
    private Status status;

}
