package it.almaviva.impleme.bolite.integration.entities.room;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.LocalTime;

@Data
@Entity
@EqualsAndHashCode(exclude = {"room"})
@Table(name = "room_openings",  uniqueConstraints=
@UniqueConstraint(columnNames={"day_of_week", "room_id"}))
public class RoomAperturaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
    private Integer id;

    @Column(name = "day_of_week")
    private Integer day_of_week;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", referencedColumnName = "id")
    private RoomEntity room;

    @Column(name = "start_hour", columnDefinition = "TIME")
    private LocalTime oraDa;

    @Column(name = "end_hour", columnDefinition = "TIME")
    private LocalTime oraA;


}
