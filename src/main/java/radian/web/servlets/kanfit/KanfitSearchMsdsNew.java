package radian.web.servlets.kanfit;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class KanfitSearchMsdsNew  extends radian.web.servlets.share.SearchMsdsNew
{
   public String getClient()
   {
      //System.out.println("\n\n============ Kanfitsearchmsds check it is here....");
      return "Kanfit";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new KanfitServerResourceBundle();
   }
}