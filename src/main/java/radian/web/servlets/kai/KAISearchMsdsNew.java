package radian.web.servlets.kai;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;
import radian.tcmis.server7.client.kai.helpers.KAIServerResourceBundle;

public class KAISearchMsdsNew  extends radian.web.servlets.share.SearchMsdsNew
{
   public String getClient()
   {
      //System.out.println("\n\n============ KAIsearchmsds check it is here....");
      return "KOREA_AEROSPACE_INDUSTRIES";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new KAIServerResourceBundle();
   }
}