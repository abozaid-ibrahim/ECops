package co.ecops.platform.model;

/**
 * Created by ZEID on 7/14/2015.
 */
public class Post {
    boolean status;
    String msg;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
