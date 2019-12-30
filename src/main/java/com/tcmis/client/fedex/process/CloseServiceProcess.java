package com.tcmis.client.fedex.process;

/**
 * Created by IntelliJ IDEA.
 * User: mathias.wennberg
 * Date: Sep 29, 2008
 * Time: 2:53:23 PM
 * To change this template use File | Settings | File Templates.
 */

import java.io.IOException;
import java.io.StringWriter;
import java.util.Collection;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.tcmis.client.fedex.beans.FedexGroundAccountDataBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.BaseStatus;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.framework.StatusType;
import com.tcmis.common.util.DateHandler;
import com.tcmis.common.util.FileHandler;
import com.tcmis.common.util.NetHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.common.util.StringHandler;

/**
 * ***************************************************************************
 * Process for processing FedEx shipments.
 * 
 * @version 1.0
 *          *****************************************************************
 *          **********
 */
public class CloseServiceProcess extends BaseProcess {
	private ResourceLibrary fedexLibrary = null;
	private ResourceLibrary msgLibrary = null;
	Log log = LogFactory.getLog(getClass());

	public CloseServiceProcess(String client) throws Exception {
		super(client);
		fedexLibrary = new ResourceLibrary("fedex");
		msgLibrary = new ResourceLibrary("com.tcmis.common.resources.CommonResources");
	}

	private Element getAccountNumber(Document doc, FedexGroundAccountDataBean fedexAccount) {
		Element accountNumberElement = doc.createElement("ns:AccountNumber");
		accountNumberElement.appendChild(doc.createTextNode(StringHandler.xmlEncode(fedexAccount.getCarrierAccount())));
		return accountNumberElement;
	}

	private Element getClientDetail(Document doc, FedexGroundAccountDataBean fedexAccount) {
		Element clientDetailElement = doc.createElement("ns:ClientDetail");
		clientDetailElement.appendChild(getAccountNumber(doc, fedexAccount));
		clientDetailElement.appendChild(getMeterNumber(doc, fedexAccount));
		return clientDetailElement;
	}

	private Element getCloseRequestElement(Document doc, FedexGroundAccountDataBean fedexAccount) {
		Element request = doc.createElement("ns:GroundCloseRequest");
		request.setAttribute("xmlns:ns", "http://fedex.com/ws/close/v2");
		request.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
		request.setAttribute("xsi:schemaLocation", "http://fedex.com/ws/close/v2/CloseService_v2.xsd");
		return request;
	}

	private String getCloseServiceRequest(FedexGroundAccountDataBean fedexAccount) throws Exception {
		StringWriter sw = new StringWriter();
		try {

			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document doc = documentBuilder.newDocument();

			Element closeRequest = getCloseRequestElement(doc, fedexAccount);

			closeRequest.appendChild(getWebAuthenticationDetail(doc, fedexAccount));

			closeRequest.appendChild(getClientDetail(doc, fedexAccount));

			closeRequest.appendChild(getVersion(doc, fedexAccount));

			Element closeTimeElement = doc.createElement("ns:TimeUpToWhichShipmentsAreToBeClosed");
			closeTimeElement.appendChild(doc.createTextNode(new StringBuilder(DateHandler.formatDate(new Date(), "yyyy-MM-dd'T'HH:mm:ssZ")).insert(22, ":").toString()));
			closeRequest.appendChild(closeTimeElement);

			doc.appendChild(closeRequest);

			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(sw);
			TransformerFactory transFactory = TransformerFactory.newInstance();
			Transformer transformer = transFactory.newTransformer();
			transformer.transform(source, result);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new BaseException(e);
		}
		return sw.toString();
	}

	private Element getKey(Document doc, FedexGroundAccountDataBean fedexAccount) {
		Element key = doc.createElement("ns:Key");
		key.appendChild(doc.createTextNode(StringHandler.xmlEncode(fedexAccount.getCarrierSecurityKey())));
		return key;
	}

	private Element getMeterNumber(Document doc, FedexGroundAccountDataBean fedexAccount) {
		Element meterNumberElement = doc.createElement("ns:MeterNumber");
		meterNumberElement.appendChild(doc.createTextNode(StringHandler.xmlEncode(fedexAccount.getCarrierMeterNumber())));
		return meterNumberElement;
	}

	private Element getPassword(Document doc, FedexGroundAccountDataBean fedexAccount) {
		Element elem = doc.createElement("ns:Password");
		elem.appendChild(doc.createTextNode(StringHandler.xmlEncode(fedexAccount.getCarrierPassword())));
		return elem;
	}

	private Element getUserCredential(Document doc, FedexGroundAccountDataBean fedexAccount) {
		Element userCredentialElement = doc.createElement("ns:UserCredential");
		userCredentialElement.appendChild(getKey(doc, fedexAccount));
		userCredentialElement.appendChild(getPassword(doc, fedexAccount));
		return userCredentialElement;
	}

	private Element getVersion(Document doc, FedexGroundAccountDataBean fedexAccount) {
		Element versionElement = doc.createElement("ns:Version");
		Element serviceIdElement = doc.createElement("ns:ServiceId");
		serviceIdElement.appendChild(doc.createTextNode("clos"));
		versionElement.appendChild(serviceIdElement);
		Element majorElement = doc.createElement("ns:Major");
		majorElement.appendChild(doc.createTextNode("2"));
		versionElement.appendChild(majorElement);
		Element intermediateElement = doc.createElement("ns:Intermediate");
		intermediateElement.appendChild(doc.createTextNode("1"));
		versionElement.appendChild(intermediateElement);
		Element minorElement = doc.createElement("ns:Minor");
		minorElement.appendChild(doc.createTextNode("0"));
		versionElement.appendChild(minorElement);
		return versionElement;
	}

	private Element getWebAuthenticationDetail(Document doc, FedexGroundAccountDataBean fedexAccount) {
		Element webAuthenticationDetailElement = doc.createElement("ns:WebAuthenticationDetail");
		webAuthenticationDetailElement.appendChild(getUserCredential(doc, fedexAccount));
		return webAuthenticationDetailElement;
	}

	public BaseStatus submitCloseRequest(String opsEntityId, String hub) throws BaseException, Exception {
		FedexGroundAccountDataBean account = null;
		String fedexUrl = fedexLibrary.getString("url");
		String backupDir = fedexLibrary.getString("backupfile.dir");
		//String fedexUrl = "https://gatewaybeta.fedex.com:443/xml";
		//String backupDir = "c:\\temp\\fedex\\";

		DbManager dbManager = new DbManager(getClient(), getLocale());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager, new FedexGroundAccountDataBean());

		SearchCriteria criteria = new SearchCriteria();
		criteria.addCriterionWithMode("hub", "is", hub);
		if (!StringHandler.isBlankString(opsEntityId)) {
			criteria.addCriterionWithMode("opsEntityId", "is", opsEntityId);
		}
		Collection dbResults = factory.select(criteria, new SortCriteria(), "FEDEX_GROUND_ACCOUNT_DATA");
		for (Object dbObject : dbResults) {
			account = (FedexGroundAccountDataBean) dbObject;
		}

		if (account != null) {
			String request = getCloseServiceRequest(account);
			FileHandler.saveTempFile(request, "hub" + hub +  "_close_service_request", ".xml", backupDir);
			BaseStatus response = new BaseStatus(StatusType.ERROR, msgLibrary.getString("system.error"));
			try {
				String fedexResponse = NetHandler.getHttpPostResponse(fedexUrl, null, null, request);
				FileHandler.saveTempFile(fedexResponse, "hub" + hub +  "_close_service_response", ".xml", backupDir);
				if (!StringHandler.isBlankString(fedexResponse)) {
					Pattern msgPattern = Pattern.compile("\\<v2\\:Message\\>(.*?)\\<\\/v2\\:Message\\>");

					if (fedexResponse.indexOf("SUCCESS") > 0) {
						response = new BaseStatus(StatusType.OK, msgLibrary.getString("label.closeperformedsuccessfully"));
					}
					else if (fedexResponse.indexOf("ERROR") > 0) {
						StringBuilder errorMessage = new StringBuilder(msgLibrary.getString("error.Fedex")).append(" ");
						Matcher errorMatch = msgPattern.matcher(fedexResponse);
						while (errorMatch.find()) {
							errorMessage.append(errorMatch.group(1)).append("<BR>");
						}
						response.setText(errorMessage.toString());
					}
					else {
						response = new BaseStatus(StatusType.WARNING);
						StringBuilder info = new StringBuilder(msgLibrary.getString("message.Fedex")).append(" ");
						Matcher infoMatch = msgPattern.matcher(fedexResponse);
						while (infoMatch.find()) {
							info.append(infoMatch.group(1)).append("<BR>");
						}
						response.setText(info.toString());

					}
				}
				else {
					response.setText(msgLibrary.getString("error.noresponsefromfedex"));
				}
			}
			catch (IOException ioe) {
				response.setText(msgLibrary.getString("error.unabletocontactfedex") + "\n" + ioe.getMessage());
			}
			return response;
		}
		else {
			return new BaseStatus(StatusType.WARNING, msgLibrary.getString("label.notavailable"));
		}

	}
}