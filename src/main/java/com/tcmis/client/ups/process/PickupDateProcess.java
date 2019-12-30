package com.tcmis.client.ups.process;

/**
 * Created by IntelliJ IDEA.
 * User: mathias.wennberg
 * Date: Jun 18, 2008
 * Time: 1:43:56 PM
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
public class PickupDateProcess
        extends BaseProcess{
    Log log = LogFactory.getLog(this.getClass());
    private ResourceLibrary library = null;
    private DbManager dbManager = null;


    public PickupDateProcess(String client) throws Exception{
        super(client);
        library = new ResourceLibrary("ups");
        dbManager = new DbManager(client);
    }


    public void getPickupDate() throws
            BaseException, Exception{
        String bookmark = "";
        while(bookmark != null) {
            String response = NetHandler.getHttpPostResponse("https://wwwcie.ups.com/ups.app/xml/QVEvents", null, null, getQuantumViewRequest(bookmark));
            File xmlFile = FileHandler.saveTempFile(response, "quantumview",".xml","C:\\");
            QuantumViewResponseParser parser = new QuantumViewResponseParser("");
            QuantumViewBean bean = parser.parse(xmlFile);
System.out.println(bean.getQuantumViewResponseBeanCollection().size() + " sections");
            for(QuantumViewResponseBean b1 : bean.getQuantumViewResponseBeanCollection()){
System.out.println(b1.getPackageResultsBeanCollection().size() + " tracking numbers");
                for(PackageResultsBean b2 : b1.getPackageResultsBeanCollection()){
System.out.println(b2.getPickupDate()+ " " + b2.getTrackingNumber());
                }
            }
            bookmark = bean.getBookmark();
System.out.println("bookmark:" + bookmark);
        }
    }

    public String getQuantumViewRequest(String bookmark) throws BaseException{
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

             //now do QuantumViewRequest
             document = documentBuilder.newDocument();
             rootElement  = document.createElement("QuantumViewRequest");
             rootElement.setAttributeNS("http://www.w3.org/XML/1998/namespace", "xml:lang", "en-US");
             document.appendChild(rootElement);
             Element requestElement  = document.createElement("Request");
             Element requestActionElement  = document.createElement("RequestAction");
             requestActionElement.appendChild(document.createTextNode("QVEvents"));
             requestElement.appendChild(requestActionElement);
             rootElement.appendChild(requestElement);

             Element subscriptionRequestElement  = document.createElement("SubscriptionRequest");
             Element nameElement  = document.createElement("Name");
             nameElement.appendChild(document.createTextNode("OutboundFull"));
             subscriptionRequestElement.appendChild(nameElement);
             rootElement.appendChild(subscriptionRequestElement);

             if(bookmark != null){
                 Element bookmarkElement  = document.createElement("Bookmark");
                 bookmarkElement.appendChild(document.createTextNode(bookmark));
                 rootElement.appendChild(bookmarkElement);
             }
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
