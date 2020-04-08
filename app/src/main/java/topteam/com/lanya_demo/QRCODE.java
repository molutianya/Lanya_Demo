package topteam.com.lanya_demo;

public class QRCODE {

    int x;
    int y;
    int s;
    String msg;

    public QRCODE(int x, int y, int s, String msg) {
        this.x = x;
        this.y = y;
        this.s = s;
        this.msg = msg;
    }

    public String getQRCODE(){

        return "QRCODE "+x+","+y+","+"H"+","+s+","+"A"+","+"0"+","+"M2"+","+"S7"+","+"\""+msg+"\""+"\n";
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getS() {
        return s;
    }

    public void setS(int s) {
        this.s = s;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
