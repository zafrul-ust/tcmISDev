package radian.web.servlets.sales;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class SalesBuildSearchPageNew  extends radian.web.servlets.share.BuildSearchPageNew
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