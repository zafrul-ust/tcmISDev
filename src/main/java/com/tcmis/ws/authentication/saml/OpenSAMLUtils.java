package com.tcmis.ws.authentication.saml;

import java.io.StringWriter;
import java.security.NoSuchAlgorithmException;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opensaml.common.impl.SecureRandomIdentifierGenerator;
import org.opensaml.ws.soap.soap11.Body;
import org.opensaml.ws.soap.soap11.Envelope;
import org.opensaml.xml.Configuration;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.XMLObjectBuilderFactory;
import org.opensaml.xml.io.Marshaller;
import org.opensaml.xml.io.MarshallingException;
import org.w3c.dom.Document;

public class OpenSAMLUtils {

	protected static Log							logger	= LogFactory.getLog(OpenSAMLUtils.class);

	private static SecureRandomIdentifierGenerator	secureRandomIdGenerator;

	static {
		try {
			secureRandomIdGenerator = new SecureRandomIdentifierGenerator();
		}
		catch (NoSuchAlgorithmException e) {
			logger.error(e.getMessage(), e);
		}
	}

	public static <T> T buildSAMLObject(final Class<T> clazz) {
		T object = null;
		try {
			XMLObjectBuilderFactory builderFactory = Configuration.getBuilderFactory();
			QName defaultElementName = (QName) clazz.getDeclaredField("DEFAULT_ELEMENT_NAME").get(null);
			object = (T) builderFactory.getBuilder(defaultElementName).buildObject(defaultElementName);
		}
		catch (IllegalAccessException e) {
			throw new IllegalArgumentException("Could not create SAML object");
		}
		catch (NoSuchFieldException e) {
			throw new IllegalArgumentException("Could not create SAML object");
		}

		return object;
	}

	public static String generateSecureRandomId() {
		return secureRandomIdGenerator.generateIdentifier();
	}

	public static void logSAMLObject(final XMLObject object) {
			logger.info(SAMLObjectToString(object));
	}
	
	public static String SAMLObjectToString(final XMLObject object) {
        try {
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            StreamResult result = new StreamResult(new StringWriter());
            DOMSource source = new DOMSource(object.getDOM());
            transformer.transform(source, result);
            return result.getWriter().toString();
        } catch (TransformerException e) {
            logger.error(e.getMessage(), e);
        }

		return "";
	}

	public static Envelope wrapInSOAPEnvelope(final XMLObject xmlObject) throws IllegalAccessException {
		Envelope envelope = OpenSAMLUtils.buildSAMLObject(Envelope.class);
		Body body = OpenSAMLUtils.buildSAMLObject(Body.class);

		body.getUnknownXMLObjects().add(xmlObject);

		envelope.setBody(body);

		return envelope;
	}
}
