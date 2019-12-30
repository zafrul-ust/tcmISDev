package radian.web.servlets.sales;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class SalesSearchMsdsNew  extends radian.web.servlets.share.SearchMsdsNew
{
   public String getClient()
   {
      //System.out.println("\n\n============ Salessearchmsds check it is here....");
      return "Sales";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new SalesServerResourceBundle();
   }
}