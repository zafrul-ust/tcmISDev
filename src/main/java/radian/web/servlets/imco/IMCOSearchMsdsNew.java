package radian.web.servlets.imco;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class IMCOSearchMsdsNew  extends radian.web.servlets.share.SearchMsdsNew
{
   public String getClient()
   {
      //System.out.println("\n\n============ IMCOsearchmsds check it is here....");
      return "IMCO";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new IMCOServerResourceBundle();
   }
}