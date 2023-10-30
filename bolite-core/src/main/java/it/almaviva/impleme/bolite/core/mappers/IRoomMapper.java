package it.almaviva.impleme.bolite.core.mappers;

import it.almaviva.impleme.bolite.domain.dto.booking.room.AperturaDTO;
import it.almaviva.impleme.bolite.domain.dto.booking.room.RangeDTO;
import it.almaviva.impleme.bolite.domain.dto.booking.room.RoomDTO;
import it.almaviva.impleme.bolite.domain.dto.booking.room.TariffarioDTO;
import it.almaviva.impleme.bolite.integration.entities.room.*;
import org.mapstruct.*;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Mapper(componentModel="spring", imports = {Collectors.class, Optional.class, Collection.class, Stream.class }, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, unmappedTargetPolicy = ReportingPolicy.IGNORE, unmappedSourcePolicy = ReportingPolicy.IGNORE)

public interface IRoomMapper {


    @Mapping(target = "comune", source = "ente.codice")
    @Mapping(target = "tipoStruttura", source = "structure.id")
    @Mapping(target = "categoria", source = "category.id")
    @Mapping(target = "id", source = "codice")
    RoomDTO entityToDto(RoomEntity roomEntity);

    List<RoomDTO> entityListToDtoList(Iterable<RoomEntity> roomEntities);

    List<TariffarioDTO> entityListToDtoTariffarioList(Set<RoomTariffEntity> rangeEntities);

    @Mapping(target = "giorno", expression = "java( AperturaDTO.DAYS.valueOf(entity.getDay_of_week()))")
    AperturaDTO map(RoomAperturaEntity entity);

    @Mapping(target = "costoFasce", source = "fasce")
    @Mapping(target = "tipoEvento", source = "eventType")
    @Mapping(target = "tipo", ignore = true )
    TariffarioDTO map(RoomTariffEntity entity);

    @Mapping(target = "giorno", expression = "java( AperturaDTO.DAYS.valueOf(entity.getGiorno()))")
    RangeDTO map(RoomDailyTariffEntity entity);


    List<RangeDTO> entityListToDtoRoomRangeList(Set<RoomRowEntity> roomRangeEntities);

    @BeforeMapping
    default void beforeMapping(@MappingTarget TariffarioDTO target, RoomTariffEntity source) {
        if(source.getCostoOrario() != null && source.getCostoOrario().compareTo(BigDecimal.ZERO)>0){
            target.setTipo("TARIFFA A ORE");
        }else if(source.getCostoSettimanale() != null && source.getFlagInteraSettimana()){
            target.setTipo("TARIFFA SETTIMANALE");
        }else if(source.getFasce() != null && !source.getFasce().isEmpty()){
            target.setTipo("TARIFFA A FASCE");
        }
    }

}
