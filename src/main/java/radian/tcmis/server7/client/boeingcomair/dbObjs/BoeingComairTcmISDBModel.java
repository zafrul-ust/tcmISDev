package radian.tcmis.server7.client.boeingcomair.dbObjs;

import radian.tcmis.server7.client.boeingcomair.helpers.BoeingComairServerResourceBundle;
import radian.tcmis.server7.share.dbObjs.TcmISDBModel;
import radian.tcmis.server7.share.helpers.ServerResourceBundle;

public class BoeingComairTcmISDBModel  extends TcmISDBModel {
	public BoeingComairTcmISDBModel(){
		this("BOEING_CO");
	}

	public BoeingComairTcmISDBModel(String client){
		super(client);
	}

	public BoeingComairTcmISDBModel(String client,String logonVersion){
		super(client,logonVersion);
	}

	public BoeingComairTcmISDBModel(String client,String logonVersion,String locale){
		super(client,logonVersion,locale);
	}

	protected ServerResourceBundle getBundle(){
		return new BoeingComairServerResourceBundle();
	}

}