package it.almaviva.impleme.bolite.core;

import java.io.IOException;

public interface IDocumentaleService{

    public String uploadDocument(String base64, String filename) throws IOException;
    
    public byte[] getContent(String id);
    
    
}