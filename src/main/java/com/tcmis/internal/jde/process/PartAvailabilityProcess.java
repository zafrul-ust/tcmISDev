package com.tcmis.internal.jde.process;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.client.ClientConfiguration;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.transport.http.HTTPConduit;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.util.ResourceLibrary;
import com.wesco.iss.model.PartAvailability;
import com.wesco.iss.model.PartAvailabilityList;

public class PartAvailabilityProcess extends BaseProcess {
    
	//to infer whether a number is in the String's starting position.
	private final static Pattern p = Pattern.compile("^\\d+(\\.\\d+)?");
		
    private ResourceLibrary issLibrary;
    
    public PartAvailabilityProcess(String client,String locale) {
          
        super(client,locale);        
        issLibrary = new ResourceLibrary("inventorySearchService");        
    }

    public void getCachedPartsAvailability(String partNumber, HttpServletRequest request) {
        
        String apiUrl = "/ils/partAvail/cache/"+partNumber;        
        makeWebServiceCall(request, apiUrl);        
    }

    public void getIlsPartsAvailability(String partNumber, HttpServletRequest request) {
        
        String apiUrl = "/ils/partAvail/"+partNumber;        
        makeWebServiceCall(request, apiUrl);               
    }

	private void makeWebServiceCall(HttpServletRequest request, String apiUrl) {
		
		WebClient webClient = null;
        try {
            webClient = getWebClient();            
            webClient.path( apiUrl );
            
            Response r = webClient.get();
            if (Response.Status.OK.getStatusCode() == r.getStatus()){
                
                PartAvailabilityList m = r.readEntity(PartAvailabilityList.class);
                
                List<PartAvailability> PartsAvailabilityList = m.getList();
                if (!PartsAvailabilityList.isEmpty()){
                
	                request.setAttribute("updateDate", PartsAvailabilityList.get(0).getLastUpdate());
	                request.setAttribute("partNum", PartsAvailabilityList.get(0).getWescoPartNumber());	                	                
                }
                // sort by quantity
                Collections.sort(PartsAvailabilityList, ByQuantity);
                request.setAttribute("partsAvailabilityList", PartsAvailabilityList);
            }
            else if (Response.Status.NO_CONTENT.getStatusCode() == r.getStatus()){

            	request.setAttribute("tcmISError", r.getHeaderString("exception"));
            }
            else{
                String msg = r.getHeaderString("exception"); 
                if (msg == null)
                    msg = "Invalid Request";
                    
                request.setAttribute("tcmISError", msg);
            }
            
        } catch(Exception e) {
        	request.setAttribute("tcmISError", e.getMessage());
        }finally {        
            if (null != webClient)
                webClient.close();
        }
	}

    private WebClient getWebClient(){
        
        final List<Object> providers2 = new ArrayList<Object>();
        // needed to unmarshal json replies
        providers2.add(new JacksonJsonProvider());
        
        String rsServerURL = issLibrary.getString("iss.server.url");
        
        WebClient webClient = WebClient.create(rsServerURL, providers2);
        webClient.accept( "application/json" );
                
        ClientConfiguration config = WebClient.getConfig(webClient);
        // manipulate the receive and connection time-out of a remote service call:
        HTTPConduit conduit = config.getHttpConduit();
        // in millseconds
        conduit.getClient().setReceiveTimeout(90000L);
        conduit.getClient().setConnectionTimeout(90000L);
        
        return webClient;
    }
    
    private List<String> accreditedVendorLevelOrder = // define your custom order
    	    Arrays.asList("AVP", "AVG", "AVS", "", null);
    
    private Comparator<PartAvailability> ByQuantity = new Comparator<PartAvailability>() {
		
	    public int compare(PartAvailability obj1, PartAvailability obj2) {
	    	
	    	int r = compareAccreditedVendorLevel(obj1.getAccreditedVendorLevel(), obj2.getAccreditedVendorLevel());
	    	if (r != 0)
	    		return r;
	    	
	    	String str1 = obj1.getQuantity().trim();
	    	String str2 = obj2.getQuantity().trim();
	    	
	        Matcher m = p.matcher(str1);
	        //Integer number1 = null;
	        Double number1 = null;
	        if (!m.matches()) {//find()) {//the first string has no numbers
	            return str1.compareTo(str2);// lexicographic comparison
	        }
	        
        	Double number2 = null;
            number1 = Double.parseDouble(str1);	            
            m = p.matcher(str2);
            if (!m.matches()) {//find()) {// the second string has no numbers
                return (str1.compareTo(str2));// lexicographic comparison
            }
            
            // both strings are numbers            
        	number2 = Double.parseDouble(str2);
            int comparison = number1.compareTo(number2)*(-1);// reverse order
            if (comparison != 0) {
                return comparison;
            }
            
            // the two numbers are the same
            return str1.compareTo(str2);// lexicographic comparison                        	        
	    }

		private int compareAccreditedVendorLevel(String accreditedVendorLevel, String accreditedVendorLevel2) {
			
			return Integer.valueOf(accreditedVendorLevelOrder.indexOf(accreditedVendorLevel)).compareTo(
					accreditedVendorLevelOrder.indexOf(accreditedVendorLevel2));
			
		}
	};
}
