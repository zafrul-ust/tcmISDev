/**
 * Title:        MSDS Viewer
 * Copyright:    Copyright (c) 2001
 * Company:      Radian
 * @author Nawaz Shaik,Rajendar Rajput
 * @version
 *
 * This servelt basically is used to forward the request to the servlet UserRegistration.
 * This is done so that we can use it for many clients.
 */
package radian.web.servlets.drs;

import radian.tcmis.server7.share.helpers.*;

public class DRSUserRegistration  extends radian.web.servlets.share.UserRegistration
{
   public String getClient()
   {
      //System.out.println("\n\n============ drsuserregistartion check it is here....");
      return "DRS";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new DRSServerResourceBundle();
   }
}