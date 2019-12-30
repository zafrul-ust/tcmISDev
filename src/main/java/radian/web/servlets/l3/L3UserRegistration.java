/**
 * Title:        MSDS Viewer
 * Copyright:    Copyright (c) 2001
 * Company:      Radian
 * @author Nawaz Shaik
 * @version
 *
 * This servelt basically is used to forward the request to the servlet UserRegistration.
 * This is done so that we can use it for many clients.
 */
package radian.web.servlets.l3;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class L3UserRegistration  extends radian.web.servlets.share.UserRegistration
{
   public String getClient()
   {
      //System.out.println("\n\n============ l3userregistartion check it is here....");
      return "L3";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new L3ServerResourceBundle();
   }
}