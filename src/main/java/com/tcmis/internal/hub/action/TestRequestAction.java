package com.tcmis.internal.hub.action;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Hashtable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.internal.hub.beans.TestRequestInputBean;
import com.tcmis.internal.hub.beans.TestResultBean;
import com.tcmis.internal.hub.factory.TestRequestBeanFactory;
import com.tcmis.internal.hub.process.TestRequestSearchProcess;

public class TestRequestAction extends TcmISBaseAction {

	@SuppressWarnings("unchecked")
	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// Default to success
		ActionForward next = mapping.findForward("success");
		
		// Check that the user is logged in before continuing.
		if (!this.isLoggedIn(request, true)) {
			request.setAttribute("requestedPage", "testrequestform");
			request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
			return (mapping.findForward("login"));
		}
		
		// Fail fast. Allow the page to reload if either the form or action is null.
		if( form == null ){
			return next;
		}
		
		// Capture the form input
		PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request,"personnelBean");
		StringBuffer requestURL = request.getRequestURL();
		TestRequestInputBean input = new TestRequestInputBean(); 
		BeanHandler.copyAttributes(form, input, this.getTcmISLocale(request));
		TestRequestSearchProcess process = new TestRequestSearchProcess(this.getDbUser(request),getTcmISLocaleString(request),requestURL.substring(0,requestURL.lastIndexOf("/")));

		String uAction = (String) ((DynaBean)form).get("uAction");
		
		// Only the uAction values enumerated here are handled. 
		// All other uAction values result in "success", basically reloading the page without changing anything.
		if("sign".equals(uAction)){
			String myStatus = input.getRequestStatus();
			if("".equals(myStatus)){
				// No status value was found. There's a problem.
				next = mapping.findForward("error");				
			} else if (TestRequestInputBean.REQUEST_STATUS_COMPLETE.equals(myStatus)){
				// The request can no longer be updated, something's wrong.
				next = mapping.findForward("error");
			} else {
                String sign = "";
                if(TestRequestInputBean.REQUEST_STATUS_NEW.equals(myStatus)){
                    boolean requiredCustomerResponse = process.testRequiredCustomerResponse(input.getTestRequestId());
                    if (requiredCustomerResponse) {
                        // The request has recently been created and has not yet been sent to the lab.
					    input.setRequestStatus(TestRequestInputBean.REQUEST_STATUS_LAB);
                    }else {
                        //if test does not need customer response then set new status to TESTING
                        input.setRequestStatus(TestRequestInputBean.REQUEST_STATUS_TESTING);
                    }
                    input.setToLabPersonnelId(personnelBean.getPersonnelIdBigDecimal());
                    sign = process.signTestRequest(input,personnelBean);
                    //cancel duplicate test request
                    if ("OK".equals(sign))
                        process.cancelDuplicateTestRequest(input,personnelBean);

                } else if (TestRequestInputBean.REQUEST_STATUS_LAB.equals(myStatus)){
					// The request is in the lab for testing.
					input.setRequestStatus(TestRequestInputBean.REQUEST_STATUS_TESTING);
					input.setSamplesReceivedPersonnelId(personnelBean.getPersonnelIdBigDecimal());
                    sign = process.signTestRequest(input,personnelBean);
				} else if (TestRequestInputBean.REQUEST_STATUS_TESTING.equals(myStatus)){
                    boolean requiredCustomerResponse = process.testRequiredCustomerResponse(input.getTestRequestId());

                    if (requiredCustomerResponse) {
					    input.setRequestStatus(TestRequestInputBean.REQUEST_STATUS_QC);
                        input.setTestCompletedPersonnelId(personnelBean.getPersonnelIdBigDecimal());
                    }else {
                        input.setRequestStatus(TestRequestInputBean.REQUEST_STATUS_COMPLETE);
                        //because this test does not need to go thru customer sign off
                        //the tester, QC and close person are the same
                        input.setTestCompletedPersonnelId(personnelBean.getPersonnelIdBigDecimal());
                        input.setQualificationPersonnelId(personnelBean.getPersonnelIdBigDecimal());
                        input.setClosedPersonnelId(personnelBean.getPersonnelIdBigDecimal());
                    }

					Collection<TestResultBean> trBeans = BeanHandler.getBeans((DynaBean)form, "testResultsDisplay", new TestResultBean(), this.getTcmISLocale(request));
					input.setTestResults(trBeans);
                    String update = process.updateTestRequest(input,personnelBean);
                    if (update != null && update.contains("OK")){
						sign = "OK";
					} else {
						sign = "Error";
					}
                } else if (TestRequestInputBean.REQUEST_STATUS_QC.equals(myStatus)){
					// The request has been returned to the warehouse.				
					input.setRequestStatus(TestRequestInputBean.REQUEST_STATUS_COMPLETE);
                    //the QC is also the person that completed this test
                    input.setQualificationPersonnelId(personnelBean.getPersonnelIdBigDecimal());
                    input.setClosedPersonnelId(personnelBean.getPersonnelIdBigDecimal());
                    sign = process.signTestRequest(input,personnelBean);
                }
                // Apply the date and user ID to the appropriate fields in the Lab_Test table, and promote the status to the next stage.
				TestRequestInputBean output = process.getExistingTestRequest(input.getTestRequestId());
				if ("OK".equals(sign)){
					if (("To Lab".equalsIgnoreCase(input.getRequestStatus()) || "QC".equalsIgnoreCase(input.getRequestStatus())))
					{
					  process.sendNotificationMail(input, personnelBean);
					}
					request.setAttribute("testrequestbean", output);
					next = mapping.findForward("success");
				} else {
					next = mapping.findForward("error");
				}
			}
		}else if ("update".equals(uAction)){
			// Grab the test result information
			Collection<TestResultBean> trBeans = BeanHandler.getBeans((DynaBean)form, "testResultsDisplay", new TestResultBean(), this.getTcmISLocale(request));
			input.setTestResults(trBeans);
			// Update all fields in the Lab_Test table, but do not promote the status.			
			String update = process.updateTestRequest(input,personnelBean);
			if (update == null || !update.contains("OK")){
				request.setAttribute("errorMsg", update);
			}
			TestRequestInputBean output = process.getExistingTestRequest(input.getTestRequestId());
			request.setAttribute("testrequestbean", output);
			next = mapping.findForward("success");
		}else if ("cancel".equals(uAction)){
			input.setCancelledBy(personnelBean.getPersonnelIdBigDecimal());
			String cancel = process.cancelTestRequest(input);
			if (!"OK".equals(cancel)){
				request.setAttribute("errorMsg", cancel);
			}
			TestRequestInputBean output = process.getExistingTestRequest(input.getTestRequestId());
			request.setAttribute("testrequestbean", output);
			next = mapping.findForward("success");
		} else if ("create".equals(uAction)){
			TestRequestInputBean output = null;
			// Create a new test request for the selected receipt.
			if(input.hasReceiptId()){
				Hashtable dataH= process.createTestRequest(input.getReceiptId(), personnelBean.getPersonnelIdBigDecimal());
				if(BigDecimal.ZERO.equals((BigDecimal)dataH.get("REQUEST_ID"))){
					request.setAttribute("createError", (String)dataH.get("ERROR_MSG"));
				} else {
					output = process.getExistingTestRequest((BigDecimal)dataH.get("REQUEST_ID"));
					request.setAttribute("testrequestbean", output);
				}
				next = mapping.findForward("success");
			}			
		} else {
			TestRequestInputBean output = null;
			// Load the specified request from the URL parameters, if the URL has at least one of 
			// the unique values ReceiptId or TestRequestId.
			if(input.hasTestRequestId()){
				output = process.getExistingTestRequest(input.getTestRequestId());
                request.setAttribute("testrequestbean", output);
			} else {
				request.setAttribute("testrequestbean", input);
			}

			next = mapping.findForward("success");
		}
		
		this.saveTcmISToken(request);
		return next;
	}

}
