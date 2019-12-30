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
package radian.web.servlets.swa;

import radian.tcmis.server7.share.helpers.*;

public class SWAUserRegistration  extends radian.web.servlets.share.UserRegistration
{
   public String getClient()
   {
      //System.out.println("\n\n============ swauserregistartion check it is here....");
      return "SWA";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new SWAServerResourceBundle();
   }
}