package radian.web.servlets.qos;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class QOSSearchMsdsNew  extends radian.web.servlets.share.SearchMsdsNew
{
   public String getClient()
   {
      //System.out.println("\n\n============ QOSsearchmsds check it is here....");
      return "QOS";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new QOSServerResourceBundle();
   }
}