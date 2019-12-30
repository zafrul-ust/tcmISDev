/******************************************************
 * ConfirmedAction.java
 *
 * Handle results from the Attribute Query form
 ******************************************************
 */
package com.tcmis.supplier.wbuy.action;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.DateHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.supplier.wbuy.beans.PoHeaderViewBean;
import com.tcmis.supplier.wbuy.beans.PoLineDetailViewBean;
import com.tcmis.supplier.wbuy.process.OrderingProcess;


public class ConfirmedAction extends TcmISBaseAction {

	private final int MAX_LINES=25;
	public String executeActionCharge(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		// servelet params
		String additemid = null;
		String radianpo = null;
		String addqty = null;
		String addprice = null;

		String forward = null;
		String errMsg = null;
		String poAction = request.getParameter("po_action");

		radianpo = request.getParameter("radianpo");

		// save the new additional line
		if(log.isDebugEnabled()) {
			log.debug("action: got radianpo: " + radianpo);
		}

		HttpSession session = request.getSession();

		// get personnel bean
		PersonnelBean personnel = (PersonnelBean) session.getAttribute("personnelBean");
		if (personnel==null) { errMsg = "No personnel found."; }

		// get the header bean frrom the session (for supplier)
		PoHeaderViewBean poHeaderBean = (PoHeaderViewBean) session.getAttribute("POHeaderBean");
		if (poHeaderBean==null) { errMsg = "No header found."; }

		// get the material bean(s) from the session (for currency_id)
		Collection materialBeans = (Collection) session.getAttribute("MaterialBeans");
		if (materialBeans==null) return "Error";
		PoLineDetailViewBean poLineDetailBean = null;
		Iterator matIter = materialBeans.iterator();
		while (matIter.hasNext()) {
			poLineDetailBean = (PoLineDetailViewBean) matIter.next();
		}
		if (poLineDetailBean==null) { errMsg = "No material lines found."; }

		if (errMsg!=null) {
			session.setAttribute("POErrorMsgBean",errMsg);
			return "Error";
		}

		String client = this.getDbUser(request);;
		OrderingProcess orderProcess = new OrderingProcess(client);

		// save the new additional line
		String nullStr=null;
		int userID=personnel.getPersonnelId();
		Vector<String> names = new Vector<String>();
		Enumeration e = request.getParameterNames();
		while( e.hasMoreElements() )
			names.add((String)e.nextElement());
		int totalAddChgLine = 0 ;

		// count the number of additional charges on the screen
		for(String name:names) {
			if(name.startsWith("addItemID_")) {
				totalAddChgLine++;
			}
		}

		String currency = poLineDetailBean.getCurrencyId();
		String supplier = poHeaderBean.getSupplier();

		BigDecimal poTotalPrice = poHeaderBean.getPoPrice();
		double totalPoPrice = poTotalPrice.doubleValue() ;
		for(int i=0;i<totalAddChgLine;i++) {
			additemid = request.getParameter("addItemID_"+i);
			addqty = request.getParameter("addQuantity_"+i);
			addprice = request.getParameter("addUnitPrice_"+i);
			String line = request.getParameter("AddLineNum_"+i);
			int lineNum= (Integer.parseInt(line));
			long itemID=Long.parseLong(additemid.trim());
			int quantity=Integer.parseInt(addqty.trim());
			if (itemID != 0)
			{
				orderProcess.saveLine(radianpo,lineNum,itemID,quantity,addprice.trim(),null,null,null,nullStr,nullStr,nullStr,nullStr,userID,nullStr,nullStr,0,nullStr,currency,supplier,nullStr,nullStr);
			}
			double addChgUnitPrice = Double.parseDouble(addprice.trim());
			double totalAddPrice = quantity * addChgUnitPrice ;
			totalPoPrice +=  totalAddPrice;
		}

		poTotalPrice = new BigDecimal(totalPoPrice);

		// now we have to rebuild line data so the new line will show up on the screen for them to confirm.

		// query only ADDITIONAL_CHARGES PO_line_detail_view bean(s)
		log.debug("getting additional charges beans");
		Collection addchargeBeans = orderProcess.getAddChargeLines(radianpo);

		// modify the POHeaderBean to reflect new TOTAL PRICE
		poTotalPrice = new BigDecimal(totalPoPrice);
		poTotalPrice = poTotalPrice.setScale(2,BigDecimal.ROUND_HALF_UP);

		poHeaderBean.setPoPrice(poTotalPrice);

		int totalLineCount=0;
		if (materialBeans!=null) totalLineCount += materialBeans.size();
		if (addchargeBeans!=null) totalLineCount += addchargeBeans.size();

		// set the session beans
		if (! addchargeBeans.isEmpty()) {
			session.setAttribute("AddChargeBeans", addchargeBeans);
		}
		session.setAttribute("TotalLineCount",totalLineCount+"");
		session.setAttribute("POHeaderBean",poHeaderBean);

		if (totalAddChgLine>0) {    // rule: set to problem if there are additional charges and user is trying to confirm.
			String confirmedStatus = null;
			String reason = "Additional charge needs to be approved by a Haas buyer.";
			if (poAction.equalsIgnoreCase("CONFIRM")) {
				String probResult = orderProcess.problemPO(poHeaderBean, personnel.getPersonnelId(),reason);
				if (probResult!=null) {
					errMsg = "Error setting PO to problem";
					forward = "Error";
				} else {
					confirmedStatus = OrderingProcess.STATUS_PROB;
					session.setAttribute("POProblemMsg",reason);
					session.setAttribute("POProblemReason","Once the buyer approves the charge the PO will be ready to confirm.");
					session.setAttribute("POProblemAction","PROBLEM");
					session.setAttribute("ConfirmedBean",confirmedStatus+"/REVIEW");
					session.setAttribute("StatusBean",confirmedStatus);
					session.setAttribute("CommentsBean",reason);
				}
			}
		}
		return forward;
	}

	private PoLineDetailViewBean findMaterial(String itemId, Collection materials,String lineNumber) throws Exception {
		PoLineDetailViewBean materialBean = null;
		Iterator iter = materials.iterator();
		while (iter.hasNext() && materialBean==null) {
			PoLineDetailViewBean detailBean = (PoLineDetailViewBean) iter.next();
			if (detailBean!=null) {
				if (itemId.equalsIgnoreCase(detailBean.getItemId().toString()) &&
						lineNumber.equalsIgnoreCase(detailBean.getPoLine().toString())) {
					materialBean = detailBean;
				}
			}
		}
		return materialBean;
	}

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// before confirming make sure we have added all additional charges to the PO
		String addLineResult = executeActionCharge(mapping,form,request,response); // this function is in original AddLineAction
		if( addLineResult != null ) // should be Error.
			return mapping.findForward(addLineResult);

		// servelet parameters
		String radianpo = null;
		String[] line_no;
		String[] unit_price;
		String[] ship_date;
		String[] dock_date;
		String[] quantity;
		String[] item_id;
		String poAction = null;

		String forward = "Success";
		String errMsg = null;

		radianpo = request.getParameter("radianpo");
		poAction = request.getParameter("po_action");
		if(log.isDebugEnabled()) {
			log.debug("got param po_action: " + poAction);
		}

		String confirmedBean = null;

		line_no = new String[MAX_LINES+1];
		unit_price = new String[MAX_LINES+1];
		ship_date = new String[MAX_LINES+1];
		dock_date = new String[MAX_LINES+1];
		quantity = new String[MAX_LINES+1];
		item_id = new String[MAX_LINES+1];
		int validCount=0;
		for (int i=1; i<=MAX_LINES; i++) {
			line_no[i] = request.getParameter("po_line_"+i);
			unit_price[i] = request.getParameter("unit_price_"+i);
			ship_date[i] = request.getParameter("vendor_ship_date_"+i);
			dock_date[i] = request.getParameter("promised_date_"+i);
			quantity[i] = request.getParameter("quantity_"+i);
			item_id[i] = request.getParameter("item_id_"+i);
			if (unit_price[i]!=null) validCount++;
		}

		if(log.isDebugEnabled()) {
			for (int j=1;j<=validCount;j++) {
				log.debug("got po_line_" + j + ": " + line_no[j]);
				log.debug("got unit_price_" + j + ": " + unit_price[j]);
				log.debug("got vendor_ship_date_" + j + ": " + ship_date[j]);
				log.debug("got promised_date_" + j + ": " + dock_date[j]);
				log.debug("got quantity_"+j +": "+ quantity[j]);
				log.debug("got item_id_"+j+": " + item_id[j]);
			}
		}
		Date[] shipDate;
		Date[] dockDate;
		shipDate = new Date[validCount+1];
		dockDate = new Date[validCount+1];

		HttpSession session = request.getSession();

		if (radianpo==null) { errMsg = "No PO number found!"; }

		// cleanup of unwanetd session beans
		session.removeAttribute("POProblemAction");
		session.removeAttribute("POProblemReason");

		// **begin* get SESSION beans

		// get personnel bean
		PersonnelBean personnel = (PersonnelBean) session.getAttribute("personnelBean");
		if (personnel==null) { errMsg = "No Personnel found!"; log.debug("No PersonnelBean!"); }

		// get the header bean frrom the session (for supplier)
		PoHeaderViewBean poHeaderBean = (PoHeaderViewBean) session.getAttribute("POHeaderBean");
		if (poHeaderBean==null) { errMsg = "No Header found!"; log.debug("No POHeaderBean!"); }

		// get original material line beans
		Collection materialBeans = (Collection) session.getAttribute("MaterialBeans");  // collection of PoLinwDetailViewBean
		if (materialBeans==null) { errMsg = "No Header found!"; log.debug("No material Line Beans!"); }

		// get additional charge line beans
		Collection addchargeBeans = (Collection) session.getAttribute("AddChargeBeans");  // collection of PoLinwDetailViewBean

		if (errMsg!=null) {
			session.setAttribute("POErrorMsgBean",errMsg);
			return mapping.findForward("Error");
		}
		// **end* get SESSION beans

		// before any actions, check for REJECT or PROBLEM
		if (poAction.equalsIgnoreCase("REJECT") || poAction.equalsIgnoreCase("PROBLEM")) {
			forward = "Problem";
			if(log.isDebugEnabled()) {
				log.debug("Problem: action is: "+ poAction);
			}
			session.setAttribute("POProblemAction",poAction);
			return mapping.findForward(forward);
		}

		// get database client (so we can get a connection)
		String client = this.getDbUser(request);
		OrderingProcess orderProcess = new OrderingProcess(client);

		ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", this.getTcmISLocale(request));
		String datePattern = library.getString("java.dateformat");

		// convert dates
		Timestamp promiseDate = null;
		Timestamp shippingDate = null;
		for (int ctr=1; ctr <= validCount; ctr++) {
			try {
				promiseDate = DateHandler.getTimestampFromString(datePattern,dock_date[ctr]);
				dockDate[ctr] = new Date(promiseDate.getTime());
			} catch (Exception eed) {
				log.error("Exception parsing dock date. (" + ctr + ")");
				errMsg = "Problem with the dock date[" + ctr + "]: "+ dock_date[ctr];
			}
			try {
				shippingDate = DateHandler.getTimestampFromString(datePattern, ship_date[ctr]);
				shipDate[ctr] = new Date(shippingDate.getTime());
			} catch (Exception eef) {
				log.error("Exception parsing ship date. (" + ctr + ")");
				errMsg = "Problem with ship date[" + ctr + "]: "+ ship_date[ctr];
			}
		}
		// now, if confirm, make sure all inputted values are within accepted tolerance (price & date)
		// verfiy split lines against the original item id material bean tolerances
		if (poAction.equalsIgnoreCase("CONFIRM")) {
			int counter=1;
			BigDecimal newPrice=null;
			Date maxvendorDate = null;
			PoLineDetailViewBean material = null;
			while (counter <= validCount) {
				material = findMaterial(item_id[counter],materialBeans,line_no[counter]);
				if (material!=null) {
					// check price
					newPrice = new BigDecimal(unit_price[counter]);
					if(log.isDebugEnabled()) {
						log.debug("newPrice  "+newPrice +" material.getMaxPriceLimit() "+material.getMaxPriceLimit()+" ");
					}
					if (newPrice!=null && newPrice.compareTo(material.getMaxPriceLimit())>0) {  // newPrice is greater than allowed price limit
						forward = "Problem";
						log.error("Problem: Price is greater than allowed tolerance levels");
						session.setAttribute("POProblemMsg","Price is greater than allowed tolerance levels.");
						session.setAttribute("POProblemReason","Price is greater than allowed tolerance levels.");
						session.setAttribute("POProblemAction","PROBLEM");
						return mapping.findForward(forward);
					}
					// check dates
					maxvendorDate = material.getMaxVendorShipDate();
					if (dockDate[counter]!=null && maxvendorDate!=null && dockDate[counter].compareTo(maxvendorDate)>0) {
						forward = "Problem";
						log.debug("Problem: Date is later than allowed limit.");
						session.setAttribute("POProblemMsg","Date is later than allowed limit.");
						session.setAttribute("POProblemReason","Date is later than allowed limit.");
						session.setAttribute("POProblemAction","PROBLEM");
						return mapping.findForward(forward);
					}

				} else {
					log.error("Problem: Material NOT FOUND!! itemId=" + item_id[counter]);
					forward = "Problem";
					session.setAttribute("POProblemMsg","Material NOT FOUND!! [itemId=" + item_id[counter]);
					session.setAttribute("POProblemReason","Material NOT FOUND!! [itemId=" + item_id[counter]+"] Unable to verify prices/dates!");
					session.setAttribute("POProblemAction","PROBLEM");
					return mapping.findForward(forward);
				}
				counter++;
			}
		}

		// first, put all additional charge lines in the charge_allocation_draft table
		if (poAction.equalsIgnoreCase("CONFIRM")) {
			if (addchargeBeans!=null && ! addchargeBeans.isEmpty()) {
				PoLineDetailViewBean addchgDetail=null;
				Iterator addchgIter = addchargeBeans.iterator();
				while (addchgIter.hasNext()) {
					addchgDetail = (PoLineDetailViewBean) addchgIter.next();
					orderProcess.addCharges(radianpo, 1000, addchgDetail.getPoLine().intValue(), 0);
				}
			}
		}

		// second, we confirm all material lines
		log.debug("executing confirm qry");
		int lineNum=0;
		String confirm=null;
		String nullStr = null;
		String supplier = poHeaderBean.getSupplier();
		String currency = null;
		String confirmedError=null;
		PoLineDetailViewBean lineDetail=null;
		
		// we must save the split/removed lines before confirming
		if (materialBeans.size()!=validCount && poAction.equalsIgnoreCase("CONFIRM")) {
			for (int k=1;k<=validCount && confirmedError==null;k++) {
				lineNum = Integer.parseInt(line_no[k]);
				lineDetail = findMaterial(item_id[k],materialBeans,line_no[k]);
				currency = lineDetail.getCurrencyId();
                int percent = 0 ;
				if ( lineDetail.getRemainingShelfLifePercent() != null )
					percent = lineDetail.getRemainingShelfLifePercent().intValue();
				BigDecimal quan = new BigDecimal(quantity[k]);
                confirmedError = orderProcess.saveLine(radianpo,lineNum,lineDetail.getItemId().longValue(),quan.intValue(),unit_price[k],lineDetail.getNeedDate(),shipDate[k],dockDate[k],lineDetail.getMfgPartNo(),lineDetail.getSupplierPartNo(),lineDetail.getDpasRating(),lineDetail.getPoLineNote(), personnel.getPersonnelId(),nullStr,nullStr,percent,nullStr,currency,supplier,confirm,nullStr);
				if (confirmedError !=null)
                {
                    log.debug("saving chg: called material saveLine("+lineNum+"): ret=["+confirmedError+"]");
                }
			}
		}

		/*TODO set auto commit to false before and then set it back to true, just like in purchaseOrderCreator.
		 * Allow to make more than one change at a time.PO gets locked as soon as one line is saved.*/
		//do the actual CONFIRM / SAVE
		for (int k=1;k<=validCount && confirmedError==null;k++) {
			lineNum = Integer.parseInt(line_no[k]);
			lineDetail = findMaterial(item_id[k],materialBeans,line_no[k]);
			currency = lineDetail.getCurrencyId();
            if (poAction.equalsIgnoreCase("CONFIRM")) {
				confirm = "Y";
			} else {
				confirm = null;
			}
			int percent = 0 ;
			if ( lineDetail.getRemainingShelfLifePercent() != null )
				percent = lineDetail.getRemainingShelfLifePercent().intValue();
			BigDecimal quan = new BigDecimal(quantity[k]);
			confirmedError = orderProcess.saveLine(radianpo,lineNum,lineDetail.getItemId().longValue(),quan.intValue(),unit_price[k],lineDetail.getNeedDate(),shipDate[k],dockDate[k],lineDetail.getMfgPartNo(),lineDetail.getSupplierPartNo(),lineDetail.getDpasRating(),lineDetail.getPoLineNote(), personnel.getPersonnelId(),nullStr,nullStr,percent,nullStr,currency,supplier,confirm,lineDetail.getShipFromLocationId());
			if (confirmedError !=null)
            {
              log.debug("called material saveLine("+lineNum+"): ret=["+confirmedError+"]");
            }
        }

		// third, we confirm all additional charge lines
		if (addchargeBeans!=null && ! addchargeBeans.isEmpty() && poAction.equalsIgnoreCase("CONFIRM")) {
			Iterator lineIter = addchargeBeans.iterator();
			while (lineIter.hasNext() && confirmedError==null) {
				lineDetail = (PoLineDetailViewBean) lineIter.next();
				confirm = "Y";
				confirmedError = orderProcess.saveLine(radianpo,lineDetail.getPoLine().intValue(),lineDetail.getItemId().longValue(),lineDetail.getQuantity().intValue(),lineDetail.getUnitPrice().toString(),null,null,null,null,nullStr,nullStr,nullStr, personnel.getPersonnelId(),nullStr,nullStr,0,nullStr,currency,supplier,confirm,lineDetail.getShipFromLocationId());
				if (confirmedError !=null)
                {
                    log.debug("called addcharges saveLine(): ret=["+confirmedError+"]");
                }
            }
		}

		// fourth, we update the beans new values for price, ship date, dock date (instead of re-quierying the server)
		confirmedBean ="CONFIRMED";
		if (confirmedError==null || (confirmedError!=null && confirmedError.length()==0)) {
			if( materialBeans.size()==validCount) {    // if same # of lines as on original
				BigDecimal lineNo;
				BigDecimal unitPrice;
				BigDecimal Quantity;
				for (int j=1;j<=validCount;j++) {
					lineNo = new BigDecimal(line_no[j]);
					unitPrice = new BigDecimal(unit_price[j]);
					Quantity = new BigDecimal(quantity[j]);
					BeanHandler.updateBean(materialBeans, "po_line", lineNo,"unit_price",  unitPrice);
					BeanHandler.updateBean(materialBeans, "po_line", lineNo,"vendor_ship_date",shipDate[j]);
					BeanHandler.updateBean(materialBeans, "po_line", lineNo,"promised_date",dockDate[j]);
					BeanHandler.updateBean(materialBeans, "po_line", lineNo,"quantity",Quantity);
				}
			}
			else { // reset related lines and attributes.
				session.removeAttribute("TotalLineCount");
				session.removeAttribute("AddChargeBeans");
				session.removeAttribute("MaterialBeans");

				materialBeans = orderProcess.getMaterialLines(radianpo);

				// query only ADDITIONAL_CHARGES PO_line_detail_view bean(s)
				addchargeBeans = orderProcess.getAddChargeLines(radianpo);

				// create specs & flowdowns, merge them with their corresponding materials, and create a Material Data View
				materialBeans = orderProcess.getAllLineData(poHeaderBean, materialBeans) ;

				int totalLineCount=0;
				if (materialBeans != null) totalLineCount+=materialBeans.size();
				if (addchargeBeans != null) totalLineCount+=addchargeBeans.size();
				session.setAttribute("TotalLineCount",totalLineCount+"");
				session.setAttribute("MaterialBeans",materialBeans);
				if (! addchargeBeans.isEmpty()) {
					session.setAttribute("AddChargeBeans", addchargeBeans);
				} else {
					session.removeAttribute("AddChargeBeans");
				}
			}
		}

		if (confirmedError==null && poAction.equalsIgnoreCase("CONFIRM")) {
			session.setAttribute("ConfirmedBean",confirmedBean);
			session.setAttribute("StatusBean",OrderingProcess.STATUS_CONF);
		}
		if (errMsg!=null) {
			session.setAttribute("POErrorMsgBean",errMsg);
		} else {
			session.removeAttribute("POErrorMsgBean");
		}

		return mapping.findForward(forward);
	}

}
