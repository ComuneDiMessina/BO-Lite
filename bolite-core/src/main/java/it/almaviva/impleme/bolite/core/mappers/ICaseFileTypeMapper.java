package it.almaviva.impleme.bolite.core.mappers;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.almaviva.impleme.bolite.domain.dto.ServizioDTO;
import it.almaviva.impleme.bolite.integration.entities.casefile.CaseFileTypeEntity;
import org.mapstruct.*;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Mapper(componentModel="spring", imports = {JsonParseException.class, IOException.class, ObjectMapper.class ,  Collectors.class, Optional.class, Collection.class, Stream.class }, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, unmappedTargetPolicy = ReportingPolicy.IGNORE, unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ICaseFileTypeMapper {

  @Mappings({
          @Mapping(target = "nome", source = "name"),
          @Mapping(target = "descrizione", source = "description"),
          @Mapping(target = "note", source = "notes"),
          @Mapping(target = "libero", source = "freeText"),
          @Mapping(target = "img", source = "img"),
          @Mapping(target = "importo", source = "importo"),

  })
  ServizioDTO map (CaseFileTypeEntity entity);


  List<ServizioDTO> map(Iterable<CaseFileTypeEntity> entities);
}
