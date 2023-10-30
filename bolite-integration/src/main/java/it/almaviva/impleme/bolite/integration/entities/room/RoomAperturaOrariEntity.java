package it.almaviva.impleme.bolite.integration.entities.room;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.LocalTime;

@Data
@Entity
@EqualsAndHashCode(exclude = {"apertura"})
@Table(name = "room_openings_hours")
public class RoomAperturaOrariEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
    private Integer id;


    @Column(name = "start_hour", columnDefinition = "TIME")
    private LocalTime oraDa;

    @Column(name = "end_hour", columnDefinition = "TIME")
    private LocalTime oraA;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_openings_id", referencedColumnName = "id")
    private RoomAperturaEntity apertura;

}
