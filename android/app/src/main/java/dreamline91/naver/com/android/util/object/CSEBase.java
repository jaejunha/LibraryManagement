package dreamline91.naver.com.android.util.object;

/**
 * Created by KYG on 2017-11-27 027.
 */

public class CSEBase {
    public String CSEHostAddress = "";
    public String CSEPort = "";
    public String CSEName = "";
    public String MQTTPort = "";

    public void setInfo(String param1, String param2, String param3, String param4 ){
        this.CSEHostAddress = param1;
        this.CSEPort = param2;
        this.CSEName = param3;
        this.MQTTPort = param4;
    }
    public String getHost() {
        return this.CSEHostAddress;
    }
    public String getPort() {
        return this.CSEPort;
    }
    public String getCSEName() {
        return this.CSEName;
    }
    public String getMQTTPort() {
        return this.MQTTPort;
    }
    public String getServiceUrl(){
        return "http://"+ this.CSEHostAddress+":"+this.CSEPort+"/"+this.CSEName;
    }
}
