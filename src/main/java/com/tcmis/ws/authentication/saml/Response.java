package com.tcmis.ws.authentication.saml;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Calendar;
import java.util.TimeZone;

import javax.xml.crypto.dsig.XMLSignature;
import javax.xml.crypto.dsig.XMLSignatureFactory;
import javax.xml.crypto.dsig.dom.DOMValidateContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Response {
	public static Log				log	= LogFactory.getLog(Response.class);

	private NodeList				assertions;
	private final Certificate		certificate;
	private String					requestId;
	private Element					rootElement;
	private Document				xmlDoc;

	public Response(AccountSettings settings) throws CertificateException {
		certificate = new Certificate();
		certificate.loadCertificate(settings.getCertificate());
	}


	public String getNameId() throws Exception {
		NodeList nodes = xmlDoc.getElementsByTagNameNS("urn:oasis:names:tc:SAML:2.0:assertion", "NameID");
		if (nodes.getLength() == 0) {
			throw new Exception("No name id found in Document.");
		}
		Node item = nodes.item(0);
		return item.getNodeValue();
	}

	public String getRequestId() {
		return requestId;
	}

	// isValid() function should be called to make basic security checks to
	// responses.
	public boolean isValid() throws Exception {
		// Security Checks
		rootElement = xmlDoc.getDocumentElement();
		assertions = xmlDoc.getElementsByTagNameNS("urn:oasis:names:tc:SAML:2.0:assertion", "Assertion");
		xmlDoc.getDocumentElement().normalize();

		// Check SAML version
		String attName = rootElement.getAttribute("Version");
		if (!attName.equals("2.0")) {
			log.debug("Unsupported SAML Version: " + attName);
			return false;
			// throw new Exception("Unsupported SAML Version.");
		}

		// Check ID in the response
		attName = rootElement.getAttribute("ID");
		if (attName.equals("")) {
			log.debug("Response has no ID");
			return false;
			// throw new Exception("Missing ID attribute on SAML Response.");
		}

		if (assertions == null || assertions.getLength() != 1) {
			log.debug("No assertions found in response");
			return false;
			// throw new Exception("SAML Response must contain 1 Assertion.");
		}

		NodeList nodes = xmlDoc.getElementsByTagNameNS("*", "Signature");
		if (nodes == null || nodes.getLength() == 0) {
			log.debug("No Signature found in response");
			return false;
			// throw new Exception("Can't find signature in Document.");
		}

		// Check destination
		// String destinationUrl = rootElement.getAttribute("Destination");
		// if (destinationUrl != null) {
		// if(!destinationUrl.equals(currentUrl)){
		// throw new Exception("The response was received at " + currentUrl +
		// " instead of " + destinationUrl);
		// }
		// }



		// Check SubjectConfirmation, at least one SubjectConfirmation must be
		// valid
		NodeList nodeSubConf = xmlDoc.getElementsByTagNameNS("*", "SubjectConfirmation");
		boolean validSubjectConfirmation = true;
		for (int i = 0; i < nodeSubConf.getLength(); i++) {
			Node method = nodeSubConf.item(i).getAttributes().getNamedItem("Method");
			if (method != null && !method.getNodeValue().equals("urn:oasis:names:tc:SAML:2.0:cm:bearer")) {
				continue;
			}
			NodeList childs = nodeSubConf.item(i).getChildNodes();
			for (int c = 0; c < childs.getLength(); c++) {
				if (childs.item(c).getLocalName().equals("SubjectConfirmationData")) {
					Node inResponseTo = childs.item(c).getAttributes().getNamedItem("InResponseTo");
					if (inResponseTo != null && !inResponseTo.getNodeValue().equals(requestId)) {
						log.debug("Invalid inResponseTo: " + inResponseTo.getNodeValue() + " != " + requestId);
						validSubjectConfirmation = false;
					}

					Node notOnOrAfter = childs.item(c).getAttributes().getNamedItem("NotOnOrAfter");
					if (notOnOrAfter != null) {
						final Calendar notOnOrAfterDate = javax.xml.bind.DatatypeConverter.parseDateTime(notOnOrAfter.getNodeValue());
						Calendar now = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
						if (notOnOrAfterDate.before(now)) {
							log.debug("Expired NotOnOrAfter: " + notOnOrAfter.getNodeValue());
							// validSubjectConfirmation = false;
						}
					}
					Node notBefore = childs.item(c).getAttributes().getNamedItem("NotBefore");
					if (notBefore != null) {
						final Calendar notBeforeDate = javax.xml.bind.DatatypeConverter.parseDateTime(notBefore.getNodeValue());
						Calendar now = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
						if (notBeforeDate.before(now)) {
							log.debug("Before NotBefore: " + notOnOrAfter.getNodeValue());
							validSubjectConfirmation = false;
						}
					}
				}
			}
		}
		if (!validSubjectConfirmation) {
			return false;
			// throw new
			// Exception("A valid SubjectConfirmation was not found on this Response");
		}

		X509Certificate cert = certificate.getX509Cert();
		DOMValidateContext ctx = new DOMValidateContext(cert.getPublicKey(), nodes.item(0));
		// XMLSignatureFactory sigF = XMLSignatureFactory.getInstance("DOM");
		XMLSignatureFactory sigF = XMLSignatureFactory.getInstance("DOM", new org.jcp.xml.dsig.internal.dom.XMLDSigRI());
		XMLSignature xmlSignature = sigF.unmarshalXMLSignature(ctx);

		return xmlSignature.validate(ctx);
	}

	public void loadXml(String xml) throws ParserConfigurationException, SAXException, IOException {
		// log.debug("SAMLResponse - " + xml);
		DocumentBuilderFactory fty = DocumentBuilderFactory.newInstance();
		fty.setNamespaceAware(true);
		// XMLConstants with FEATURE_SECURE_PROCESSING prevents external
		// document access. (XXE/XEE Possible Attacks).
		// fty.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
		DocumentBuilder builder = fty.newDocumentBuilder();
		ByteArrayInputStream bais = new ByteArrayInputStream(xml.getBytes());
		xmlDoc = builder.parse(bais);

	}

	public void loadXmlFromBase64(String response) throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {
		loadXml(new String(new Base64().decode(response)));
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
}
