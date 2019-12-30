package radian.web.servlets.bae;

import radian.tcmis.server7.share.helpers.*;

public class BAEClientHome extends radian.web.servlets.share.ClientHome
{
   public String getClient()
   {
      return "BAE";
   }
   protected ServerResourceBundle getBundle()
   {
      return (ServerResourceBundle) new BAEServerResourceBundle();
   }
}