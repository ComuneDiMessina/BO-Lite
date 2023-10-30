package it.almaviva.impleme.bolite.integration.entities.booking;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Data
@Entity
@Table(name = "BOOKING_SERVICE")
public class BookingServiceEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	private Integer id;
	
	@Column(nullable = false)
	private String code;
	
	@Column(nullable = false)
	private String description;
	
	@Column
	private String note;
	
	@Column
	private BigDecimal amount;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id", nullable = false)
    private BookingEntity booking;


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		BookingServiceEntity that = (BookingServiceEntity) o;
		return getCode().equals(that.getCode());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getCode());
	}
}
