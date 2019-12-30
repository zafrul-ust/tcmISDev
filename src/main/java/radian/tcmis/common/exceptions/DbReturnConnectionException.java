package radian.tcmis.common.exceptions;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class DbReturnConnectionException
    extends BaseException {
  public DbReturnConnectionException(String message) {
    super(message);
    setMessageKey(message);
  }
}
