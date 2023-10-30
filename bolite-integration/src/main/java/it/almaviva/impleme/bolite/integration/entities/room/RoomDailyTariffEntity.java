package it.almaviva.impleme.bolite.integration.entities.room;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "ROOM_DAILY_TARIFF")
@EqualsAndHashCode(exclude = {"fasce","tariff"})
public class RoomDailyTariffEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
    private Integer id;

    @Column(name = "day_of_week", nullable = false)
    private Integer giorno;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_tariff_id", nullable = false)
    private RoomTariffEntity tariff;

    @OneToMany(mappedBy = "tariff" , orphanRemoval = true, cascade  = {  CascadeType.PERSIST,  CascadeType.MERGE})
    private Set<RoomRowEntity> fasce = new HashSet<>(0);

    public void addFascia(RoomRowEntity item) {
        this.fasce.add(item);
        item.setTariff(this);
    }

}
