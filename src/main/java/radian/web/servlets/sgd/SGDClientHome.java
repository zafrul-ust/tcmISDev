package radian.web.servlets.sgd;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class SGDClientHome extends radian.web.servlets.share.ClientHome
{
   public String getClient()
   {
      return "SGD";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new SGDServerResourceBundle();
   }
}