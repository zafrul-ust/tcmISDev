package com.tcmis.batch.processdocuments;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.admin.process.MailProcess;

public class ProcessDocumentsCommand
{
    static Log log;
    
    public static void main(final String[] array) throws Exception {
        try {
            new ProcessDocumentsProcess("TCM_OPS").processDoc();
        }
        catch (Exception ex) {
            logInitializationError(ex);
        }
    }
    
    protected static void logInitializationError(final Exception ex) throws Exception {
        ProcessDocumentsCommand.log.error((Object)"Receipt doc process error", (Throwable)ex);
        MailProcess.sendEmail("deverror@tcmis.com", (String)null, "", "Receipt doc process error", "See log file." + ex.getMessage());
    }
    
    static {
        ProcessDocumentsCommand.log = LogFactory.getLog((Class)ProcessDocumentsCommand.class);
    }
}
