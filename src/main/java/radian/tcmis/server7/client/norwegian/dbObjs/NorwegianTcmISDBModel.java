package radian.tcmis.server7.client.norwegian.dbObjs;

	import radian.tcmis.server7.share.dbObjs.*;
	import radian.tcmis.server7.share.helpers.*;
	import radian.tcmis.server7.client.norwegian.helpers.NorwegianServerResourceBundle;

	public class NorwegianTcmISDBModel  extends TcmISDBModel {
	   public NorwegianTcmISDBModel(){
	      this("NORWEGIAN");
	   }

	   public NorwegianTcmISDBModel(String client){
	      super(client);
	   }

	   public NorwegianTcmISDBModel(String client,String logonVersion){
	      super(client,logonVersion);
	   }

	   public NorwegianTcmISDBModel(String client,String logonVersion,String locale){
	      super(client,logonVersion,locale);
	   }

		protected ServerResourceBundle getBundle(){
	      return (ServerResourceBundle) new NorwegianServerResourceBundle();
	   }

	}
