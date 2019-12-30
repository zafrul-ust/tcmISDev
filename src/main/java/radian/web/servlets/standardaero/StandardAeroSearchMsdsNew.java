package radian.web.servlets.standardaero;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class StandardAeroSearchMsdsNew  extends radian.web.servlets.share.SearchMsdsNew
{
   public String getClient()
   {
      //System.out.println("\n\n============ StandardAerosearchmsds check it is here....");
      return "STANDARD_AERO";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new StandardAeroServerResourceBundle();
   }
}