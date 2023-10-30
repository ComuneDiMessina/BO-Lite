package it.almaviva.impleme.bolite.core.mappers.room;

import it.almaviva.impleme.bolite.domain.dto.booking.room.AperturaDTO;
import it.almaviva.impleme.bolite.domain.dto.booking.room.RangeDTO;
import it.almaviva.impleme.bolite.domain.dto.booking.room.TariffarioDTO;
import it.almaviva.impleme.bolite.integration.entities.room.RoomAperturaEntity;
import it.almaviva.impleme.bolite.integration.entities.room.RoomDailyTariffEntity;
import it.almaviva.impleme.bolite.integration.entities.room.RoomTariffEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Mapper(componentModel="spring", imports = {Collectors.class, Optional.class, Collection.class, Stream.class }, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, unmappedTargetPolicy = ReportingPolicy.IGNORE, unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IRoomTariffMapper {

    @Mapping(target = "giorno", expression = "java( AperturaDTO.DAYS.valueOf(entity.getDay_of_week()))")
    AperturaDTO map(RoomAperturaEntity entity);

    @Mapping(target = "costoFasce", source = "fasce")
    @Mapping(target = "tipoEvento", source = "eventType")
    TariffarioDTO map (RoomTariffEntity entity);

    @Mapping(target = "giorno", expression = "java( AperturaDTO.DAYS.valueOf(entity.getGiorno()))")
    RangeDTO map(RoomDailyTariffEntity entity);

}
