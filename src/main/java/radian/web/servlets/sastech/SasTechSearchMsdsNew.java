package radian.web.servlets.sastech;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class SasTechSearchMsdsNew  extends radian.web.servlets.share.SearchMsdsNew
{
   public String getClient()
   {
      //System.out.println("\n\n============ BAsearchmsds check it is here....");
      return "SAS_TECH";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new SasTechServerResourceBundle();
   }
}