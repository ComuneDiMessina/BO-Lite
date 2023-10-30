package it.almaviva.impleme.bolite.core.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import it.almaviva.impleme.bolite.core.IReportService;
import it.almaviva.impleme.bolite.core.mappers.IReportMapper;
import it.almaviva.impleme.bolite.domain.dto.report.DuringDTO;
import it.almaviva.impleme.bolite.domain.dto.report.ReportDTO;
import it.almaviva.impleme.bolite.integration.entities.booking.BookingEntity;
import it.almaviva.impleme.bolite.integration.entities.casefile.CaseFileEntity;
import it.almaviva.impleme.bolite.integration.repositories.casefile.ICaseFileRepository;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JsonDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
@Transactional
public class ReportService implements IReportService {
	
	@Autowired
	private IReportMapper reportMapper;
	
	@Autowired
	private ICaseFileRepository iCaseFileRepository;
	
	
    @Override
    @SneakyThrows
    @Transactional
    public void generateReport(UUID codice, String pdf_template, String tempDir){
    	
    	
    	
    	
    	if(pdf_template.equals("roomBooking")) {
    		 ObjectMapper objectMapper = new ObjectMapper();
    	        objectMapper.registerModule(new JavaTimeModule());
    	        //objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    	        
    	    	CaseFileEntity caseFileEntity = iCaseFileRepository.findByCodice(codice).get();
    	    	
    	    	//String testBooking = "{\"during\":[{\"start\":\"2020-11-08 08:00:00\",\"end\":\"2020-11-08 08:30:00\"},{\"start\":\"2020-11-09 10:00:00\",\"end\":\"2020-11-09 10:30:00\"},{\"start\":\"2020-11-10 11:00:00\",\"end\":\"2020-11-10 11:30:00\"}]}";
    			
    	    	
    	    	//GENERAZIONE QRCODE
//    	        QRCodeWriter qrCodeWriter = new QRCodeWriter();
//
//    	        BitMatrix bitMatrix = qrCodeWriter.encode(codice.toString(), BarcodeFormat.QR_CODE, 200, 200);
//
//    	        BufferedImage image = MatrixToImageWriter.toBufferedImage(bitMatrix);
//    	        
//    	        ByteArrayOutputStream out = new ByteArrayOutputStream();
//    	        ImageIO.write(image, "PNG", out);
//    	        byte[] bytes = out.toByteArray();
//
//    	        String base64bytes = Base64.getEncoder().encodeToString(bytes);
//    	        
//    	        System.out.println("base64 qrcode -> "+ base64bytes);
//    	        
//    	        caseFileEntity.setQrcode(base64bytes);
    	        
    	        ReportDTO reportDTO = new ReportDTO();
    	        
    	        DuringDTO duringDTO = new DuringDTO();
    	        
    	       ArrayList<DuringDTO> arrayList = new ArrayList<DuringDTO>();

				final BookingEntity booking = caseFileEntity.getBooking();
				final Boolean isWeek = booking.getFlagWeek();

				if(!isWeek) {
					duringDTO.setStart(booking.getBookingStartDate() + " " + caseFileEntity.getBooking().getBookingStartHour());
					duringDTO.setEnd(booking.getBookingEndDate() + " " + caseFileEntity.getBooking().getBookingEndHour());
				}else{
					duringDTO.setStart(booking.getBookingStartDate()+" "+ LocalTime.of(0,0));
					duringDTO.setEnd(booking.getBookingEndDate()+" "+ LocalTime.of(0,0));
				}
    	        arrayList.add(duringDTO);
    	        
    	        reportDTO.setDuring(arrayList);
    	        
    	        String testDuring = objectMapper.writeValueAsString(reportDTO);

    	        
    	        //FIXME
    	        Map<String, Object> params = reportMapper.CaseFileEntityToMap(caseFileEntity);

    	        try (InputStream jasperStream = this.getClass().getResourceAsStream("/reports/"+pdf_template+".jasper")) {

    	            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperStream);

    	            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, new JsonDataSource(new ByteArrayInputStream(testDuring.getBytes())));//FIXME
    	            ByteArrayOutputStream baos = new ByteArrayOutputStream();
    	            JasperExportManager.exportReportToPdfStream(jasperPrint, baos);

    	            String fileName = "CaseFile_" + codice.toString()+ "_" + System.currentTimeMillis() + ".pdf"+"d=Domanda Prenotazione";

    	            String tempValue = tempDir;
    	            tempValue = !tempValue.endsWith("/") ? tempValue + "/" : tempValue;

    	            if (Files.exists(Paths.get(tempValue)))
    	                JasperExportManager.exportReportToPdfFile(jasperPrint, tempValue + fileName);
    	            

    	        } catch (Exception e) {
    	            log.error("Create PDF error " + e.getMessage());
    	            throw e;
    	        }
    	}else {
    		
    		CaseFileEntity caseFileEntity = iCaseFileRepository.findByCodice(codice).get();
    		
    		//FIXME
	        Map<String, Object> params = reportMapper.CaseFileEntityToMapAutomatic(caseFileEntity);
    		
    		   try (InputStream jasperStream = this.getClass().getResourceAsStream("/reports/"+pdf_template+".jasper")) {

    	            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperStream);

    	            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, new JREmptyDataSource());//FIXME
    	            ByteArrayOutputStream baos = new ByteArrayOutputStream();
    	            JasperExportManager.exportReportToPdfStream(jasperPrint, baos);

    	            String fileName = "CaseFile_" + codice.toString()+ "_" + System.currentTimeMillis() + ".pdf"+"d=Pratica";

    	            String tempValue = tempDir;
    	            tempValue = !tempValue.endsWith("/") ? tempValue + "/" : tempValue;

    	            if (Files.exists(Paths.get(tempValue)))
    	                JasperExportManager.exportReportToPdfFile(jasperPrint, tempValue + fileName);


    	        } catch (Exception e) {
    	            log.error("Create PDF error " + e.getMessage());
    	            throw e;
    	        }

    		
    	}
       
    }
}
