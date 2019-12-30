package radian.tcmis.client.share.httpCgi;

public abstract class StatusDlg extends Thread {
    public StatusDlg(String label){
      super(label);
    }
    protected abstract void setCount(int n);
    protected abstract void setLen(int m);
}