package com.tcmis.client.ups.process;

/**
 * Created by IntelliJ IDEA.
 * User: mathias.wennberg
 * Date: Jun 20, 2008
 * Time: 2:59:40 PM
 * To change this template use File | Settings | File Templates.
 */

import java.io.*;
import java.math.BigDecimal;
import java.util.*;

import org.apache.commons.logging.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.tcmis.common.db.*;
import com.tcmis.common.exceptions.*;
import com.tcmis.common.framework.*;
import com.tcmis.common.util.*;
import com.tcmis.common.admin.process.ZplDataProcess;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.client.ups.beans.*;
import com.tcmis.client.ups.factory.*;
import com.tcmis.internal.print.beans.PrintLabelInputBean;
import com.tcmis.internal.hub.beans.LabelInputBean;
import com.tcmis.supplier.shipping.factory.MslBoxViewBeanFactory;
import com.tcmis.supplier.shipping.beans.MslBoxViewBean;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.Transformer;

/**
 * ***************************************************************************
 * Process for processing ups shipments.
 *
 * @version 1.0
 *          ***************************************************************************
 */
public class AddressValidationProcess
        extends BaseProcess{
    Log log = LogFactory.getLog(this.getClass());
    private ResourceLibrary library = null;
    private DbManager dbManager = null;


    public AddressValidationProcess(String client) throws Exception{
        super(client);
        library = new ResourceLibrary("ups");
        dbManager = new DbManager(client);
    }


    public AddressValidationBean validateAddress(AddressValidationInputBean inputBean) throws
            BaseException, Exception{


            String response = NetHandler.getHttpPostResponse("https://onlinetools.ups.com/ups.app/xml/AV", null, null, getAddressValidationRequest(inputBean));
            File xmlFile = FileHandler.saveTempFile(response, "address",".xml","C:\\");
            AddressValidationResponseParser parser = new AddressValidationResponseParser("");
            AddressValidationBean bean = parser.parse(xmlFile);
            for(AddressValidationResultBean b1 : bean.getAddressValidationResultBeanCollection()){
System.out.println(b1.getRank()+":"+b1.getCity()+"-"+b1.getState()+"-"+b1.getZipLowEnd()+" to "+b1.getZipHighEnd());
            }
        return bean;
    }

    public String getAddressValidationRequest(AddressValidationInputBean inputBean) throws BaseException{
        StringWriter sw = new StringWriter();
        try {
            //first to accessrequest
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();
            Element rootElement  = document.createElement("AccessRequest");
            rootElement.setAttributeNS("http://www.w3.org/XML/1998/namespace", "xml:lang", "en-US");
            document.appendChild(rootElement);

            Element accessLicenseNumberElement  = document.createElement("AccessLicenseNumber");
            accessLicenseNumberElement.appendChild(document.createTextNode("5C27EDFCA5E60558"));
            rootElement.appendChild(accessLicenseNumberElement);

            Element userIdElement  = document.createElement("UserId");
            userIdElement.appendChild(document.createTextNode("mwennberg.qvm"));
            rootElement.appendChild(userIdElement);

            Element passwordElement  = document.createElement("Password");
            passwordElement.appendChild(document.createTextNode("haastcm"));
            rootElement.appendChild(passwordElement);

             DOMSource source = new DOMSource(document);
             StreamResult result = new StreamResult(sw);
             TransformerFactory transFactory = TransformerFactory.newInstance();
             Transformer transformer = transFactory.newTransformer();
             transformer.transform(source, result);

             //now do AddressValidationRequest
             document = documentBuilder.newDocument();
             rootElement  = document.createElement("AddressValidationRequest");
             rootElement.setAttributeNS("http://www.w3.org/XML/1998/namespace", "xml:lang", "en-US");
             document.appendChild(rootElement);
             Element requestElement  = document.createElement("Request");
             Element requestActionElement  = document.createElement("RequestAction");
             requestActionElement.appendChild(document.createTextNode("AV"));
             requestElement.appendChild(requestActionElement);
             rootElement.appendChild(requestElement);

             Element addressElement  = document.createElement("Address");
             Element cityElement  = document.createElement("City");
             cityElement.appendChild(document.createTextNode(StringHandler.emptyIfNull(inputBean.getCity())));
             addressElement.appendChild(cityElement);
             Element stateElement  = document.createElement("StateProvinceCode");
             stateElement.appendChild(document.createTextNode(StringHandler.emptyIfNull(inputBean.getState())));
             addressElement.appendChild(stateElement);
             Element zipElement  = document.createElement("PostalCode");
             zipElement.appendChild(document.createTextNode(StringHandler.emptyIfNull(inputBean.getZip())));
             addressElement.appendChild(zipElement);
             rootElement.appendChild(addressElement);


             source = new DOMSource(document);
             result = new StreamResult(sw);
             transFactory = TransformerFactory.newInstance();
             transformer = transFactory.newTransformer();
             transformer.transform(source, result);
System.out.println("req:" + sw.toString());
        }
        catch(Exception e){
            e.printStackTrace();
            throw new BaseException(e);
        }
        return sw.toString();
    }
}
