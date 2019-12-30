package com.tcmis.supplier.xbuy.airgas.action;

/**
 * Created by IntelliJ IDEA.
 * User: mathias.wennberg
 * Date: Feb 12, 2008
 * Time: 5:04:03 PM
 * To change this template use File | Settings | File Templates.
 */
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.PrintWriter;
import java.util.Vector;

import com.tcmis.supplier.xbuy.airgas.beans.*;
import com.tcmis.supplier.xbuy.airgas.process.AirgasURLProcess;
/******************************************************************************
 * Controller for FreightAdvice
 * @version 1.0
	******************************************************************************/

public class StockQueryAction
 extends TcmISBaseAction {
 public ActionForward executeAction(ActionMapping mapping,
	ActionForm form,
	HttpServletRequest request,
	HttpServletResponse response) throws
	BaseException, Exception {

	 AirgasURLProcess process = new AirgasURLProcess(this.getDbUser(request));
	 StockQueryInputBean inputBean = new StockQueryInputBean();
	 BeanHandler.copyAttributes(form, inputBean);

	 String result="-1";
	 this.setHtmlHeader(response);
	 PrintWriter writer = response.getWriter(); 
	 try {
		 Vector<StockQueryResultViewBean> v = (Vector<StockQueryResultViewBean>) process.StockQuery(inputBean);
		 if( v.size() > 0 ) {
			 result = "";
			 String prepend = "";
			 for( int i = 0; i < v.size(); i++ ) {
				 result += prepend + v.get(i).getQtyAvailable();
				 prepend = ",";
			 }
		 }
	 } catch (Exception ex) { ex.printStackTrace(); result="-1";}
	 //writer.println(result);
	 writer.write(result);
	 writer.flush();
	 writer.close();
	 System.out.println("Done action:"+result);
	 return noForward;
 }
}

