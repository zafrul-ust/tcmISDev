package com.tcmis.client.api.process;

import com.tcmis.client.catalog.beans.CatalogInputBean;
import com.tcmis.client.common.beans.ShoppingCartBean;
import com.tcmis.common.admin.process.MailProcess;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.NetHandler;
import com.tcmis.common.util.ResourceLibrary;
import org.json.JSONObject;
import com.tcmis.common.util.SqlHandler;
import com.tcmis.common.util.StringHandler;

import java.util.Collection;

public class LaunchTcmisProcess extends BaseProcess {
  private GenericSqlFactory factory = null;

  public LaunchTcmisProcess(String client) {
      super(client);
      factory = new GenericSqlFactory(new DbManager(getClient()));
  }

  public String getPayloadIdCount(String payloadId, String logonId)  {
      String result = "";
      try {
        result = factory.selectSingleValue("select count(*) from punchout_session where payload_id = '" + payloadId + "' and user_id = '"+logonId+"'");
      } catch(Exception ex) { ex.printStackTrace() ; }
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
            result.append("/launchtcmis.do?payloadId=").append(input.getString("payloadID"));
            result.append("&logonId=").append(tcmISLogonId).append("&ecommerceSource=ARIBA");
            result.append("&timestamp=").append(input.getString("timestamp"));
            result.append("&lang=").append(input.getString("lang"));
        }else
            result = new StringBuilder("");
      }catch (Exception e) {
        e.printStackTrace();
        result = new StringBuilder("");
      }
      return result.toString();
  }
 
}