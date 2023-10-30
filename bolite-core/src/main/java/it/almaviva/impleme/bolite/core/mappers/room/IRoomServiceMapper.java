package it.almaviva.impleme.bolite.core.mappers.room;

import it.almaviva.impleme.bolite.domain.dto.booking.services.ServiceDTO;
import it.almaviva.impleme.bolite.integration.entities.room.RoomServiceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Mapper(componentModel="spring", imports = {Collectors.class, Optional.class, Collection.class, Stream.class }, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, unmappedTargetPolicy = ReportingPolicy.IGNORE, unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IRoomServiceMapper {

  ServiceDTO map (RoomServiceEntity entity);

}
