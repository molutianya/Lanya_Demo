package topteam.com.lanya_demo;

import java.io.UnsupportedEncodingException;

public class TEXT {

    int width;
    int height;
    int radiu;
    int fontHeight;
    int fontWidth;
    byte[] bb;

    String msg;

    public TEXT(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public TEXT(int width, int height, int radiu, int fontHeight, int fontWidth) {
        this.width = width;
        this.height = height;
        this.radiu = radiu;
        this.fontHeight = fontHeight;
        this.fontWidth = fontWidth;
    }

    public String getTEXT() {
        return "TEXT " + width + "," + height + "," + "FONT001" + "," + radiu + "," + fontHeight + "," + fontWidth+","+"\"";
    }

    public byte[] getGB2312() throws UnsupportedEncodingException {
        byte[] bytes = new byte[1024];
        bytes = msg.getBytes("GB2312");
        return bytes;
    }

    public byte[] getBb() {
        return bb;
    }

    public void setBb(byte[] bb) {
        this.bb = bb;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getRadiu() {
        return radiu;
    }

    public void setRadiu(int radiu) {
        this.radiu = radiu;
    }

    public int getFontHeight() {
        return fontHeight;
    }

    public void setFontHeight(int fontHeight) {
        this.fontHeight = fontHeight;
    }

    public int getFontWidth() {
        return fontWidth;
    }

    public void setFontWidth(int fontWidth) {
        this.fontWidth = fontWidth;
    }
}
