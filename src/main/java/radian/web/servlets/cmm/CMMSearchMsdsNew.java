package radian.web.servlets.cmm;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class CMMSearchMsdsNew  extends radian.web.servlets.share.SearchMsdsNew
{
   public String getClient()
   {
      //System.out.println("\n\n============ CMMsearchmsds check it is here....");
      return "CMM";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new CMMServerResourceBundle();
   }
}