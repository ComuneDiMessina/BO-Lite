package it.almaviva.impleme.bolite.integration.entities.room;

import lombok.Data;

import javax.persistence.*;
import java.util.*;

@Data
@Entity
@Table(name = "ROOM")
public class RoomEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	private Integer id;
	
	@Column(name = "codice", unique = true, nullable = false)
	private UUID codice;
	
	 @Column(name="name",nullable = false)
	 private String nome;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ente_id", nullable = false)
    private EnteEntity ente;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private RoomCategoryEntity category;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "structure_id", nullable = false)
    private RoomStructureTypeEntity structure;
    
    @Column(name="capacity",nullable = false)
    private Integer capienza;
    
    @Column(name="use_conditions", columnDefinition = "TEXT")
    private String condizioniUtilizzo;
    
    @Column(name="flag_catering")
    private Boolean catering;
    
    @Column(name="flag_terze_parti")
    private Boolean terzeParti;
    
    @Column(name = "advance_days",columnDefinition = "int default 15")
    private Integer giorniAnticipo;
    
    @Column
    private Boolean blocked;
    
    @OneToMany(mappedBy = "room" , orphanRemoval = true, cascade = { CascadeType.PERSIST,  CascadeType.MERGE})
    private Set<RoomServiceEntity> servizi = new HashSet<>(0);
    
    @OneToMany(mappedBy = "room" , orphanRemoval = true, cascade = { CascadeType.PERSIST,  CascadeType.MERGE})
    private Set<RoomTariffEntity> tariffario = new HashSet<>(0);
    
    @OneToMany(mappedBy = "room" , orphanRemoval = true, cascade = { CascadeType.PERSIST,  CascadeType.MERGE})
    private Set<RoomReservationEntity> riserve = new HashSet<>(0);

    @OneToMany(mappedBy = "room" , orphanRemoval = true,cascade = { CascadeType.PERSIST,  CascadeType.MERGE})
    private Set<RoomAperturaEntity> aperture = new HashSet<>(0);


    public void addApertura(RoomAperturaEntity item) {
        this.aperture.add(item);
        item.setRoom(this);
    }

    public void removeAperture() {
        Iterator<RoomAperturaEntity> iterator = this.aperture.iterator();

        while (iterator.hasNext()) {
            RoomAperturaEntity apertura = iterator.next();

            apertura.setRoom(null);
            iterator.remove();
        }
    }


    public void removeRiserve() {
        Iterator<RoomReservationEntity> iterator = this.riserve.iterator();

        while (iterator.hasNext()) {
            RoomReservationEntity riserva = iterator.next();

            riserva.setRoom(null);
            iterator.remove();
        }
    }


    public void removeServizi() {
        Iterator<RoomServiceEntity> iterator = this.servizi.iterator();

        while (iterator.hasNext()) {
            RoomServiceEntity s = iterator.next();

            s.setRoom(null);
            iterator.remove();
        }
    }

    public void removeTariffe() {
        Iterator<RoomTariffEntity> iterator = this.tariffario.iterator();

        while (iterator.hasNext()) {
            RoomTariffEntity s = iterator.next();

            s.setRoom(null);
            iterator.remove();
        }
    }



    public void addService(RoomServiceEntity item) {
        this.servizi.add(item);
        item.setRoom(this);
    }
    
    public void addTariff(RoomTariffEntity item) {
        this.tariffario.add(item);
       	item.setRoom(this);
    }
    
    public void addReservation(RoomReservationEntity item) {
        this.riserve.add(item);
    	item.setRoom(this);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoomEntity that = (RoomEntity) o;
        return getCodice().equals(that.getCodice());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCodice());
    }
}
