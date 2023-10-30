package it.almaviva.impleme.bolite.core.mappers.room;

import it.almaviva.impleme.bolite.domain.dto.booking.room.CategoryDTO;
import it.almaviva.impleme.bolite.integration.entities.room.RoomCategoryEntity;
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
public interface IRoomCategoryMapper {

    @Mapping(target = "categoria", source = "category")
    CategoryDTO map (RoomCategoryEntity entity);

    List<CategoryDTO> map (List<RoomCategoryEntity> entity);

}
