package com.tcmis.internal.invoice.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.BeanCopyException;
import com.tcmis.common.exceptions.NoDataException;
import com.tcmis.common.exceptions.NoRequestException;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.framework.TcmISBaseAction;
//import com.tcmis.common.spring.HaasApplicationContext;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.web.IHaasConstants;
import com.tcmis.internal.distribution.beans.InvoiceSearchBean;
import com.tcmis.internal.invoice.beans.InvoiceInputBean;
//import com.tcmis.internal.invoice.domain.IConfiguredInvoiceGroupsMapping;
import com.tcmis.internal.invoice.factory.InvoiceBeanFactory;
import com.tcmis.internal.invoice.process.AlbionInvoiceProcess;
import com.tcmis.internal.invoice.process.ArvinMeritorInvoiceProcess;
import com.tcmis.internal.invoice.process.AuroraInvoiceProcess;
import com.tcmis.internal.invoice.process.BaeInvoiceProcess;
import com.tcmis.internal.invoice.process.BedfordInvoiceProcess;
import com.tcmis.internal.invoice.process.BloomingburgInvoiceProcess;
import com.tcmis.internal.invoice.process.BoeingOffsiteInvoiceProcess;
import com.tcmis.internal.invoice.process.CalInvoiceProcess;
import com.tcmis.internal.invoice.process.CaterpillarInvoiceProcess;
import com.tcmis.internal.invoice.process.CharlesCityInvoiceProcess;
import com.tcmis.internal.invoice.process.DanaInvoiceProcess;
import com.tcmis.internal.invoice.process.DcxInvoiceProcess;
import com.tcmis.internal.invoice.process.DetroitDieselInvoiceProcess;
import com.tcmis.internal.invoice.process.DrsInvoiceProcess;
import com.tcmis.internal.invoice.process.DrsPalmInvoiceProcess;
import com.tcmis.internal.invoice.process.ExostarInvoiceProcess;
import com.tcmis.internal.invoice.process.FecInvoiceProcess;
import com.tcmis.internal.invoice.process.FlintInvoiceProcess;
import com.tcmis.internal.invoice.process.FortDodgeInvoiceProcess;
import com.tcmis.internal.invoice.process.GeWhippanyInvoiceProcess;
import com.tcmis.internal.invoice.process.GemaInvoiceProcess;
import com.tcmis.internal.invoice.process.GeneralDynamicsInvoiceProcess;
import com.tcmis.internal.invoice.process.GenericInvoiceProcess;
import com.tcmis.internal.invoice.process.GoletaewInvoiceProcess;
import com.tcmis.internal.invoice.process.HartleyInvoiceProcess;
import com.tcmis.internal.invoice.process.KellyInvoiceProcess;
import com.tcmis.internal.invoice.process.L3InvoiceProcess;
import com.tcmis.internal.invoice.process.LindenInvoiceProcess;
import com.tcmis.internal.invoice.process.MillerInvoiceProcess;
import com.tcmis.internal.invoice.process.MoraineInvoiceProcess;
import com.tcmis.internal.invoice.process.NalcoInvoiceProcess;
import com.tcmis.internal.invoice.process.NrgInvoiceProcess;
//import com.tcmis.internal.invoice.process.PolchemInvoiceProcess;
import com.tcmis.internal.invoice.process.PrismInvoiceProcess;
import com.tcmis.internal.invoice.process.QosInvoiceProcess;
import com.tcmis.internal.invoice.process.RacInvoiceProcess;
import com.tcmis.internal.invoice.process.SaicInvoiceProcess;
import com.tcmis.internal.invoice.process.SauerInvoiceProcess;
import com.tcmis.internal.invoice.process.SchweizerInvoiceProcess;
import com.tcmis.internal.invoice.process.SlacInvoiceProcess;
import com.tcmis.internal.invoice.process.StarkInvoiceProcess;
import com.tcmis.internal.invoice.process.SwaInvoiceProcess;
import com.tcmis.internal.invoice.process.SyracuseInvoiceProcess;
import com.tcmis.internal.invoice.process.TimkenInvoiceProcess;
import com.tcmis.internal.invoice.process.UtcInvoiceProcess;
import com.tcmis.internal.invoice.process.VolvoNrvInvoiceProcess;
import com.tcmis.internal.invoice.process.WelcomeInvoiceProcess;
import com.tcmis.internal.invoice.process.WilmingtonInvoiceProcess;
import com.tcmis.internal.invoice.process.ZfInvoiceProcess;

/***********************************************************
 * Action for calling invoice process.
 ***********************************************************/
public class InvoiceAction2 extends TcmISBaseAction
{

	//private static final String INV_GRP_CONFIG_BEAN_NAME = "invoiceGroupsConfigurationBean";
	private static final String RPT_PARAM_GROUP_BEAN_NAME = "&rpt_ReportGroupBeanId=";
	//private static final String CONFIGURED_INV_GRP_OPTIONS = "configuredInventoryGroupOptions";

	@SuppressWarnings("unchecked")
	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws BaseException, Exception
	{

		Log log = LogFactory.getLog(this.getClass());

		if (!this.isLoggedIn(request))
		{
			return (mapping.findForward("login"));
		}
		
		/*if (this.getSessionObject(request, CONFIGURED_INV_GRP_OPTIONS) == null)
	    {configureInvoiceGroupOptions(request);}*/
		InvoiceInputBean bean = new InvoiceInputBean();
		BeanHandler.copyAttributes(form, bean);
		String client = bean.getClient();
		if (client == null || client.length() == 0)
			return mapping.findForward("success");
		// I'll set default header to excel header and overwrite if I need html
		this.setExcelHeader(response);

		String invoiceConfBeanName = null;
		boolean isGroupReport = false;
		
		/*IConfiguredInvoiceGroupsMapping invoiceGroupsMapping = (IConfiguredInvoiceGroupsMapping) this
				.getSessionObject(request, INV_GRP_CONFIG_BEAN_NAME);*/

		try
		{
			if ("BAE".equalsIgnoreCase(client))
			{BaeInvoiceProcess process = new BaeInvoiceProcess(getDbUser(request));
				process.getWordInvoice(bean, response.getWriter());}
   else if ("BEDFORD".equalsIgnoreCase(client))
			{BedfordInvoiceProcess process = new BedfordInvoiceProcess(getDbUser(request));
				process.getWordInvoice(bean,response.getWriter());}
   else if ("BOEING_OFFSITE".equalsIgnoreCase(client))
			{BoeingOffsiteInvoiceProcess process = new BoeingOffsiteInvoiceProcess(getDbUser(request));
				process.getWordInvoice(bean,response.getWriter());}
   else if ("CAL".equalsIgnoreCase(client))
			{CalInvoiceProcess process = new CalInvoiceProcess(getDbUser(request));
				process.getWordInvoice(bean,response.getWriter());}
   /*else if (client.length() > 3 && "DANA".equalsIgnoreCase(client.substring(0,4)))
			{DanaInvoiceProcess process = new DanaInvoiceProcess(getDbUser(request));
				process.getWordInvoice(bean,response.getWriter());}*/
   else if (client.length() > 7 && "SCHAFER".equalsIgnoreCase(client.substring(0,7)))
			{DanaInvoiceProcess process = new DanaInvoiceProcess(getDbUser(request),"word.schafer.xsl",
					"INVOICE_VIEW_SG");
				process.getWordInvoice(bean,response.getWriter());}
   else if ("FENTON".equalsIgnoreCase(client))
			{DcxInvoiceProcess process = new DcxInvoiceProcess(getDbUser(request));
				process.getWordInvoice(bean,response.getWriter());}
   else if ("DET-DIESEL".equalsIgnoreCase(client))
			{DetroitDieselInvoiceProcess process = new DetroitDieselInvoiceProcess(getDbUser(request));
				process.getWordInvoice(bean,response.getWriter(),client);}
   else if ("DET-DIESEL-SF".equalsIgnoreCase(client))
			{DetroitDieselInvoiceProcess process = new DetroitDieselInvoiceProcess(getDbUser(request));
				process.getWordInvoice(bean,response.getWriter(),client);}
   else if ("DRS".equalsIgnoreCase(client))
			{DrsInvoiceProcess process = new DrsInvoiceProcess(getDbUser(request));
				process.getWordInvoice(bean,response.getWriter());}
   else if ("DRS_PALM_BAY".equalsIgnoreCase(client))
			{DrsPalmInvoiceProcess process = new DrsPalmInvoiceProcess(getDbUser(request));
				process.getWordInvoice(bean,false,response.getWriter());}
   else if ("DRS_PALM_BAY_FEE".equalsIgnoreCase(client))
			{DrsPalmInvoiceProcess process = new DrsPalmInvoiceProcess(	getDbUser(request));
				process.getWordInvoice(bean,true,response.getWriter());}
	   else if ("EXOSTAR".equalsIgnoreCase(client))
			{ExostarInvoiceProcess process = new ExostarInvoiceProcess(getDbUser(request));
				process.getWordInvoice(bean,response.getWriter());}
   else if ("FEC".equalsIgnoreCase(client))
			{FecInvoiceProcess process = new FecInvoiceProcess(getDbUser(request));
				process.getWordInvoice(bean,response.getWriter());}
   else if ("FEC_ADD_CHARGE".equalsIgnoreCase(client))
			{FecInvoiceProcess process = new FecInvoiceProcess(getDbUser(request));
				process.getAddChargeWordInvoice(bean,response.getWriter());}
   else if ("FLINT".equalsIgnoreCase(client))
			{FlintInvoiceProcess process = new FlintInvoiceProcess(getDbUser(request));
				process.getWordInvoice(bean,response.getWriter());}
   else if ("FLINT_MATERIAL".equalsIgnoreCase(client))
			{FlintInvoiceProcess process = new FlintInvoiceProcess(getDbUser(request));
				process.getWordInvoiceMaterial(bean,response.getWriter());}
   else if ("GEMA".equalsIgnoreCase(client))
			{GemaInvoiceProcess process = new GemaInvoiceProcess(getDbUser(request));
				process.getWordInvoice(bean,response.getWriter());}
	else if ("GD".equalsIgnoreCase(client))
	        {GeneralDynamicsInvoiceProcess process = new GeneralDynamicsInvoiceProcess(getDbUser(request));
				process.getWordInvoice(bean,response.getWriter());}
	else if ("GOLETAEW".equalsIgnoreCase(client))
			{GoletaewInvoiceProcess process = new GoletaewInvoiceProcess(getDbUser(request));
				process.getWordInvoice(bean,response.getWriter());}
	else if ("GRAYCOURT".equalsIgnoreCase(client))
			{ZfInvoiceProcess process = new ZfInvoiceProcess(getDbUser(request));
				process.getWordInvoice(bean,response.getWriter());}
	else if ("KELLY".equalsIgnoreCase(client))
			{KellyInvoiceProcess process = new KellyInvoiceProcess(getDbUser(request));
				process.getWordInvoice(bean,response.getWriter());}
	else if ("L-3".equalsIgnoreCase(client))
			{L3InvoiceProcess process = new L3InvoiceProcess(getDbUser(request));
				process.getWordInvoice(bean,response.getWriter());}
	else if ("MARYSVILLE".equalsIgnoreCase(client))
			{ZfInvoiceProcess process = new ZfInvoiceProcess(getDbUser(request));
				process.getWordInvoice(bean,response.getWriter());}
	else if ("MILLER".equalsIgnoreCase(client))
			{MillerInvoiceProcess process = new MillerInvoiceProcess(getDbUser(request));
				process.getWordInvoice(bean,response.getWriter());}
	else if ("MORAINE".equalsIgnoreCase(client))
			{MoraineInvoiceProcess process = new MoraineInvoiceProcess(getDbUser(request));
				process.getWordInvoice(bean,response.getWriter());}
	else if ("NALCO".equalsIgnoreCase(client))
			{NalcoInvoiceProcess process = new NalcoInvoiceProcess(getDbUser(request));
				process.getWordInvoice(bean,response.getWriter());}
	/*else if ("NRG".equalsIgnoreCase(client))
			{NrgInvoiceProcess process = new NrgInvoiceProcess(getDbUser(request));
				process.getWordInvoice(bean,response.getWriter());}*/
	else if ("PRISM".equalsIgnoreCase(client))
			{PrismInvoiceProcess process = new PrismInvoiceProcess(getDbUser(request));
				process.getWordInvoice(bean,response.getWriter());}
	else if ("RAC".equalsIgnoreCase(client))
			{RacInvoiceProcess process = new RacInvoiceProcess(getDbUser(request));	
			process.getWordInvoice(bean,response.getWriter());}
	else if ("SAIC".equalsIgnoreCase(client))
			{SaicInvoiceProcess process = new SaicInvoiceProcess(getDbUser(request));
				process.getWordInvoice(bean,response.getWriter());}
	else if ("SAIC_TF".equalsIgnoreCase(client))
			{SaicInvoiceProcess process = new SaicInvoiceProcess(getDbUser(request));
				process.getTestingfeeWordInvoice(bean,response.getWriter());}
	else if ("SAUER_DANFOSS".equalsIgnoreCase(client))
			{SauerInvoiceProcess process = new SauerInvoiceProcess(getDbUser(request));
				process.getWordInvoice(bean,response.getWriter());}
	else if ("SLAC".equalsIgnoreCase(client))
			{this.setHtmlHeader(response);
			SlacInvoiceProcess process = new SlacInvoiceProcess(getDbUser(request));
				process.getInvoice(bean,response.getWriter());}
	else if ("SLAC-SF".equalsIgnoreCase(client))
			{SlacInvoiceProcess process = new SlacInvoiceProcess(getDbUser(request));
				process.getServiceFeeWordInvoice(bean,response.getWriter());}
	else if ("STARK".equalsIgnoreCase(client))
			{StarkInvoiceProcess process = new StarkInvoiceProcess(getDbUser(request));
				process.getWordInvoice(bean,response.getWriter());}
	else if ("SWA".equalsIgnoreCase(client))
			{SwaInvoiceProcess process = new SwaInvoiceProcess(getDbUser(request));
				process.getWordInvoice(bean,response.getWriter());}
	else if ("TIMKEN".equalsIgnoreCase(client))
			{TimkenInvoiceProcess process = new TimkenInvoiceProcess(getDbUser(request));
				process.getWordInvoice(bean,response.getWriter());}
	else if ("UTC_HSD".equalsIgnoreCase(client))
			{bean.setDivision("HSD");
			  UtcInvoiceProcess process = new UtcInvoiceProcess(getDbUser(request));
			   process.getWordInvoice(bean,response.getWriter());}
	else if ("UTC_PWA".equalsIgnoreCase(client))
			{bean.setDivision("PWA");
			 UtcInvoiceProcess process = new UtcInvoiceProcess(getDbUser(request));
				process.getWordInvoice(bean,response.getWriter());}
    else if ("UTC_SAC".equalsIgnoreCase(client))
			{bean.setDivision("SAC");
				UtcInvoiceProcess process = new UtcInvoiceProcess(getDbUser(request));
				process.getWordInvoice(bean,response.getWriter());}
  	else if ("UTC_SAC_CTI".equalsIgnoreCase(client))
			{NrgInvoiceProcess process = new NrgInvoiceProcess(getDbUser(request),"word.utcsacctiinvoice.xsl",
						"INVOICE_UTC_WORD_DETAIL_VIEW ");
				process.getWordInvoice(bean,response.getWriter());}
	else if ("WELCOME".equalsIgnoreCase(client))
			{WelcomeInvoiceProcess process = new WelcomeInvoiceProcess(getDbUser(request));
				process.getWordInvoice(bean,response.getWriter());}
	else if ("WHIPPANY".equalsIgnoreCase(client))
	{GeWhippanyInvoiceProcess process = new GeWhippanyInvoiceProcess(getDbUser(request));
		process.getWordInvoice(bean,response.getWriter());}
	else if ("WHIPPANY FEE".equalsIgnoreCase(client))
		{GeWhippanyInvoiceProcess process = new GeWhippanyInvoiceProcess(getDbUser(request));
		process.getWordInvoice(bean,response.getWriter());}
	else if ("WILMINGTON".equalsIgnoreCase(client))
			{WilmingtonInvoiceProcess process = new WilmingtonInvoiceProcess(getDbUser(request));
				process.getWordInvoice(bean,response.getWriter());}
	else if ("VOLVO".equalsIgnoreCase(client))
			{VolvoNrvInvoiceProcess process = new VolvoNrvInvoiceProcess(getDbUser(request));
				process.getWordInvoice(bean,response.getWriter());}
	else if ("ZFGC MACHINE SERVICE".equalsIgnoreCase(client))
	{ZfInvoiceProcess process = new ZfInvoiceProcess(getDbUser(request));
		process.getWordInvoice(bean,response.getWriter());}
		else
			/*{
				invoiceConfBeanName = invoiceGroupsMapping
						.getConfiguredInvoiceGroupsMap()
						.get(client)
						.getInvoiceConfigurationBeanName();
				isGroupReport = invoiceGroupsMapping
						.getConfiguredInvoiceGroupsMap()
						.get(client)
						.isGroup();
				if (invoiceGroupsMapping
						.getConfiguredInvoiceGroupsMap()
						.containsKey(
								client)
						&& invoiceConfBeanName != null)
				{
					StringBuilder reportRequest = getHaasReportRequest(
							request,
							bean,
							invoiceConfBeanName,
							isGroupReport);
					response.sendRedirect(response
							.encodeRedirectURL(reportRequest
									.toString()));
				}
			
		
				else
				{
					GenericInvoiceProcess process = new GenericInvoiceProcess(
							getDbUser(request),
							this.getTcmISLocale(request));
					process.getWordInvoice(
							bean,
							response.getWriter());
				}*/
		{
			
			invoiceConfBeanName = client.substring(client.indexOf("|")+1, client.lastIndexOf("|"));
			if(client.indexOf("Group") > 0)
				{
				   isGroupReport=true;
				}
			if(invoiceConfBeanName != null)
			{
				StringBuilder reportRequest = getHaasReportRequest(
						request,
						bean,
						invoiceConfBeanName,
						isGroupReport);
				response.sendRedirect(response
						.encodeRedirectURL(reportRequest
								.toString()));
				
			}
			else
			{
				GenericInvoiceProcess process = new GenericInvoiceProcess(
						getDbUser(request),
						this.getTcmISLocale(request));
				process.getWordInvoice(
						bean,
						response.getWriter());
				
			}
			
		  }
			log.debug("done");
		}
		catch (BeanCopyException bce)
		{
			this.setHtmlHeader(response);
			// error copying bean
			// the only reason to fail is if non numeric invoice period was
			// entered
			log.fatal("BeanCopyException:" + bce.getMessage());
			BaseException bex = new BaseException(bce);
			bex.setMessageKey("error.invoiceperiod.invalid");
			throw bex;
		}
		catch (NoRequestException nrex)
		{
			this.setHtmlHeader(response);
			// no data to create report
			log.info("NoRequestException:" + nrex.getMessage());
			BaseException bex = new BaseException(nrex);
			bex.setMessageKey("error.norequest");
			throw bex;
		}
		catch (NoDataException ndex)
		{
			this.setHtmlHeader(response);
			// no data to create report
			log.info("NoDataException:" + ndex.getMessage());
			BaseException bex = new BaseException(ndex);
			bex.setMessageKey("error.nodata");
			throw bex;
		}
		catch (Exception e)
		{
			this.setHtmlHeader(response);
			log.fatal("Exception:" + e.getMessage());
			e.printStackTrace(System.out);
			throw e;
		}
		return (mapping.findForward("success"));
	}

	protected void setExcelHeader(HttpServletResponse response)
	{
		int cacheExpiringDuration = 10;
		response.setDateHeader("Expires", System.currentTimeMillis()
				+ cacheExpiringDuration * 1000);
		response.setHeader("Content-Disposition",
				"attachment; filename=unknown.doc");
		response.setContentType("text/xml");
	}

	protected void setHtmlHeader(HttpServletResponse response)
	{
		response.setHeader("Content-Disposition", "");
		response.setContentType("text/html");
	}

	/**
	 * Builds request string for selected invoice
	 * 
	 * @param request
	 * @param bean
	 * @param boolean
	 * @return request string
	 * @throws BaseException
	 */
	private StringBuilder getHaasReportRequest(HttpServletRequest request,
			InvoiceInputBean bean, String reportDefinitionBeanName,
			boolean isGroupReport) throws BaseException
	{
		String client = new String(bean.getClient().substring(0,bean.getClient().indexOf("|")));
		boolean useDuplicate = false;
		if (client.endsWith(" Original") || client.endsWith(" Duplicate"))
		{
			useDuplicate = true;
			if (client.indexOf("Freight") > 0)
			{
				client = client.replaceAll(" Freight Original", "");
				client = client.replaceAll(" Freight Duplicate", "");
				
			}
			else
			{
				client = client.replaceAll(" Original", "");
				client = client.replaceAll(" Duplicate", "");
			}
		}
		else
		{
			if (client.indexOf("#") > 0)
			{
				String[] words = client.split("#");
				client = words[0];
			}
		}
		StringBuilder reportRequest = new StringBuilder(
				"/HaasReports/report/printConfigurableReport.do");
		reportRequest.append("?pr_invoiceGroup=");
		reportRequest.append(client);
		reportRequest.append("&pr_filter_cond=");
		reportRequest.append(this.getFilterQueryString(bean));
		if (useDuplicate)
		{
			if (bean.getClient().substring(0,bean.getClient().indexOf("|")).endsWith(" Original"))
				reportRequest.append("&pr_originalInvoice=Y");
			else
				if (bean.getClient().substring(0,bean.getClient().indexOf("|")).endsWith(" Duplicate"))
					reportRequest.append("&pr_originalInvoice=N");
		}
		if (isGroupReport)
		{
			reportRequest.append(RPT_PARAM_GROUP_BEAN_NAME);
		}
		else
		{
			reportRequest.append(IHaasConstants.RPT_PARAM_BEAN_NAME);
		}
		reportRequest.append(reportDefinitionBeanName);
		reportRequest.append("&locale=");
		reportRequest.append(request.getLocale());
		if (log.isDebugEnabled())
		{
			log.debug("reportRequest : " + reportRequest.toString());
		}
		return reportRequest;
	}

	/**
	 * Returns a string for a WHERE clause
	 * 
	 * @param bean
	 * @return String
	 * @throws BaseException
	 */
	private String getFilterQueryString(InvoiceInputBean bean)
			throws BaseException
	{
		DbManager dbManager = new DbManager(IHaasConstants.TCM_CLIENT);
		InvoiceBeanFactory factory = new InvoiceBeanFactory(dbManager);
		GenericSqlFactory gfactory = new GenericSqlFactory(dbManager,new InvoiceSearchBean());
		String invoiceNumber = "";
		SearchCriteria searchCriteria = new SearchCriteria();
		StringBuilder query = new StringBuilder();
		query.append(IHaasConstants.DEFAULT_FILTER);
		if (bean.getInvoicePeriod() != null)
			searchCriteria.addCriterion("invoicePeriod",
					SearchCriterion.EQUALS, bean.getInvoicePeriod().toString());
// Larry Note: just pass compilation on my machine		
		if (bean.getInvoiceNumber() != null ) 
		{
			invoiceNumber = gfactory.selectSingleValue("select customer.fx_get_tcmis_invoice('"+bean.getInvoiceNumber()+"') from dual");
			searchCriteria.addCriterion("invoice", SearchCriterion.EQUALS, invoiceNumber);
		}
		String whereClause = factory.getWhereClause(searchCriteria);
		if (whereClause != null && whereClause.length() > 0)
		{
			String whereQuery = null;
			query.append(" AND ");
			if (whereClause.startsWith(IHaasConstants.KEYWORD_WHERE))
				whereQuery = new String(whereClause.substring(6));
			else
				whereQuery = new String(whereClause);
			query.append(whereQuery);
		}
		if (log.isDebugEnabled())
		{
			log.debug("query : " + query.toString());
		}
		return query.toString();
	}
	
	/*private void configureInvoiceGroupOptions(HttpServletRequest request)
	throws BaseException
		{
		IConfiguredInvoiceGroupsMapping invoiceGroupsMapping = null;
		if (this.getSessionObject(request, INV_GRP_CONFIG_BEAN_NAME) == null)
		{
			// Load the bean definition
			invoiceGroupsMapping = (IConfiguredInvoiceGroupsMapping) HaasApplicationContext
					.getApplicationContext().getBean(INV_GRP_CONFIG_BEAN_NAME);
			this.setSessionObject(request, invoiceGroupsMapping,
					INV_GRP_CONFIG_BEAN_NAME);
		}
		else
		{
			invoiceGroupsMapping = (IConfiguredInvoiceGroupsMapping) this
					.getSessionObject(request, INV_GRP_CONFIG_BEAN_NAME);
		}
		
		if (invoiceGroupsMapping.getInvoiceGroupsSelection() != null)
		{
			this.setSessionObject(request,
					invoiceGroupsMapping.getInvoiceGroupsSelection(),
					CONFIGURED_INV_GRP_OPTIONS);
		}
      }*/
}
