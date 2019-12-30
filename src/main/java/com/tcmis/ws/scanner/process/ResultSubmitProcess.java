package com.tcmis.ws.scanner.process;

/**
 * Created by IntelliJ IDEA.
 * User: mathias.wennberg
 * Date: Sep 12, 2008
 * Time: 2:48:59 PM
 * To change this template use File | Settings | File Templates.
 */

import java.io.File;


import java.math.BigDecimal;
import java.sql.Connection;
import java.util.Vector;

import org.apache.commons.digester.Digester;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.db.SqlManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.GenericWebServiceProcess;
import com.tcmis.common.util.FileHandler;
import com.tcmis.common.util.ResourceLibrary;

import com.tcmis.ws.scanner.beans.ScanDataBean;
import com.tcmis.ws.scanner.beans.ScanDataList;

public class ResultSubmitProcess extends GenericWebServiceProcess {
    Log log = LogFactory.getLog(this.getClass());
    private ResourceLibrary library = null;
    private String url = null;
    static boolean ignoreTokenError = false; // should load this from localconfig.

    static {
        try {
            ResourceLibrary lconfig = new ResourceLibrary("localconfig");
            if (lconfig != null) {
                if ("IgnoreTokenError".equals(lconfig.getString("IgnoreTokenError")))
                    ignoreTokenError = true;
            }
        } catch (Exception ex) {
        }
    }

    public ResultSubmitProcess(String client, String url) throws BaseException {
        super(client);
        library = new ResourceLibrary("scannerupload");
        this.url = url;
    }

    public ScanDataList parseResultSubmit(File file) throws BaseException {
        ScanDataList orderBean = null;

        try {
            Digester digester = new Digester();
            digester.addObjectCreate("ResultSubmitRequest", ScanDataList.class);

            digester.addCallMethod("ResultSubmitRequest/PersonnelId", "setPersonnelIdStr", 0);
            digester.addCallMethod("ResultSubmitRequest/Token", "setToken", 0);

            digester.addObjectCreate("ResultSubmitRequest/Scans/Scan", ScanDataBean.class);
            digester.addSetNext("ResultSubmitRequest/Scans/Scan", "addScanDataBean");
            digester.addCallMethod("ResultSubmitRequest/Scans/Scan/BinId", "setBinIdStr", 0);
            digester.addCallMethod("ResultSubmitRequest/Scans/Scan/CompanyId", "setCompanyId", 0);
            digester.addCallMethod("ResultSubmitRequest/Scans/Scan/ScanDate", "setCountDatetimeStr", 0);
            digester.addCallMethod("ResultSubmitRequest/Scans/Scan/CountQuantity", "setCountQuantityStr", 0);
            digester.addCallMethod("ResultSubmitRequest/Scans/Scan/CountType", "setCountType", 0);
            digester.addCallMethod("ResultSubmitRequest/Scans/Scan/PersonnelId", "setPersonnelIdStr", 0);
            digester.addCallMethod("ResultSubmitRequest/Scans/Scan/ReceiptId", "setReceiptIdStr", 0);
            digester.addCallMethod("ResultSubmitRequest/Scans/Scan/ContainerNumber", "setContainerNumberStr", 0);
            digester.addCallMethod("ResultSubmitRequest/Scans/Scan/ContainerStatus", "setContainerStatus", 0);
            digester.addCallMethod("ResultSubmitRequest/Scans/Scan/ContainerCondition", "setContainerCondition", 0);

            digester.parse(file);
            orderBean = (ScanDataList) digester.getRoot();
        } catch (Exception e) {
            System.out.println("Error:" + e.getMessage());
            log.error("Error:" + e.getMessage());
            e.printStackTrace(System.out);
            BaseException be = new BaseException(e);
            be.setMessageKey("");
            be.setRootCause(e);
            throw be;
        }
        return orderBean;
    }

    public String processResultSubmit(String Company, String xmlString) throws BaseException {
        String status = "Failed";
        String reason = "System failed to save backup file";
        try {
            String dirname = library.getString("upload.backupdir");
            File orderFile = FileHandler.saveTempFile(xmlString, "ResultSubmit_" + getClient() + "_", ".xml", dirname);
            CabinetCountProcess.sendErrorEmailAttach("ScanUpload", "data backup file", orderFile.getAbsolutePath());

            reason = "Failed to parse Scan Data.";
            //parse data
            ScanDataList orilist = parseResultSubmit(orderFile);
            Vector<ScanDataBean> scanData = orilist.getScanData();
            Vector<ScanDataList> batches = new Vector();
            Vector<ScanDataBean> curBatch = null;
            BigDecimal pre = null;
            //separate data by person
            //all data for a given person is consider as 1 batch of upload
            for (ScanDataBean sbean : scanData) {
                BigDecimal cur = sbean.getPersonnelId();
                if (cur == null) continue;
                if (!cur.equals(pre)) {
                    ScanDataList curList = new ScanDataList();
                    curList.setToken(orilist.getToken());
                    curBatch = new Vector();
                    curList.setPersonnelId(cur);
                    curList.setScanData(curBatch);
                    batches.add(curList);
                }
                curBatch.add(sbean);
                pre = cur;
            }

            if (batches.size() == 0) {
                reason = "No Scan Data.";
                throw new Exception("");
            }
            status = "TokenFailure";
            reason = "Invalid token. Please sign in again.";
            boolean hastoken = false;
            for (ScanDataList list : batches) {
                String logonId = this.factory.selectSingleValue("select logon_id from personnel where personnel_id = " + list.getPersonnelId());
                String query = null;
                String s = "";
                try {
                    s = this.factory.getFunctionValue("Pkg_token.fx_verify_tcmis_token",
                            logonId,
                            Company,
                            list.getToken()
                    );
                } catch (Exception exxxx) {
                    log.debug(exxxx.getMessage());
                    exxxx.printStackTrace();
                }
                if (!"OK".equals(s)) continue;
                hastoken = true;
                break;
            }

            if (ignoreTokenError) hastoken = true;
            if (!hastoken) throw new Exception("");

            BigDecimal nextupLoadSeq = null;
            Vector<BigDecimal> seqs = new Vector();
            status = "Failed";
            reason = "Database Error.";
            for (ScanDataList list : batches) {
                try {
                    nextupLoadSeq = getNextUpLoadSeq();
                } catch (Exception ee) {}

                if (nextupLoadSeq == null) throw new Exception("");
                seqs.add(nextupLoadSeq);
            }
            int i = 0;
            for (ScanDataList list : batches) {
                nextupLoadSeq = seqs.get(i++);
                CabinetCountWorkerProcess p = new CabinetCountWorkerProcess(list, list.getPersonnelId(), nextupLoadSeq, getClient(), url);
                Thread thread = new Thread(p);
                thread.start();
            } // for

        } catch (Exception ex) {
            StringBuilder sw = new StringBuilder("<?xml version=\"1.0\" encoding=\"utf-8\"?><ResultSubmitResponse xmlns=\"http://beans.scanner.ws.tcmis.com/xsd\"><Status><Result>" + status + "</Result><Message>" + reason + "</Message></Status></ResultSubmitResponse>");
            return sw.toString();
        }
        status = "Success";
        reason = "Upload Successful.";
        StringBuilder sw = new StringBuilder("<?xml version=\"1.0\" encoding=\"utf-8\"?><ResultSubmitResponse xmlns=\"http://beans.scanner.ws.tcmis.com/xsd\"><Status><Result>" + status + "</Result><Message>" + reason + "</Message></Status></ResultSubmitResponse>");
        return sw.toString();

    }

    private BigDecimal getNextUpLoadSeq() {
        BigDecimal b = null;
        Connection connection = null;
        try {
            connection = dbManager.getConnection();
            b = new BigDecimal("" + new SqlManager().getOracleSequence(connection, "upload_sequence"));
        }catch (Exception ex) {
        }finally {
            try {
                dbManager.returnConnection(connection);
            }catch (Exception ee) {}
            connection = null;
        }
        return b;
    }

    public String manualProcessResultSubmit(String xmlString) throws BaseException {
        String status = "Failed";
        String reason = "System failed to save backup file";
        try {
            File orderFile = new File(xmlString);

            reason = "Failed to parse Scan Data.";
            //parse data
            ScanDataList orilist = parseResultSubmit(orderFile);
            Vector<ScanDataBean> scanData = orilist.getScanData();
            Vector<ScanDataList> batches = new Vector();
            Vector<ScanDataBean> curBatch = null;
            BigDecimal pre = null;
            //separate data by person
            //all data for a given person is consider as 1 batch of upload
            for (ScanDataBean sbean : scanData) {
                BigDecimal cur = sbean.getPersonnelId();
                if (cur == null) continue;
                if (!cur.equals(pre)) {
                    ScanDataList curList = new ScanDataList();
                    curList.setToken(orilist.getToken());
                    curBatch = new Vector();
                    curList.setPersonnelId(cur);
                    curList.setScanData(curBatch);
                    batches.add(curList);
                }
                curBatch.add(sbean);
                pre = cur;
            }

            if (batches.size() == 0) {
                reason = "No Scan Data.";
                throw new Exception("");
            }

            BigDecimal nextupLoadSeq = null;
            Vector<BigDecimal> seqs = new Vector();
            status = "Failed";
            reason = "Database Error.";
            for (ScanDataList list : batches) {
                try {
                    nextupLoadSeq = getNextUpLoadSeq();
                } catch (Exception ee) {}

                if (nextupLoadSeq == null) throw new Exception("");
                seqs.add(nextupLoadSeq);
            }
            int i = 0;
            for (ScanDataList list : batches) {
                nextupLoadSeq = seqs.get(i++);
                CabinetCountWorkerProcess p = new CabinetCountWorkerProcess(list, list.getPersonnelId(), nextupLoadSeq, getClient(), url);
                Thread thread = new Thread(p);
                thread.start();
            }

        } catch (Exception ex) {
            StringBuilder sw = new StringBuilder("<?xml version=\"1.0\" encoding=\"utf-8\"?><ResultSubmitResponse xmlns=\"http://beans.scanner.ws.tcmis.com/xsd\"><Status><Result>" + status + "</Result><Message>" + reason + "</Message></Status></ResultSubmitResponse>");
            return sw.toString();
        }
        status = "Success";
        reason = "Upload Successful.";
        StringBuilder sw = new StringBuilder("<?xml version=\"1.0\" encoding=\"utf-8\"?><ResultSubmitResponse xmlns=\"http://beans.scanner.ws.tcmis.com/xsd\"><Status><Result>" + status + "</Result><Message>" + reason + "</Message></Status></ResultSubmitResponse>");
        return sw.toString();

    }

    public static void main(String[] argv) {
        try {
            ResultSubmitProcess process = new ResultSubmitProcess("GKN", "https://www.tcmis.com/tcmIS/gkn/customermrtracking.do?");
            String filePath = "C:\\GeneratedJava\\ScannerUpload\\ResultSubmit_119628.xml";
            String xmlString = GenericWebServiceProcess.readFileAsString(filePath);
            String result = process.processResultSubmit("GKN", xmlString);
            System.out.println("result:" + result);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}

