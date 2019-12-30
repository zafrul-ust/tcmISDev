package com.tcmis.client.report.action;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.framework.TcmISBaseAction;


public class GetDayOfYearAction extends TcmISBaseAction {

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		
        String inputDate = (String)request.getParameter("inputdate");
        Locale lcl = getTcmISLocale(request);
        String dateMask = "dd-MMM-yyyy";
        SimpleDateFormat dateParser = (lcl == null) ? new SimpleDateFormat(dateMask) : new SimpleDateFormat(dateMask, lcl);
        Date returnValue = dateParser.parse(inputDate);
        
        String result = "";
        SimpleDateFormat sdf = new SimpleDateFormat("z");
        result = sdf.format(returnValue);
        
        Calendar cal = Calendar.getInstance();
        cal.setTime(returnValue);

        int numOfDays = cal.get(Calendar.DAY_OF_YEAR); 
         
        result = numOfDays + "";
        // Write out the data
        response.setCharacterEncoding("utf-8");
        //response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        //set the modified date in the text field. 
    	out.println(result);   		
		out.close();
		return noForward;	
	}

}
