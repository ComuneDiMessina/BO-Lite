package it.almaviva.impleme.bolite.core.mappers;

import it.almaviva.impleme.bolite.domain.dto.AttachmentDTO;
import it.almaviva.impleme.bolite.integration.entities.casefile.CaseFileDocumentEntity;
import org.mapstruct.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Mapper(componentModel="spring", imports = {Collectors.class, Optional.class, Collection.class, Stream.class }, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, unmappedTargetPolicy = ReportingPolicy.IGNORE, unmappedSourcePolicy = ReportingPolicy.IGNORE)

public interface IDocumentMapper {

	@Mappings({
		  @Mapping(target = "idDocument", source = "idDocument"),
	      @Mapping(target = "name", source = "fileName"),
	      @Mapping(target = "descrizione", source = "description"),
	})
	public abstract CaseFileDocumentEntity dtoToEntity(AttachmentDTO attachmentDTO);
	
	

	@Mappings({
		  @Mapping(target = "idDocument", source = "idDocument"),
	      @Mapping(target = "fileName", source = "name"),
	      @Mapping(target = "description", source = "descrizione"),
	})
	@Named("toDto")
	public abstract AttachmentDTO map(CaseFileDocumentEntity attachmentEntity);
	
	@IterableMapping(qualifiedByName = "toDto")
	public abstract ArrayList<AttachmentDTO> map(Set<CaseFileDocumentEntity> attachmentEntity);
}
