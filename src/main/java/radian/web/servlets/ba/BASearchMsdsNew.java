package radian.web.servlets.ba;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class BASearchMsdsNew  extends radian.web.servlets.share.SearchMsdsNew
{
   public String getClient()
   {
      //System.out.println("\n\n============ BAsearchmsds check it is here....");
      return "BA";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new BAServerResourceBundle();
   }
}