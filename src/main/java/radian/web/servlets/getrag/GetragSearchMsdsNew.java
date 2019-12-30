package radian.web.servlets.getrag;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class GetragSearchMsdsNew  extends radian.web.servlets.share.SearchMsdsNew
{
   public String getClient()
   {
      //System.out.println("\n\n============ Getragsearchmsds check it is here....");
      return "Getrag";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new GetragServerResourceBundle();
   }
}