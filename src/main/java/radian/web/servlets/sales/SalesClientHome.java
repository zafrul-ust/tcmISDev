package radian.web.servlets.sales;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class SalesClientHome extends radian.web.servlets.share.ClientHome
{
   public String getClient()
   {
      return "Sales";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new SalesServerResourceBundle();
   }
}