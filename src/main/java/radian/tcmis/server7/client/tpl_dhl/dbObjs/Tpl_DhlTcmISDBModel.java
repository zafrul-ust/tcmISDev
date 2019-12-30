package radian.tcmis.server7.client.tpl_dhl.dbObjs;

	import radian.tcmis.server7.share.dbObjs.*;
	import radian.tcmis.server7.share.helpers.*;
	import radian.tcmis.server7.client.tpl_dhl.helpers.Tpl_DhlServerResourceBundle;

	public class Tpl_DhlTcmISDBModel  extends TcmISDBModel {
	   public Tpl_DhlTcmISDBModel(){
	      this("TPL_DHL");
	   }

	   public Tpl_DhlTcmISDBModel(String client){
	      super(client);
	   }

	   public Tpl_DhlTcmISDBModel(String client,String logonVersion){
	      super(client,logonVersion);
	   }

	   public Tpl_DhlTcmISDBModel(String client,String logonVersion,String locale){
	      super(client,logonVersion,locale);
	   }

		protected ServerResourceBundle getBundle(){
	      return (ServerResourceBundle) new Tpl_DhlServerResourceBundle();
	   }

	}
