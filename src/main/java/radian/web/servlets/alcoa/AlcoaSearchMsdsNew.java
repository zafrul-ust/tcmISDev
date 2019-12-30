package radian.web.servlets.alcoa;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class AlcoaSearchMsdsNew  extends radian.web.servlets.share.SearchMsdsNew
{
   public String getClient()
   {
      //System.out.println("\n\n============ Alcoasearchmsds check it is here....");
      return "Alcoa";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new AlcoaServerResourceBundle();
   }
}