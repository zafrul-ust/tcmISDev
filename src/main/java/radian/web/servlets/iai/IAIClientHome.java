package radian.web.servlets.iai;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class IAIClientHome extends radian.web.servlets.share.ClientHome
{
   public String getClient()
   {
      return "IAI";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new IAIServerResourceBundle();
   }
}