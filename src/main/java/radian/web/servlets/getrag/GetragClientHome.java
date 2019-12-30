package radian.web.servlets.getrag;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class GetragClientHome extends radian.web.servlets.share.ClientHome
{
   public String getClient()
   {
      return "Getrag";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new GetragServerResourceBundle();
   }
}