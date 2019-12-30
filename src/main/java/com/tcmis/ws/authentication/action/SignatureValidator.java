package com.tcmis.ws.authentication.action;

import java.security.Key;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.xml.security.signature.XMLSignature;
import org.apache.xml.security.signature.XMLSignatureException;
import org.opensaml.xml.security.SecurityHelper;
import org.opensaml.xml.security.credential.Credential;
import org.opensaml.xml.signature.Signature;
import org.opensaml.xml.signature.impl.SignatureImpl;
import org.opensaml.xml.validation.ValidationException;
import org.opensaml.xml.validation.Validator;

public class SignatureValidator
  implements Validator<Signature>
{
  private final Log log = LogFactory.getLog(SignatureValidator.class);
  private Credential validationCredential;

  public SignatureValidator(Credential validatingCredential)
  {
    validationCredential = validatingCredential;
  }

  public void validate(Signature signature) throws ValidationException
  {
    log.debug("Attempting to validate signature using key from supplied credential");

    XMLSignature xmlSig = buildSignature(signature);
    xmlSig.setFollowNestedManifests(false);

    Key validationKey = SecurityHelper.extractVerificationKey(validationCredential);
    if (validationKey == null) {
      log.debug("Supplied credential contained no key suitable for signature validation");
      throw new ValidationException("No key available to validate signature");
    }

    log.debug("Validating signature with signature algorithm URI: " + signature.getSignatureAlgorithm());
    log.debug("Validation credential key algorithm '" + validationKey.getAlgorithm() +"', key instance class '" + validationKey.getClass().getName() + "'");
    try
    {
    	xmlSig.setFollowNestedManifests(false);
      if (xmlSig.checkSignatureValue(validationKey)) {
        log.debug("Signature validated with key from supplied credential");
        return;
      }
    } catch (XMLSignatureException e) {
      throw new ValidationException("Unable to evaluate key against signature", e);
    }

    log.debug("Signature did not validate against the credential's key");

    throw new ValidationException("Signature did not validate against the credential's key");
  }

  protected XMLSignature buildSignature(Signature signature)
  {
    log.debug("Creating XMLSignature object");

    return ((SignatureImpl)signature).getXMLSignature();
  }
}