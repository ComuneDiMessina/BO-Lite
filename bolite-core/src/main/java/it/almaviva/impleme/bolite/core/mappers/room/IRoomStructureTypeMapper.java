package it.almaviva.impleme.bolite.core.mappers.room;

import it.almaviva.impleme.bolite.domain.dto.booking.room.TypologyDTO;
import it.almaviva.impleme.bolite.integration.entities.room.RoomStructureTypeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Mapper(componentModel="spring", imports = {Collectors.class, Optional.class, Collection.class, Stream.class }, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, unmappedTargetPolicy = ReportingPolicy.IGNORE, unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IRoomStructureTypeMapper {

    @Mapping(target = "tipo", source = "struttura")
    TypologyDTO map (RoomStructureTypeEntity entity);

    List<TypologyDTO> map (List<RoomStructureTypeEntity> entity);
}
