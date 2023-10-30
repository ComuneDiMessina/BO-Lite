package it.almaviva.impleme.bolite.core.mappers;

import it.almaviva.impleme.bolite.integration.entities.casefile.CaseFileEntity;
import it.almaviva.impleme.bolite.integration.entities.casefile.CaseFileUserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Mapper(componentModel="spring", imports = {Collectors.class, Optional.class, Collection.class, Stream.class }, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, unmappedTargetPolicy = ReportingPolicy.IGNORE, unmappedSourcePolicy = ReportingPolicy.IGNORE)

public interface IReportMapper {
	
	static class ReportFields {
		
		//richiedente - organizzatore
        final static String NOME_RICHIEDENTE_ORGANIZZATORE = "nome_richiedente_organizzatore";
        final static String COGNOME_RICHIEDENTE_ORGANIZZATORE = "cognome_richiedente_organizzatore";
        final static String BIRTH_PLACE_RICHIEDENTE_ORGANIZZATORE = "birth_place_richiedente_organizzatore";
        final static String BIRTH_DATE_RICHIEDENTE_ORGANIZZATORE = "birth_date_richiedente_organizzatore";
        final static String CODICE_FISCALE_RICHIEDENTE_ORGANIZZATORE = "cf_richiedente_organizzatore";
        final static String INDIRIZZO_RICHIEDENTE_ORGANIZZATORE = "indirizzo_richiedente_organizzatore";
        final static String CIVICO_RICHIEDENTE_ORGANIZZATORE = "civico_richiedente_organizzatore";
        final static String COMUNE_RICHIEDENTE_ORGANIZZATORE = "comune_richiedente_organizzatore";
        final static String PROVINCIA_RICHIEDENTE_ORGANIZZATORE = "provincia_richiedente_organizzatore";
        final static String CAP_RICHIEDENTE_ORGANIZZATORE = "cap_richiedente_organizzatore";
        final static String RAGIONE_SOCIALE_RICHIEDENTE_ORGANIZZATORE = "ragione_sociale_richiedente_organizzatore";
        final static String PIVA_SOCIALE_RICHIEDENTE_ORGANIZZATORE = "piva_richiedente_organizzatore";
        final static String EMAIL_SOCIALE_RICHIEDENTE_ORGANIZZATORE = "email_richiedente_organizzatore";
        final static String TELEFONO_RICHIEDENTE_ORGANIZZATORE = "telefono_richiedente_organizzatore";
        final static String CELLULARE_RICHIEDENTE_ORGANIZZATORE = "cellulare_richiedente_organizzatore";
        
        //flag
        final static String FLAG_RICHIEDENTE = "flag_richiedente";
        final static String FLAG_RICHIEDENTE_ORGANIZZATORE = "flag_richiedente_organizzatore";
        final static String FLAG_ORGANIZZATORE = "flag_organizzatore";
        
        //richiedente
        final static String NOME_ORGANIZZATORE = "nome_richiedente_organizzatore";
        final static String COGNOME_ORGANIZZATORE = "cognome_richiedente_organizzatore";
        final static String BIRTH_PLACE_ORGANIZZATORE = "birth_place_richiedente_organizzatore";
        final static String BIRTH_DATE_ORGANIZZATORE = "birth_date_richiedente_organizzatore";
        final static String CODICE_FISCALE_ORGANIZZATORE = "cf_richiedente_organizzatore";
        final static String INDIRIZZO_ORGANIZZATORE = "indirizzo_richiedente_organizzatore";
        final static String CIVICO_ORGANIZZATORE = "civico_richiedente_organizzatore";
        final static String COMUNE_ORGANIZZATORE = "comune_richiedente_organizzatore";
        final static String PROVINCIA_ORGANIZZATORE = "provincia_richiedente_organizzatore";
        final static String CAP_ORGANIZZATORE = "cap_richiedente_organizzatore";
        final static String RAGIONE_SOCIALE_ORGANIZZATORE = "ragione_sociale_richiedente_organizzatore";
        final static String PIVA_SOCIALE_ORGANIZZATORE = "piva_richiedente_organizzatore";
        final static String EMAIL_SOCIALE_ORGANIZZATORE = "email_richiedente_organizzatore";
        final static String TELEFONO_ORGANIZZATORE = "telefono_richiedente_organizzatore";
        final static String CELLULARE_ORGANIZZATORE = "cellulare_richiedente_organizzatore";
        
 
    	final static String ROOM_NAME_REPORT_FIELD_NAME = "roomName";
    	final static String ROOM_BOOKING_PURPOSE_REPORT_FIELD_NAME = "roomBookingPurpose";
    	final static String REPORT_DATAPATH_FIELD_NAME = "REPORT_DATAPATH";
    	final static String AMOUNT_AUTO_REPORT_FIELD_NAME = "amount";
		final static String DETAILS_FIELD_NAME = "dettaglio";
    	final static String QRCODE = "qrcode";
    	final static String USE_CONDITION = "use_condiction";
    	
    	//pratica generica
    	 final static String OWNER_NAME_REPORT_FIELD_NAME = "ownerName";
         final static String OWNER_SURNAME_REPORT_FIELD_NAME = "ownerSurname";
         final static String OWNER_EMAIL_REPORT_FIELD_NAME = "ownerEmail";
         final static String OWNER_TELEPHONE_REPORT_FIELD_NAME = "ownerTelephoneNumber";
         final static String OWNER_CITY_REPORT_FIELD_NAME = "ownerCity";
         final static String OWNER_PROVINCE_REPORT_FIELD_NAME = "ownerProvince";
         final static String OWNER_TAXCODE_REPORT_FIELD_NAME = "ownerTaxCode";
         final static String OWNER_ADDRESS_REPORT_FIELD_NAME = "ownerAddress";
         final static String OWNER_CAP_REPORT_FIELD_NAME = "ownerPostalCode";

		final static String DELEGATO_NAME_REPORT_FIELD_NAME = "delegatoName";
		final static String DELEGATO_SURNAME_REPORT_FIELD_NAME = "delegatoSurname";
		final static String DELEGATO_EMAIL_REPORT_FIELD_NAME = "delegatoEmail";
		final static String DELEGATO_TELEPHONE_REPORT_FIELD_NAME = "delegatoTelephoneNumber";
		final static String DELEGATO_CITY_REPORT_FIELD_NAME = "delegatoCity";
		final static String DELEGATO_PROVINCE_REPORT_FIELD_NAME = "delegatoProvince";
		final static String DELEGATO_TAXCODE_REPORT_FIELD_NAME = "delegatoTaxCode";
		final static String DELEGATO_ADDRESS_REPORT_FIELD_NAME = "delegatoAddress";
		final static String DELEGATO_CAP_REPORT_FIELD_NAME = "delegatoPostalCode";

    	
    }
	

	default Map<String, Object> CaseFileEntityToMap(CaseFileEntity caseFileEntity){
		Map<String, Object> reportMap = new HashMap<String, Object>();
		
		reportMap.put(ReportFields.ROOM_NAME_REPORT_FIELD_NAME, caseFileEntity.getBooking().getRoom().getNome());
        //reportMap.put(ReportFields.ROOM_BOOKING_PURPOSE_REPORT_FIELD_NAME, dto.getRoomBookingInfo().getPurpose());
		reportMap.put(ReportFields.USE_CONDITION, caseFileEntity.getBooking().getRoom().getCondizioniUtilizzo());
		
		Set<CaseFileUserEntity> users = caseFileEntity.getUsers();
	
      

        	
        	for (CaseFileUserEntity caseFileUserEntity : users) {
        		
        		
        	  	if(caseFileUserEntity.getFlag_organizzatore().equals(true) && caseFileUserEntity.getFlag_richiedente().equals(true)) {
              		 reportMap.put(ReportFields.NOME_RICHIEDENTE_ORGANIZZATORE, caseFileUserEntity.getNome());
              		 reportMap.put(ReportFields.COGNOME_RICHIEDENTE_ORGANIZZATORE, caseFileUserEntity.getSurname());
              		 reportMap.put(ReportFields.BIRTH_PLACE_RICHIEDENTE_ORGANIZZATORE, caseFileUserEntity.getBirthPlace());
              		 reportMap.put(ReportFields.BIRTH_DATE_RICHIEDENTE_ORGANIZZATORE, caseFileUserEntity.getBirthDate().toString());
              		 reportMap.put(ReportFields.CODICE_FISCALE_RICHIEDENTE_ORGANIZZATORE, caseFileUserEntity.getCf());
              		 reportMap.put(ReportFields.INDIRIZZO_RICHIEDENTE_ORGANIZZATORE, caseFileUserEntity.getResidenza_address());
              		 reportMap.put(ReportFields.CIVICO_RICHIEDENTE_ORGANIZZATORE, caseFileUserEntity.getResidenza_civico());
              		 reportMap.put(ReportFields.COMUNE_RICHIEDENTE_ORGANIZZATORE, caseFileUserEntity.getResidenza_comune());
              		 reportMap.put(ReportFields.PROVINCIA_RICHIEDENTE_ORGANIZZATORE, caseFileUserEntity.getResidenza_provincia());
              		 reportMap.put(ReportFields.CAP_RICHIEDENTE_ORGANIZZATORE, caseFileUserEntity.getResidenza_cap());
              		 reportMap.put(ReportFields.RAGIONE_SOCIALE_RICHIEDENTE_ORGANIZZATORE, caseFileUserEntity.getEnte_ragione_sociale());
              		 reportMap.put(ReportFields.PIVA_SOCIALE_RICHIEDENTE_ORGANIZZATORE, caseFileUserEntity.getPiva());
              		 reportMap.put(ReportFields.EMAIL_SOCIALE_RICHIEDENTE_ORGANIZZATORE, caseFileUserEntity.getEmail());
              		 reportMap.put(ReportFields.TELEFONO_RICHIEDENTE_ORGANIZZATORE, caseFileUserEntity.getTelephoneNumber());
              		 reportMap.put(ReportFields.FLAG_RICHIEDENTE, true);
              		 reportMap.put(ReportFields.FLAG_RICHIEDENTE_ORGANIZZATORE, true);
              		 break;
              		 
              		 
              	}
              		
              		
        	  	
        	  	
        	  	if (caseFileUserEntity.getFlag_richiedente().equals(true))
    			
        	  	 reportMap.put(ReportFields.NOME_RICHIEDENTE_ORGANIZZATORE, caseFileUserEntity.getNome());
        		 reportMap.put(ReportFields.COGNOME_RICHIEDENTE_ORGANIZZATORE, caseFileUserEntity.getSurname());
        		 reportMap.put(ReportFields.BIRTH_PLACE_RICHIEDENTE_ORGANIZZATORE, caseFileUserEntity.getBirthPlace());
        		 reportMap.put(ReportFields.BIRTH_DATE_RICHIEDENTE_ORGANIZZATORE,  caseFileUserEntity.getBirthDate() != null ? caseFileUserEntity.getBirthDate().toString():null);
        		 reportMap.put(ReportFields.CODICE_FISCALE_RICHIEDENTE_ORGANIZZATORE, caseFileUserEntity.getCf());
        		 reportMap.put(ReportFields.INDIRIZZO_RICHIEDENTE_ORGANIZZATORE, caseFileUserEntity.getResidenza_address());
        		 reportMap.put(ReportFields.CIVICO_RICHIEDENTE_ORGANIZZATORE, caseFileUserEntity.getResidenza_civico());
        		 reportMap.put(ReportFields.COMUNE_RICHIEDENTE_ORGANIZZATORE, caseFileUserEntity.getResidenza_comune());
        		 reportMap.put(ReportFields.PROVINCIA_RICHIEDENTE_ORGANIZZATORE, caseFileUserEntity.getResidenza_provincia());
        		 reportMap.put(ReportFields.CAP_RICHIEDENTE_ORGANIZZATORE, caseFileUserEntity.getResidenza_cap());
        		 reportMap.put(ReportFields.RAGIONE_SOCIALE_RICHIEDENTE_ORGANIZZATORE, caseFileUserEntity.getEnte_ragione_sociale());
        		 reportMap.put(ReportFields.PIVA_SOCIALE_RICHIEDENTE_ORGANIZZATORE, caseFileUserEntity.getPiva());
        		 reportMap.put(ReportFields.EMAIL_SOCIALE_RICHIEDENTE_ORGANIZZATORE, caseFileUserEntity.getEmail());
        		 reportMap.put(ReportFields.TELEFONO_RICHIEDENTE_ORGANIZZATORE, caseFileUserEntity.getTelephoneNumber());
        		 reportMap.put(ReportFields.FLAG_RICHIEDENTE, true);
        		 
        		 if (caseFileUserEntity.getFlag_organizzatore().equals(true)) {
     				
     				reportMap.put(ReportFields.NOME_ORGANIZZATORE, caseFileUserEntity.getNome());
     				reportMap.put(ReportFields.COGNOME_ORGANIZZATORE, caseFileUserEntity.getSurname());
            		 	reportMap.put(ReportFields.BIRTH_PLACE_ORGANIZZATORE, caseFileUserEntity.getBirthPlace());
            		 	reportMap.put(ReportFields.BIRTH_DATE_ORGANIZZATORE, caseFileUserEntity.getBirthDate() != null ? caseFileUserEntity.getBirthDate().toString():null);
            		 	reportMap.put(ReportFields.CODICE_FISCALE_ORGANIZZATORE, caseFileUserEntity.getCf());
            		 	reportMap.put(ReportFields.INDIRIZZO_ORGANIZZATORE, caseFileUserEntity.getResidenza_address());
            		 	reportMap.put(ReportFields.CIVICO_ORGANIZZATORE, caseFileUserEntity.getResidenza_civico());
            		 	reportMap.put(ReportFields.COMUNE_ORGANIZZATORE, caseFileUserEntity.getResidenza_comune());
            		 	reportMap.put(ReportFields.PROVINCIA_ORGANIZZATORE, caseFileUserEntity.getResidenza_provincia());
            		 	reportMap.put(ReportFields.CAP_ORGANIZZATORE, caseFileUserEntity.getResidenza_cap());
            		 	reportMap.put(ReportFields.RAGIONE_SOCIALE_ORGANIZZATORE, caseFileUserEntity.getEnte_ragione_sociale());
            		 	reportMap.put(ReportFields.PIVA_SOCIALE_ORGANIZZATORE, caseFileUserEntity.getPiva());
            		 	reportMap.put(ReportFields.EMAIL_SOCIALE_ORGANIZZATORE, caseFileUserEntity.getEmail());
            		 	reportMap.put(ReportFields.TELEFONO_ORGANIZZATORE, caseFileUserEntity.getTelephoneNumber());
             		 reportMap.put(ReportFields.FLAG_ORGANIZZATORE, true);
     			}
       		 
       		 
      
    			
    			
    			
    			
    			
    		}

		

		
        	 reportMap.put(ReportFields.REPORT_DATAPATH_FIELD_NAME, "/reports/");
			 //reportMap.put(ReportFields.QRCODE, image);
		
		
		
		
		return reportMap;
		
	}
	
	
	default Map<String, Object> CaseFileEntityToMapAutomatic(CaseFileEntity caseFileEntity){
		
		Map<String, Object> reportMap = new HashMap<String, Object>();

		reportMap.put(ReportFields.DETAILS_FIELD_NAME, caseFileEntity.getCausale());

		Set<CaseFileUserEntity> users = caseFileEntity.getUsers();
		
		for (CaseFileUserEntity caseFileUserEntity : users) {
			 if (caseFileUserEntity.getFlag_richiedente().equals(true)) {
  				
  				reportMap.put(ReportFields.OWNER_NAME_REPORT_FIELD_NAME, caseFileUserEntity.getNome());
  				reportMap.put(ReportFields.OWNER_SURNAME_REPORT_FIELD_NAME, caseFileUserEntity.getSurname());
         		 	reportMap.put(ReportFields.OWNER_TAXCODE_REPORT_FIELD_NAME, caseFileUserEntity.getCf());
         		 	reportMap.put(ReportFields.OWNER_ADDRESS_REPORT_FIELD_NAME, caseFileUserEntity.getResidenza_address());
         		 	reportMap.put(ReportFields.OWNER_CITY_REPORT_FIELD_NAME, caseFileUserEntity.getResidenza_comune());
         		 	reportMap.put(ReportFields.OWNER_PROVINCE_REPORT_FIELD_NAME, caseFileUserEntity.getResidenza_provincia());
         		 	reportMap.put(ReportFields.OWNER_CAP_REPORT_FIELD_NAME, caseFileUserEntity.getResidenza_cap());
         		 	reportMap.put(ReportFields.OWNER_EMAIL_REPORT_FIELD_NAME, caseFileUserEntity.getEmail());
         		 	reportMap.put(ReportFields.OWNER_TELEPHONE_REPORT_FIELD_NAME, caseFileUserEntity.getTelephoneNumber());
         		 	
         		 	
  			}else{
				 reportMap.put(ReportFields.DELEGATO_NAME_REPORT_FIELD_NAME, caseFileUserEntity.getNome());
				 reportMap.put(ReportFields.DELEGATO_SURNAME_REPORT_FIELD_NAME, caseFileUserEntity.getSurname());
				 reportMap.put(ReportFields.DELEGATO_TAXCODE_REPORT_FIELD_NAME, caseFileUserEntity.getCf());
				 reportMap.put(ReportFields.DELEGATO_ADDRESS_REPORT_FIELD_NAME, caseFileUserEntity.getResidenza_address());
				 reportMap.put(ReportFields.DELEGATO_CITY_REPORT_FIELD_NAME, caseFileUserEntity.getResidenza_comune());
				 reportMap.put(ReportFields.DELEGATO_PROVINCE_REPORT_FIELD_NAME, caseFileUserEntity.getResidenza_provincia());
				 reportMap.put(ReportFields.DELEGATO_CAP_REPORT_FIELD_NAME, caseFileUserEntity.getResidenza_cap());
				 reportMap.put(ReportFields.DELEGATO_EMAIL_REPORT_FIELD_NAME, caseFileUserEntity.getEmail());
				 reportMap.put(ReportFields.DELEGATO_TELEPHONE_REPORT_FIELD_NAME, caseFileUserEntity.getTelephoneNumber());
			 }
    		 
		}
		


		
		return reportMap;
	}
	
	


}
