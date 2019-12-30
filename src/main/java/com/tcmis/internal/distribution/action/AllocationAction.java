package com.tcmis.internal.distribution.action;

import java.math.BigDecimal;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.admin.beans.PermissionBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.distribution.beans.AllocationDetailBean;
import com.tcmis.internal.distribution.beans.AllocationInputBean;
import com.tcmis.internal.distribution.beans.ItemUomBean;
import com.tcmis.internal.distribution.process.AllocationDetailProcess;


  public class AllocationAction
      extends TcmISBaseAction {
    public ActionForward executeAction(ActionMapping mapping, ActionForm form,
                                       HttpServletRequest request,
                                       HttpServletResponse response) throws BaseException, Exception {
        String source = "allocation";
      	if (!this.checkLoggedIn( source ) ) return mapping.findForward("login");
      	request.setAttribute("source", source );
        /*Since there is no search section we consider this the start time. This should be done only for
        * pages that have no search section.*/
        request.setAttribute("startSearchTime", new Date().getTime() );
        String forward = "success";
        String url = request.getQueryString();
        PersonnelBean personnelBean = (PersonnelBean) this.getSessionObject(request, "personnelBean");
        BigDecimal personnelId = new BigDecimal(personnelBean.getPersonnelId());

        AllocationInputBean in = new AllocationInputBean();
        BeanHandler.copyAttributes(form, in,this.getTcmISLocale());
        
        String queryString = request.getQueryString();
   
        if(queryString != null) {
	        String[] queryArr = queryString.split("&");
	        for(String param : queryArr) {
	            if(param.startsWith("needDateString="))
	            {
	                try {// This is for China project
	                	String passedDateString = URLDecoder.decode(param.substring(param.indexOf("=")+1), "UTF-8");
	                	in.setNeedDate(new SimpleDateFormat("dd-MMM-yyyy", this.getTcmISLocale()).parse(passedDateString));
	                }
	                catch(Exception ex) {
	                    ex.printStackTrace();
	                }
	            }
	            
	            if(param.startsWith("partDesc="))
	            {
	                try {// This is for China project
	                	in.setPartDesc(URLDecoder.decode(param.substring(param.indexOf("=")+1), "UTF-8"));
	                }
	                catch(Exception ex) {
	                    ex.printStackTrace();
	                }
	            }
	        }
        }
        request.setAttribute("needDate", in.getNeedDate());
        request.setAttribute("partDesc", in.getPartDesc());

        AllocationDetailProcess process = new AllocationDetailProcess(this);

        String action = request.getParameter("uAction");
        
        if( "IG".equals(in.getSearchKey())||
             "REGION".equals(in.getSearchKey())||
             "GLOBAL".equals(in.getSearchKey())) {
//        	action="search";
        }
        if ( "autoAllocate".equals(action)) {
        	process.allocateLineDirect(in);
        	in.setSearchKey("IG");
        }
        else if (action != null && "forceBuy".equals(action)) {
            this.checkToken(request);
		//	java.util.Enumeration<String> e = request.getParameterNames();
			
		//	Vector<String> v = new Vector();
		//	while(e.hasMoreElements()) 
		//		v.add(e.nextElement());
		//	Collections.sort(v);
		//	for(String ss:v) {
		//		System.out.println("Name:"+ss+":value:"+request.getParameter(ss));
		//	}
		    String ErrorMsg = "";
		    Collection<AllocationDetailBean> beans = BeanHandler.getBeans((DynaBean)form,"AllocationBean",new AllocationDetailBean(),this.getTcmISLocale());
		    PermissionBean perbean = personnelBean.getPermissionBean();

		    ErrorMsg = process.newProcessData(beans,in,personnelId,perbean,personnelBean);
            process.processForceBuyData(beans,in,personnelId,perbean,personnelBean);
		    process.allocateLineDirect(in);

            /*process.deleteStage();
		    for(AllocationDetailBean bean:beans) {
		  	    if( perbean.hasInventoryGroupPermission("GenerateOrders", bean.getInventoryGroup(), null, null) )
		    	process.allocateLine(bean, in, personnelBean);
		    }
		    ErrorMsg = process.processData(personnelId,in);*/
		   request.setAttribute("tcmISError", ErrorMsg);
		    request.setAttribute("updateComplete", "forceBuyOrder");
		}
        else if (action != null && "update".equals(action)) {
                    this.checkToken(request);
//			java.util.Enumeration<String> e = request.getParameterNames();
			
//			Vector<String> v = new Vector();
//			while(e.hasMoreElements()) 
//				v.add(e.nextElement());
//			Collections.sort(v);
//			for(String ss:v) {
//				System.out.println("Name:"+ss+":value:"+request.getParameter(ss));
//			}
            String ErrorMsg = "";
            Collection<AllocationDetailBean> beans = BeanHandler.getBeans((DynaBean)form,"AllocationBean",new AllocationDetailBean(),this.getTcmISLocale());
            PermissionBean perbean = personnelBean.getPermissionBean();
            ErrorMsg = process.newProcessData(beans,in,personnelId,perbean,personnelBean);
            /*process.deleteStage();
            for(AllocationDetailBean bean:beans) {
          	    if( perbean.hasInventoryGroupPermission("GenerateOrders", bean.getInventoryGroup(), null, null) )
            	process.allocateLine(bean, in, personnelBean);
            }
            ErrorMsg = process.processData(personnelId,in);*/
           request.setAttribute("tcmISError", ErrorMsg);
            request.setAttribute("updateComplete", "updateComplete");
        }
        if( "MV".equalsIgnoreCase(in.getItemType() ) ) {
        		request.setAttribute("uosColl",process.getSearchResult("select * from item_uom where item_id = {0}", new ItemUomBean(),in.getItemId()));
//        	Vector coll = process.getSearchResult("select PURCHASING_UNIT_OF_MEASURE from purchasing_uom where item_id = {0} and inventory_group = {1}", new ItemUomBean(),in.getItemId(),in.getInventoryGroup());
//        	if( coll == null || coll.size() == 0 ) {
//        		coll = process.getSearchResult("select PURCHASING_UNIT_OF_MEASURE from purchasing_uom where item_id = {0} and inventory_group = {1}", new ItemUomBean(),in.getItemId(),"All");
//        	}
//        	String uos = "";
//        	if( coll != null && coll.size() !=0 )
//        		uos = ((ItemUomBean)coll.get(0)).getPurchasingUnitOfMeasure();
//        	in.setUnitOfMeasure(uos);
    	}
        //refreshUOS is the same as search.
    	Object[] results = process.showResult(in,personnelBean);		
		request.setAttribute("beanColl", results[0]);
		request.setAttribute("rowCountPart", results[1]);

        this.saveTcmISToken(request);

        request.setAttribute("endSearchTime", new Date().getTime() );
        return (mapping.findForward(forward));
      }
 }