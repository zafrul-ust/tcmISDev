package radian.web.servlets.dcx;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class DCXSearchMsdsNew  extends radian.web.servlets.share.SearchMsdsNew
{
   public String getClient()
   {
      //System.out.println("\n\n============ DCXsearchmsds check it is here....");
      return "DCX";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new DCXServerResourceBundle();
   }
}