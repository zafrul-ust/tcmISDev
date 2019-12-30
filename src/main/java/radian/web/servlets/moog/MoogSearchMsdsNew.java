package radian.web.servlets.moog;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class MoogSearchMsdsNew  extends radian.web.servlets.share.SearchMsdsNew
{
   public String getClient()
   {
      //System.out.println("\n\n============ moogsearchmsds check it is here....");
      return "MOOG";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new MoogServerResourceBundle();
   }
}