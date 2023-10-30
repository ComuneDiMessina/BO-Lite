package it.almaviva.impleme.bolite.core;

import java.util.UUID;

public interface IReportService{
    /**
     *
     * @param codice
     * @param pdf_template
     * @param tempDir
     * @throws Exception
     */
    void generateReport(UUID codice, String pdf_template, String tempDir);
}