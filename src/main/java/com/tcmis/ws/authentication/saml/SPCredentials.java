package com.tcmis.ws.authentication.saml;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opensaml.xml.security.Criteria;
import org.opensaml.xml.security.CriteriaSet;
import org.opensaml.xml.security.credential.Credential;
import org.opensaml.xml.security.credential.KeyStoreCredentialResolver;
import org.opensaml.xml.security.criteria.EntityIDCriteria;
import org.opensaml.xml.security.x509.BasicX509Credential;


public class SPCredentials {
	protected static Log						log				= LogFactory.getLog(SPCredentials.class);
	private static final String		KEY_STORE_PASSWORD			= "p1ckl34me";
	private static final String		KEY_STORE_ENTRY_PASSWORD	= "c0ffee4u";
	private static final String		KEY_STORE_PATH				= "/saml-keystore.jks";
	//private static final String		KEY_ENTRY_ID				= "tcmis-saml";
	private static final String		KEY_ENTRY_ID				= "tcmis-saml-selfsigned";

	private static final Credential	credential;

	static {
		try {
			KeyStore keystore = readKeystoreFromFile(KEY_STORE_PATH, KEY_STORE_PASSWORD);
			Map<String, String> passwordMap = new HashMap<String, String>();
			passwordMap.put(KEY_ENTRY_ID, KEY_STORE_ENTRY_PASSWORD);
			KeyStoreCredentialResolver resolver = new KeyStoreCredentialResolver(keystore, passwordMap);

			Criteria criteria = new EntityIDCriteria(KEY_ENTRY_ID);
			CriteriaSet criteriaSet = new CriteriaSet(criteria);

			credential = resolver.resolveSingle(criteriaSet);
		}
		catch (org.opensaml.xml.security.SecurityException e) {
			throw new RuntimeException("Something went wrong reading credentials", e);
		}
	}

	private static KeyStore readKeystoreFromFile(String pathToKeyStore, String keyStorePassword) {
		try {
			KeyStore keystore = KeyStore.getInstance("JKS");
			InputStream inputStream = SPCredentials.class.getResourceAsStream(pathToKeyStore);
			keystore.load(inputStream, keyStorePassword.toCharArray());
			inputStream.close();
			return keystore;
		}
		catch (Exception e) {
			throw new RuntimeException("Something went wrong reading keystore", e);
		}
	}

	public static Credential getCredential() {
		return credential;
	}

	public static Credential getCredentialFromString(String certificate) {
		BasicX509Credential credential = null;
		try {
			CertificateFactory fty = CertificateFactory.getInstance("X.509");
			ByteArrayInputStream bais = new ByteArrayInputStream(Base64.decodeBase64(certificate.getBytes()));
			X509Certificate x509Cert = (X509Certificate) fty.generateCertificate(bais);
			credential = new BasicX509Credential();
			credential.setEntityCertificate(x509Cert);
		}
		catch (Exception e) {
			log.error("Error reading X.509 certificate from String", e);
		}
		return credential;
	}
}
