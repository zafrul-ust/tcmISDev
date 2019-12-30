package radian.web.servlets.l3;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class L3SearchMsdsNew  extends radian.web.servlets.share.SearchMsdsNew
{
   public String getClient()
   {
      //System.out.println("\n\n============ L3searchmsds check it is here....");
      return "L3";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new L3ServerResourceBundle();
   }
}