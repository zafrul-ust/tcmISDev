package com.tcmis.client.openCustomer.process;

import org.apache.commons.logging.*;
import com.tcmis.common.framework.*;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import java.util.*;

import com.tcmis.common.util.EncryptHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.StringHandler;
import com.tcmis.common.admin.beans.ErrorBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.beans.PersonnelPasswordHistoryBean;
import com.tcmis.common.admin.beans.PersonnelPasswordRuleViewBean;
import com.tcmis.common.admin.process.PasswordProcess;

/******************************************************************************
 * Process for changing password
 * @version 1.0
 *****************************************************************************/
public class ChangePasswordProcess extends BaseProcess {
  Log log = LogFactory.getLog(this.getClass());
  private PersonnelBean personnelBean;

  public ChangePasswordProcess(String client, PersonnelBean bean) {
	    super(client);
	    this.personnelBean = bean;
  }

  public ErrorBean resetPassword(PersonnelBean personnelBean, Locale locale) {
    ErrorBean errorBean = new ErrorBean();
    ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
    try {
      PasswordProcess process = new PasswordProcess(this.getClient(), personnelBean);
      boolean passwordIsNew = isPasswordNew(personnelBean.getClearTextPassword());
      if (passwordIsNew) {
		  PersonnelPasswordRuleViewBean passwordRuleBean = process.getPersonnelPasswordRule();
		  checkPasswordRule(process,passwordRuleBean,personnelBean.getClearTextPassword(),errorBean,library);
        if (!errorBean.getShowError()) {
          if (!process.updatePassword(passwordRuleBean,personnelBean.getClearTextPassword(), null)) {
            errorBean.setShowError(true);
            errorBean.setHeader(library.getString("errors.header"));
          }
        }
      } else {
        errorBean.setShowError(true);
        errorBean.setMessage2(library.getString("error.password.reuse"));
      }
    }catch (Exception e) {
      log.error("Base Exception in resetPassword: " + e);
      errorBean.setShowError(true);
      errorBean.setHeader(library.getString("errors.header"));
    }
    return errorBean;
  } //end of method

  public ErrorBean changePassword(PersonnelBean personnelBean, String oldPassword, String newPassword, byte[] salt, Locale locale) {
    ErrorBean errorBean = new ErrorBean();
    ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
    try {
      PasswordProcess process = new PasswordProcess(this.getClient(), personnelBean);
      boolean passwordMatch = process.isPasswordMatch(oldPassword);
      boolean passwordIsNew = isPasswordNew(newPassword);
      if (passwordMatch && passwordIsNew) {
		  PersonnelPasswordRuleViewBean passwordRuleBean = process.getPersonnelPasswordRule();
		  checkPasswordRule(process,passwordRuleBean,newPassword,errorBean,library);
        if (!errorBean.getShowError()) {
          if (!process.updatePassword(passwordRuleBean,newPassword, salt)) {
            errorBean.setShowError(true);
            errorBean.setHeader(library.getString("errors.header"));
          }
        }
      } else {
        errorBean.setShowError(true);
        if (!passwordMatch) {
          errorBean.setMessage1(library.getString("error.password.oldpasswordinvalid"));
        }
        if (!passwordIsNew) {
          errorBean.setMessage2(library.getString("error.password.reuse"));
        }
      }
    }catch (Exception e) {
      log.error("Base Exception in changePassword: " + e);
      errorBean.setShowError(true);
      errorBean.setHeader(library.getString("errors.header"));
    }
    return errorBean;
  } //end of method

  public void checkPasswordRule(PasswordProcess passwordProcess, PersonnelPasswordRuleViewBean passwordRuleBean, String newPassword, ErrorBean errorBean, ResourceLibrary library) {
    try {
      //PersonnelPasswordRuleViewBean bean = passwordProcess.getPersonnelPasswordRule();
      if (passwordRuleBean != null) {
        boolean failed = false;
        //first check for total length
        if (newPassword.length() < passwordRuleBean.getTotalLength().intValue()) {
          failed = true;
        }
        //check to see if password meets at least the number of alpha_characters, numeric_characters and special_characters
        String[] breakupPassword = passwordProcess.breakingStringIntoDigitLetterAndOther(newPassword);

        //check apha_characters
        if (breakupPassword[PasswordProcess.OUTPUT_ALPHA_UPPER_INDEX].length()+breakupPassword[PasswordProcess.OUTPUT_ALPHA_LOWER_INDEX].length() < passwordRuleBean.getAlphaCharacters().intValue()) {
          failed = true;
        }
        //check alpha_upper_characters
        if (breakupPassword[PasswordProcess.OUTPUT_ALPHA_UPPER_INDEX].length() < passwordRuleBean.getAlphaUpperCharacters().intValue()) {
          failed = true;
        }
        //check alpha_lower_characters
        if (breakupPassword[PasswordProcess.OUTPUT_ALPHA_LOWER_INDEX].length() < passwordRuleBean.getAlphaLowerCharacters().intValue()) {
          failed = true;
        }
        //check numeric_characters
        if (breakupPassword[PasswordProcess.OUTPUT_NUMBER_INDEX].length() < passwordRuleBean.getNumericCharacters().intValue()) {
          failed = true;
        }
        //check special_characters
        if (breakupPassword[PasswordProcess.OUTPUT_SPECIAL_CHARACTER_INDEX].length() < passwordRuleBean.getSpecialCharacters().intValue()) {
          failed = true;
        }
        //
        if(failed) {
          errorBean.setShowError(true);
          errorBean.setHeader(library.getString("error.password.requirement")+":");
          //total length
          if (passwordRuleBean.getTotalLength().intValue() > 1) {
            errorBean.setMessage1(library.getString("error.password.atleast") + " " + passwordRuleBean.getTotalLength() + " " + library.getString("error.password.totalcharacters"));
          }else if (passwordRuleBean.getTotalLength().intValue() == 1) {
            errorBean.setMessage1(library.getString("error.password.atleast") + " " + passwordRuleBean.getTotalLength() + " " + library.getString("error.password.totalcharacter"));
          }
          //alphabetic
          if (passwordRuleBean.getAlphaCharacters().intValue() > 1) {
            errorBean.setMessage2(library.getString("error.password.atleast") + " " + passwordRuleBean.getAlphaCharacters() + " " + library.getString("error.password.totalalphabetics"));
          }else if (passwordRuleBean.getAlphaCharacters().intValue() == 1) {
            errorBean.setMessage2(library.getString("error.password.atleast") + " " + passwordRuleBean.getAlphaCharacters() + " " + library.getString("error.password.totalalphabetic"));
          }
          //upper alphabetic
          if (passwordRuleBean.getAlphaUpperCharacters().intValue() > 1) {
            errorBean.setMessage3(library.getString("error.password.atleast") + " " + passwordRuleBean.getAlphaUpperCharacters() + " " + library.getString("error.password.totalupperalphabetics"));
          }else if (passwordRuleBean.getAlphaUpperCharacters().intValue() == 1) {
            errorBean.setMessage3(library.getString("error.password.atleast") + " " + passwordRuleBean.getAlphaUpperCharacters() + " " + library.getString("error.password.totalupperalphabetic"));
          }
          //lower alphabetic
          if (passwordRuleBean.getAlphaLowerCharacters().intValue() > 1) {
            errorBean.setMessage4(library.getString("error.password.atleast") + " " + passwordRuleBean.getAlphaLowerCharacters() + " " + library.getString("error.password.totalloweralphabetics"));
          }else if (passwordRuleBean.getAlphaLowerCharacters().intValue() == 1) {
            errorBean.setMessage4(library.getString("error.password.atleast") + " " + passwordRuleBean.getAlphaLowerCharacters() + " " + library.getString("error.password.totalloweralphabetic"));
          }
          //numeric
          if (passwordRuleBean.getNumericCharacters().intValue() > 1) {
            errorBean.setMessage5(library.getString("error.password.atleast") + " " + passwordRuleBean.getNumericCharacters() + " " + library.getString("error.password.totalnumerics"));
          }else if (passwordRuleBean.getNumericCharacters().intValue() == 1) {
            errorBean.setMessage5(library.getString("error.password.atleast") + " " + passwordRuleBean.getNumericCharacters() + " " + library.getString("error.password.totalnumeric"));
          }
          //special character
          if (passwordRuleBean.getSpecialCharacters().intValue() > 1) {
            errorBean.setMessage6(library.getString("error.password.atleast") + " " + passwordRuleBean.getSpecialCharacters() + " " + library.getString("error.password.totalothers"));
          }else if (passwordRuleBean.getSpecialCharacters().intValue() == 1) {
            errorBean.setMessage6(library.getString("error.password.atleast") + " " + passwordRuleBean.getSpecialCharacters() + " " + library.getString("error.password.totalother"));
          }
        }
      }
    } catch (Exception e) {
      log.error("Base Exception in checkPasswordRule: " + e);
    }
  } //end of method
  
  private boolean isPasswordNew(String newPassword) throws BaseException {
	    boolean result = true;
	    try {
	      PasswordProcess process = new PasswordProcess(this.getClient(), personnelBean);
	      PersonnelPasswordRuleViewBean passwordRuleBean = process.getPersonnelPasswordRule();
	      if (passwordRuleBean != null) {
	    	DbManager dbManager = new DbManager(getClient(), getLocale());
	  		GenericSqlFactory factory = new GenericSqlFactory(dbManager, new PersonnelPasswordHistoryBean());
	  		
	  		StringBuilder query = new StringBuilder("select personnel_id,date_changed,last_password from table (pkg_open_customer.fx_get_personnel_pwd_hist_data(");
	  		query.append(StringHandler.emptyIfNull(personnelBean.getPersonnelId()));
	  		query.append(")) union select personnel_id, sysdate date_changed, password last_password from table (pkg_open_customer.fx_get_personnel_data(");
	  		query.append(StringHandler.emptyIfNull(personnelBean.getPersonnelId()));
	  		query.append(")) order by date_changed desc");
	  		
	  		Collection passwordHistoryCol = factory.selectQuery(query.toString());
	        
	        if (passwordHistoryCol.size() > 0) {
	          Iterator myIterator = passwordHistoryCol.iterator();
	          int count = 0;
	          while (myIterator.hasNext() && count < passwordRuleBean.getReuseCount().intValue()) {
	            PersonnelPasswordHistoryBean passwordHistoryBean = (PersonnelPasswordHistoryBean) myIterator.next();
                  //days_lapsed_for_reuse
                  Calendar cal = Calendar.getInstance();
                  if (passwordRuleBean.getDaysLapsedForReuse() != null) {
                        cal.add(Calendar.DATE,-passwordRuleBean.getDaysLapsedForReuse().intValue());
                  }
                  if (!StringHandler.isBlankString(passwordHistoryBean.getLastPassword()) &&
                      passwordHistoryBean.getDateChanged().getTime() > cal.getTime().getTime()) {
                	  byte[] salt = EncryptHandler.extractSalt(personnelBean.getPassword());
	  				if (passwordHistoryBean.getLastPassword().equals(EncryptHandler.pbkdf2Encrypt(newPassword, salt))) {
		                result = false;
		                break;
		            }
	  				else if (passwordHistoryBean.getLastPassword().equals(EncryptHandler.encrypt(newPassword))) {
		                result = false;
		                break;
	  				}             
		          }
	            count++;
	          }
	        }
	      }
	    } catch (Exception e) {
	      log.error("Base Exception in isPasswordNew: " + e);
	      result = false;
	    }
	    return result;
  } //end of method
  


} //end of class
