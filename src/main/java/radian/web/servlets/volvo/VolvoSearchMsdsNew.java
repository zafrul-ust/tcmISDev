package radian.web.servlets.volvo;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class VolvoSearchMsdsNew  extends radian.web.servlets.share.SearchMsdsNew
{
   public String getClient()
   {
      //System.out.println("\n\n============ Volvosearchmsds check it is here....");
      return "Volvo";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new VolvoServerResourceBundle();
   }
}