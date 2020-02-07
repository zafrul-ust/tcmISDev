package com.tcmis.client.api.process;

import com.tcmis.common.db.DbManager;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import org.json.JSONObject;
import com.tcmis.common.util.SqlHandler;
import com.tcmis.common.util.StringHandler;

public class LaunchTcmisProcess extends BaseProcess {
    private GenericSqlFactory factory = null;

    public LaunchTcmisProcess(String client) {
        super(client);
        factory = new GenericSqlFactory(new DbManager(getClient()));
    }

    public LaunchTcmisProcess() {}

    public String getPayloadIdCount(String payloadId, String logonId)  {
        String result = "";
        try {
            result = factory.selectSingleValue("select count(*) from punchout_session where payload_id = '" + payloadId + "' and user_id = '"+logonId+"'");
        } catch(Exception ex) { log.error("Get Payload ID count",ex); }
        return result;
    }

    public String setupLaunchData(JSONObject input, String baseUrl) {
        StringBuilder result =  new StringBuilder(baseUrl);
        try {
            String logonId = input.getJSONObject("Request").getJSONObject("PunchOutSetupRequest").getJSONObject("ShipTo").getJSONObject("Address").getString("addressID");
            //first check to make sure user is defined in our system
            StringBuilder query = new StringBuilder("select personnel_id from company_application_logon where company_application_logon_id = ").append(SqlHandler.delimitString(logonId));
            String personnelId = factory.selectSingleValue(query.toString());
            if (!StringHandler.isBlankString(personnelId)) {
                //insert data into punchout_session
                query = new StringBuilder("insert into punchout_session (company_id,session_id,payload_id,user_id,logged,mr_number,oracle)");
                query.append(" select company_id,null,").append(SqlHandler.delimitString(input.getString("payloadID")));
                query.append(",logon_id,'Y',null,'N' from personnel where personnel_id = ").append(personnelId);
                factory.deleteInsertUpdate(query.toString());
                //insert data into punchout_setup_request
                query = new StringBuilder("insert into punchout_setup_request (company_id,payload_id,operation,buyer_cookie,browser_form_post)");
                query.append(" select company_id,").append(SqlHandler.delimitString(input.getString("payloadID")));
                query.append(",'create',null,'").append(input.getJSONObject("Request").getJSONObject("PunchOutSetupRequest").getJSONObject("BrowserFormPost").get("URL"));
                query.append("' from personnel where personnel_id = ").append(personnelId);
                factory.deleteInsertUpdate(query.toString());
                //get tcmIS logonID
                query = new StringBuilder("select logon_id from personnel where personnel_id = ").append(personnelId);
                String tcmISLogonId = factory.selectSingleValue(query.toString());
                //return url
                result.append(getTcmisLaunchURL(input.getString("payloadID"),tcmISLogonId,input.getString("timestamp"),input.getString("lang"),input.getJSONObject("Request").getJSONObject("PunchOutSetupRequest").getString("BuyerCookie")));
            }else
                result = new StringBuilder("");
        }catch (Exception e) {
            log.error("Setup Launch data",e);
            result = new StringBuilder("");
        }
        return result.toString().replace("http:","https:");
    }

    public String getTcmisLaunchURL(String payloadId, String tcmisLogonId, String timeStamp, String language, String browserCookie) {
        StringBuilder url = new StringBuilder("");
        url.append("/launchtcmis.do?payloadId=").append(payloadId);
        url.append("&logonId=").append(tcmisLogonId).append("&ecommerceSource=ARIBA");
        url.append("&timestamp=").append(timeStamp);
        url.append("&buyerCookie=").append(browserCookie);
        url.append("&lang=").append(language);
        return url.toString();
    }

}