package radian.web.servlets.oma;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class OMASearchMsdsNew  extends radian.web.servlets.share.SearchMsdsNew
{
   public String getClient()
   {
      //System.out.println("\n\n============ OMAsearchmsds check it is here....");
      return "OMA";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new OMAServerResourceBundle();
   }
}