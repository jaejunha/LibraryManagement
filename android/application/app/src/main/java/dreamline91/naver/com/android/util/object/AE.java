package dreamline91.naver.com.android.util.object;

/**
 * Created by KYG on 2017-11-27 027.
 */

public class AE {
    public String appos = "";
    public String appId = "";
    public String aeId = "";
    public String appName = "";
    public String pointOfAccess = "";
    public String appPort = "";
    public String appProtocol = "";
    public String tasPort = "";
    public String cilimit = "";

    public void setAEid(String aeid){
        this.aeId = aeid;
    }
    public void setAppName(String appname){
        this.appName = appname;
    }
    public String getAEid() {
        return this.aeId;
    }
    public String getappName(){
        return this.appName;
    }
}