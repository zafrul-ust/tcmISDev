package radian.web.servlets.zf;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class ZFSearchMsdsNew  extends radian.web.servlets.share.SearchMsdsNew
{
   public String getClient()
   {
      //System.out.println("\n\n============ ZFsearchmsds check it is here....");
      return "ZF";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new ZFServerResourceBundle();
   }
}