package radian.web.servlets.bai;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class BAISearchMsdsNew  extends radian.web.servlets.share.SearchMsdsNew
{
   public String getClient()
   {
      //System.out.println("\n\n============ BAIsearchmsds check it is here....");
      return "BAI";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new BAIServerResourceBundle();
   }
}