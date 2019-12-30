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
package radian.web.servlets.gd;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class GDUserRegistration  extends radian.web.servlets.share.UserRegistration
{
   public String getClient()
   {
      //System.out.println("\n\n============ gduserregistartion check it is here....");
      return "GD";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new GDServerResourceBundle();
   }
}