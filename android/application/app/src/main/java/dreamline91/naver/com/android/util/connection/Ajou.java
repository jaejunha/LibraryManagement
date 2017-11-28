package dreamline91.naver.com.android.util.connection;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dreamline91.naver.com.android.util.object.User;

/**
 * Created by dream on 2017-11-24.
 */

public class Ajou {
    private final static String MOBILE = "https://mb.ajou.ac.kr";
    private final static String LIBAPP = "http://libapp.ajou.ac.kr";
    private final static String GET_PROFILE = "/mobile/M03/M03_010_010.es";
    private final static String REQUEST_PICTURE = "/QrCodeService/GetPhotoImg.svc/GetUserPhotoAJOU?Loc=AJOU&Idno=";
    private final static String GET_PICTURE = "/KCPPhoto//PHO_";

    public String loginAjou(String id, String pwd) {
        try {
            String cookie = postLogin(new URL(MOBILE + "/mobile/login.json"), id, pwd);

            return cookie;
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    public User printUser(String cookie) {
        User user = new User();
        try {
            String line = null;
            Pattern pattern;
            Matcher matcher;
            BufferedReader rd = getSimple(new URL(MOBILE + GET_PROFILE), cookie);
            while ((line = rd.readLine()) != null) {
                if (line.indexOf("성명") > -1) {
                    line = rd.readLine();
                    user.setName(line.substring(line.indexOf("<td>") + 4, line.indexOf("</td>")));
                } else if (line.indexOf("학번") > -1) {
                    line = rd.readLine();
                    pattern = Pattern.compile("[0-9]+");
                    matcher = pattern.matcher(line);
                    if (matcher.find())
                        user.setNumber(Integer.parseInt(matcher.group(0)));
                } else if (line.indexOf("전공") > -1) {
                    line = rd.readLine();
                    user.setMajor(line.substring(line.indexOf("<td>") + 4, line.indexOf("</td>")));
                }
            }
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return user;
    }

    public Bitmap printPicture(int number, int size) {
        try {
            getSimple(new URL(LIBAPP+REQUEST_PICTURE+number),"");
            Bitmap bitmap = BitmapFactory.decodeStream(new BufferedInputStream(HTTP.makeConnection(new URL(LIBAPP+GET_PICTURE+number+".jpg")).getInputStream()));
            return Bitmap.createScaledBitmap(bitmap, size,size, true);
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    private String postLogin(URL url, String id, String pwd) {
        try {
            HttpURLConnection con = HTTP.makeConnection(url);
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
            wr.write(loginPara(id, pwd));
            wr.flush();

            String cookie = con.getHeaderFields().get("Set-Cookie").toString();
            if(cookie.indexOf("JSESS")<0)
                return null;
            cookie = cookie.substring(cookie.indexOf("JSESS"));
            cookie = cookie.substring(0, cookie.indexOf(";"));
            con.disconnect();
            return cookie;
        } catch (IOException | IndexOutOfBoundsException e) {
            // TODO Auto-generated catch block
            return null;
        }
    }

    private BufferedReader getSimple(URL url, String cookie) {
        try {
            HttpURLConnection con = HTTP.makeConnection(url);
            con.setDoOutput(false);

            if(cookie.equals("") == false)
                con.setRequestProperty("Cookie", cookie);
            con.setRequestMethod("GET");

            return new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    private String loginPara(String id, String pwd) {
        String parameter;
        try {
            parameter = URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8");
            parameter += "&" + URLEncoder.encode("passwd", "UTF-8") + "=" + URLEncoder.encode(pwd, "UTF-8");
            parameter += "&" + URLEncoder.encode("rememberMe", "UTF-8") + "=" + URLEncoder.encode("N", "UTF-8");
            parameter += "&" + URLEncoder.encode("platformType", "UTF-8") + "=" + URLEncoder.encode("A", "UTF-8");
            parameter += "&" + URLEncoder.encode("deviceToken", "UTF-8") + "=";
            return parameter;
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }
}
