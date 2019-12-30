package radian.web.servlets.ford;

import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class FordClientHome extends radian.web.servlets.share.ClientHome
{
   public String getClient()
   {
      return "Ford";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new FordServerResourceBundle();
   }
}