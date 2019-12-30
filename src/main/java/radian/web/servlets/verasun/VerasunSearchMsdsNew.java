package radian.web.servlets.verasun;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class VerasunSearchMsdsNew  extends radian.web.servlets.share.SearchMsdsNew
{
   public String getClient()
   {
      //System.out.println("\n\n============ Verasunsearchmsds check it is here....");
      return "Verasun";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new VerasunServerResourceBundle();
   }
}