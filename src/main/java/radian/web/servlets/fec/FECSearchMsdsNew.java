package radian.web.servlets.fec;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class FECSearchMsdsNew  extends radian.web.servlets.share.SearchMsdsNew
{
   public String getClient()
   {
      //System.out.println("\n\n============ FECsearchmsds check it is here....");
      return "FEC";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new FECServerResourceBundle();
   }
}