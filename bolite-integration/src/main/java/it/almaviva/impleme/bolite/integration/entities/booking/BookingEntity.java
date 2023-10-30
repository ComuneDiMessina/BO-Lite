package it.almaviva.impleme.bolite.integration.entities.booking;

import it.almaviva.impleme.bolite.integration.entities.room.RoomEntity;
import lombok.Data;
import org.hibernate.annotations.Check;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
@Entity
@Check(constraints = "booking_start_date < booking_end_date")
@Table(name = "BOOKING")
public class BookingEntity {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
    private Integer id;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false, name = "amount_servizi")
    private BigDecimal amountServizi = BigDecimal.ZERO;

    @Column
    private String note;

	@Column(name="booking_date",nullable = false,columnDefinition = "TIMESTAMP")
	private LocalDateTime bookingDate;
	
	@Column(name="protocol_number")
	private String protocolNumber;
	
	@Column(name="protocol_date",columnDefinition = "TIMESTAMP")
	private LocalDateTime protocolDate;
	
	@Column(name="booking_start_date",nullable = false,columnDefinition = "DATE")
	private LocalDate bookingStartDate;
	
	@Column(name="booking_end_date",nullable = false,columnDefinition = "DATE")
	private LocalDate bookingEndDate;
	
	@Column(name = "booking_start_hour", columnDefinition = "TIME")
	private LocalTime bookingStartHour;

	@Column(name = "booking_end_hour", columnDefinition = "TIME")
	private  LocalTime bookingEndHour;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    private RoomEntity room;
    
    @Column(name = "evento", nullable = false)
    private String eventType;
    
    @Column(name="event_description")
	private String eventDescription;
    
    @Column(name="event_title",nullable = false)
	private String eventTitle;

  @Column(name = "FLAG_WEEK", nullable = false)
  private Boolean flagWeek;
    
    @OneToMany(mappedBy = "booking" , orphanRemoval = true, cascade =  CascadeType.ALL)
    private Set<BookingServiceEntity> services = new HashSet<>(0);;

    public void addService(BookingServiceEntity item) {
        this.services.add(item);
        item.setBooking(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookingEntity that = (BookingEntity) o;
        return getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
