package it.almaviva.impleme.bolite.core.impl;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import it.almaviva.impleme.bolite.core.IQrcodeService;
import it.almaviva.impleme.bolite.integration.entities.casefile.CaseFileEntity;
import it.almaviva.impleme.bolite.integration.repositories.casefile.ICaseFileRepository;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.UUID;

@Slf4j
@Service
@Transactional
public class QrcodeService implements IQrcodeService{
	
	@Autowired
	private ICaseFileRepository iCaseFileRepository;

	@Override
	@SneakyThrows
	public void generateQrcode(UUID codice) {
		// TODO Auto-generated method stub
		CaseFileEntity caseFileEntity = iCaseFileRepository.findByCodice(codice).get();
		
        QRCodeWriter qrCodeWriter = new QRCodeWriter();

        BitMatrix bitMatrix;
		try {
			bitMatrix = qrCodeWriter.encode(codice.toString(), BarcodeFormat.QR_CODE, 200, 200);
			
			BufferedImage image = MatrixToImageWriter.toBufferedImage(bitMatrix);
	        
	        ByteArrayOutputStream out = new ByteArrayOutputStream();
	        ImageIO.write(image, "PNG", out);
	        byte[] bytes = out.toByteArray();

	        String base64bytes = Base64.getEncoder().encodeToString(bytes);

	        caseFileEntity.setQrcode(base64bytes);
	        iCaseFileRepository.save(caseFileEntity);
		} catch (WriterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        
        
     
	} 

}
