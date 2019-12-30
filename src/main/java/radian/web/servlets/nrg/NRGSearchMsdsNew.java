package radian.web.servlets.nrg;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class NRGSearchMsdsNew  extends radian.web.servlets.share.SearchMsdsNew
{
   public String getClient()
   {
      //System.out.println("\n\n============ NRGsearchmsds check it is here....");
      return "NRG";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new NRGServerResourceBundle();
   }
}