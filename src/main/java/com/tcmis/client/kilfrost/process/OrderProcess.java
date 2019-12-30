package com.tcmis.client.kilfrost.process;

/**
 * Created by IntelliJ IDEA.
 * User: mathias.wennberg
 * Date: Sep 12, 2008
 * Time: 2:48:59 PM
 * To change this template use File | Settings | File Templates.
 */

import java.io.File;
import java.io.FileOutputStream;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

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

import com.tcmis.client.kilfrost.beans.KilfrostDcSentBean;
import com.tcmis.client.kilfrost.beans.KilfrostDeliveryConfirmationBean;
import com.tcmis.client.kilfrost.beans.OrderBean;
import com.tcmis.client.kilfrost.beans.OrderLineBean;
import com.tcmis.client.kilfrost.factory.KilfrostDcSentBeanFactory;
import com.tcmis.client.kilfrost.factory.KilfrostDeliveryConfirmationBeanFactory;
import com.tcmis.client.order.beans.CustomerPoPreStageBean;
import com.tcmis.client.order.factory.CustomerPoPreStageBeanFactory;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.util.DateHandler;
import com.tcmis.common.util.FileHandler;
import com.tcmis.common.util.NetHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.StringHandler;

/**
 * ***************************************************************************
 * Process for processing kilfrost orders.
 *
 * @version 1.0
 *          ***************************************************************************
 */
public class OrderProcess
extends BaseProcess{
	Log log = LogFactory.getLog(this.getClass());
	private ResourceLibrary library = null;
	private DbManager dbManager = null;

	public OrderProcess(String client) throws BaseException {
		super(client);
		dbManager = new DbManager(client);
		library = new ResourceLibrary("kilfrost");
	}


	public void processOrder(String xmlString) throws
	BaseException {
		try {
			String dirname = library.getString("backupfile.dir");
			//        	dirname = "c:\\GeneratedJava\\";
			File orderFile = FileHandler.saveTempFile(xmlString, "order", ".xml", dirname);
			OrderParser parser = new OrderParser(getClient());
			OrderBean orderBean = parser.parse(orderFile);
			File image = File.createTempFile("label",".pdf",new File(dirname));
			try {
				FileOutputStream out = new FileOutputStream(image, true);
				out.write(new sun.misc.BASE64Decoder().decodeBuffer(orderBean.getDeliveryNote()));
				out.close();
			}
			catch(Exception e) {
				log.fatal("Error saving pdf file from Kilfrost:" + e.getMessage(),e);
			}
			orderBean.setDeliveryNote(image.getAbsolutePath());
			saveOrder(orderBean);
			//print label
			try {
				//PrintHandler.print(printerPath, image.getAbsolutePath());
			}
			catch(Exception e) { //in case someone turned off the printer....
				log.fatal("Error printing label:" + e.getMessage(),e);
			}
		}
		catch(Exception e) {
			log.fatal("Error processing order from kilfrost.", e);
			throw new BaseException(e.getCause());
		}
	}

	private void saveOrder(OrderBean orderBean) throws BaseException {
		BigDecimal loadId = new BigDecimal(dbManager.getOracleSequence("CUSTOMER_PO_LOAD_SEQ"));
		CustomerPoPreStageBeanFactory factory = new CustomerPoPreStageBeanFactory(dbManager);
		CustomerPoPreStageBean customerPoPreStageBean = new CustomerPoPreStageBean();

		customerPoPreStageBean.setStatus("NEW");

		customerPoPreStageBean.setCompanyId("KILFROST");
		customerPoPreStageBean.setUom("EA");
		customerPoPreStageBean.setCatalogId("Kilfrost");
		customerPoPreStageBean.setDateInserted(new Date());
		customerPoPreStageBean.setTransport("XML");
		customerPoPreStageBean.setTransporterAccount("Kilfrost");
		customerPoPreStageBean.setTradingPartner("KILFROST");
		customerPoPreStageBean.setTradingPartnerId("KILFROST");
		customerPoPreStageBean.setPre860("N");
		customerPoPreStageBean.setCustomerPoLineNo("0");

		if( "2".equals(orderBean.getOrderStatus() ) ) {
			customerPoPreStageBean.setTransactionType("860");
			customerPoPreStageBean.setChangeOrderSequence(new BigDecimal("1"));
		}
		else
			customerPoPreStageBean.setTransactionType("850");

		customerPoPreStageBean.setShiptoPartyId("KILFROST");
		customerPoPreStageBean.setPartGroupNo(new BigDecimal("1"));
		customerPoPreStageBean.setCustomerPoNo(orderBean.getOrderNumber());
		customerPoPreStageBean.setDateIssued(orderBean.getOrderDate());
		customerPoPreStageBean.setReleaseNum(orderBean.getPurchaseOrder());
		customerPoPreStageBean.setShippingNotes(orderBean.getShippingNote());
		customerPoPreStageBean.setShiptoPartyName(orderBean.getCustomerName());
		customerPoPreStageBean.setShiptoContactName(orderBean.getContactName());
		customerPoPreStageBean.setShiptoAddress1(orderBean.getAddress1());
		customerPoPreStageBean.setShiptoAddress2(orderBean.getAddress2());
		customerPoPreStageBean.setShiptoAddress3(orderBean.getAddress3());
		customerPoPreStageBean.setShiptoCity(orderBean.getCity());
		customerPoPreStageBean.setShiptoState(orderBean.getStateProvence());
		customerPoPreStageBean.setShiptoZip(orderBean.getZip());
		customerPoPreStageBean.setShiptoCountry(orderBean.getCountry());
		customerPoPreStageBean.setShiptoPhone(orderBean.getPhone());
		customerPoPreStageBean.setShiptoEmail(orderBean.getEmail());
		customerPoPreStageBean.setDeliveryNote(orderBean.getDeliveryNote());

		customerPoPreStageBean.setCustomerOrderType(orderBean.getOrderStatus());

		customerPoPreStageBean.setCustomerPickup(orderBean.getCustomerPickup());
		customerPoPreStageBean.setLoadId(loadId);
		int i = 0;
		for(OrderLineBean orderLineBean : orderBean.getOrderLineBeanCollection()){
			customerPoPreStageBean.setCustomerPoLineNo(Integer.toString(i));
			customerPoPreStageBean.setCatPartNo(orderLineBean.getPartNumber());
			if( orderLineBean.getQuantity().length() != 0 )
				customerPoPreStageBean.setQuantity(new BigDecimal(orderLineBean.getQuantity()));
			else
				customerPoPreStageBean.setQuantity(new BigDecimal("0"));
			//customer_po_line starts at 0 and load_line starts at 1
			i++;
			customerPoPreStageBean.setLoadLine(new BigDecimal(i));
			factory.insert(customerPoPreStageBean);

		}

	}

	public void sendShippingNotification() throws
	BaseException {
		try {
			KilfrostDeliveryConfirmationBeanFactory factory = new KilfrostDeliveryConfirmationBeanFactory(dbManager);
			Collection <KilfrostDeliveryConfirmationBean>c = factory.select(new SearchCriteria(), null);
			if (log.isDebugEnabled()) {
				log.debug("Found " + c.size() + " shipments for Kilfrost.");
			}
			KilfrostDcSentBeanFactory sentFactory = new KilfrostDcSentBeanFactory(dbManager);
			for(KilfrostDeliveryConfirmationBean bean : c) {
				//String response = NetHandler.getHttpPostResponse("https://www.kilfrost.com/xmlupdatev2.php", null, null, this.getShippingNotificationXml(bean));
				String response = NetHandler.getHttpPostResponse("https://www.kilfrost.com/xmlupdate.php", null, null, this.getShippingNotificationXml(bean));
				if (log.isDebugEnabled()) {
					log.debug("Response:" + response);
				}
				//flag as sent
				KilfrostDcSentBean sentBean = new KilfrostDcSentBean();
				sentBean.setDateSent(new Date());
				sentBean.setDateShipped(bean.getDateShipped());
				sentBean.setIssueId(bean.getTransactionNumber());
				sentBean.setPoNumber(bean.getKilfrostOrderNumber());
				sentBean.setQuantity(bean.getQuantityShipped());
				sentBean.setOrderStatus(bean.getOrderStatus());
				sentFactory.insert(sentBean);
				//I'm adding this to avoid getting a 403 error from their side
				Thread.sleep(2000);
			}

		}
		catch(Exception e) {
			log.fatal("Error processing order from kilfrost.", e);
			throw new BaseException(e);
		}
	}

	public String getShippingNotificationXml(KilfrostDeliveryConfirmationBean kilfrostDeliveryConfirmationBean) throws BaseException {
		StringWriter sw = new StringWriter();
		try {
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document document = documentBuilder.newDocument();
			Element
			rootElement  = document.createElement("ordershipping");
			rootElement.setAttributeNS("http://www.w3.org/XML/1998/namespace", "xml:lang", "en-US");
			document.appendChild(rootElement);

			Element
			shippingHeaderElement  = document.createElement("shippingheader");
			Element
			shippingTransIdElement  = document.createElement("shippingtransid");
			shippingTransIdElement.appendChild(document.createTextNode(kilfrostDeliveryConfirmationBean.getTransactionNumber().toString()));
			shippingHeaderElement.appendChild(shippingTransIdElement);
			Element
			orderNumberElement  = document.createElement("ordernumber");
			orderNumberElement.appendChild(document.createTextNode(kilfrostDeliveryConfirmationBean.getKilfrostOrderNumber()));
			shippingHeaderElement.appendChild(orderNumberElement);
			Element
			orderStatusElement  = document.createElement("orderstatus");
			orderStatusElement.appendChild(document.createTextNode(StringHandler.emptyIfNull(kilfrostDeliveryConfirmationBean.getOrderStatus())));
			shippingHeaderElement.appendChild(orderStatusElement);
			Element
			shippingDateElement  = document.createElement("shippingdate");
			shippingDateElement.appendChild(document.createTextNode(StringHandler.emptyIfNull(DateHandler.formatDate(kilfrostDeliveryConfirmationBean.getDateShipped(),"yyyy-MM-dd"))));
			shippingHeaderElement.appendChild(shippingDateElement);
			Element
			carrierNameElement  = document.createElement("carriername");
			carrierNameElement.appendChild(document.createTextNode(StringHandler.emptyIfNull(kilfrostDeliveryConfirmationBean.getCarrier())));
			shippingHeaderElement.appendChild(carrierNameElement);
			Element
			trackingidElement  = document.createElement("trackingid");
			trackingidElement.appendChild(document.createTextNode(StringHandler.emptyIfNull(kilfrostDeliveryConfirmationBean.getTrackingNumber())));
			shippingHeaderElement.appendChild(trackingidElement);
			Element
			actualFreightCostElement  = document.createElement("actualfreightcost");
			actualFreightCostElement.appendChild(document.createTextNode(StringHandler.emptyIfNull(kilfrostDeliveryConfirmationBean.getFreightCost())));
			//actualFreightCostElement.appendChild(document.createTextNode("111.22"));
			shippingHeaderElement.appendChild(actualFreightCostElement);
			Element
			currencyElement  = document.createElement("currency");
			currencyElement.appendChild(document.createTextNode(StringHandler.emptyIfNull(kilfrostDeliveryConfirmationBean.getFreightCostCurrencyId())));
			//currencyElement.appendChild(document.createTextNode("USD"));
			shippingHeaderElement.appendChild(currencyElement);
			rootElement.appendChild(shippingHeaderElement);
			Element
			shippingDetailsElement  = document.createElement("shippingdetails");
			Element
			batchNumberElement = document.createElement("batchnumber");
			batchNumberElement.appendChild(document.createTextNode(StringHandler.emptyIfNull(kilfrostDeliveryConfirmationBean.getBatchNumber())));
			shippingDetailsElement.appendChild(batchNumberElement);
			Element
			quantityElement  = document.createElement("quantity");
			quantityElement.appendChild(document.createTextNode(kilfrostDeliveryConfirmationBean.getQuantityShipped().toString()));
			shippingDetailsElement.appendChild(quantityElement);
			rootElement.appendChild(shippingDetailsElement);

			DOMSource source = new DOMSource(document);
			StreamResult result = new StreamResult(sw);
			TransformerFactory transFactory = TransformerFactory.newInstance();
			Transformer transformer = transFactory.newTransformer();
			transformer.transform(source, result);
			if (log.isDebugEnabled()) {
				log.debug("returning:" + sw.toString());
			}
		}
		catch(Exception e){
			e.printStackTrace();
			throw new BaseException(e);
		}
		return sw.toString();
	}
}
