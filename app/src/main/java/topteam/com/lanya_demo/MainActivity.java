package topteam.com.lanya_demo;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.tscdll.TSCActivity;
import com.example.tscdll.TscWifiActivity;
import com.sun.jna.Library;
import com.sun.jna.Native;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    EditText inputMac_v;  //蓝牙的MAC地址
    EditText inputWidth_v; //标签的长度
    EditText inputHeight_v; //标签的高度
    CheckBox check_v; //记住mac
    Button print_v; //打印测试
    String commit = "\"\n";
    int x;
    int y;
    static String mac;
    boolean flg = false;

    final TSCActivity tscDll = new TSCActivity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        initView();
        check_v.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                flg = isChecked;
            }
        });

        //获取上次保存的MAC
        getMac();


    }

    /**
     * 初始化所有控件
     */
    private void initView() {
        inputMac_v = findViewById(R.id.inputmac);
        inputWidth_v = findViewById(R.id.inputwidth);
        inputHeight_v = findViewById(R.id.inputheight);
        check_v = findViewById(R.id.ckeck);
        print_v = findViewById(R.id.print);
    }

    /**
     * 保存蓝牙的MAC地址
     */
    private void saveMac(String mac) {
        /**
         *  获取SharedPreferences 对象
         *  data 保存的文件名称
         *  MODE_PRIVATE 保存的模式，默认  会以覆盖的形式保存
         */
        SharedPreferences.Editor editor = getSharedPreferences("macData", MODE_PRIVATE).edit();
        editor.putString("mac", mac); //
        editor.putBoolean("state", flg);
        editor.apply(); //提交数据
    }

    /**
     * 读取蓝牙的MAC
     */
    private void getMac() {
        /**
         * 读取保存的数据
         * data 从哪个文件读取
         * MODE_PRIVATE 读取的形式
         */
        SharedPreferences preferences = getSharedPreferences("macData", MODE_PRIVATE);
        mac = preferences.getString("mac", "");
        if (preferences.getBoolean("state", false)) {
            inputMac_v.setText(mac);
            check_v.setChecked(true);
        }

    }


    /**
     * 打印测试的信息
     *
     * @param mac 蓝牙的mac地址
     * @param x   标签的长度
     * @param y   标签的高度
     * @throws UnsupportedEncodingException
     */
    private void printTest(String mac, int x, int y) throws UnsupportedEncodingException {
        //连接蓝牙打印机  参数：蓝牙的mac地址
        tscDll.openport(mac);
        //打印机设置
        tscDll.setup(x, y, 4, 12, 0, 2, 0);
        //出纸方向设置
        tscDll.sendcommand("DIRECTION 1,0");
        tscDll.sendcommand("\n");
        tscDll.clearbuffer(); //清除缓存
        //画表格
        tscDll.sendcommand((new BOX(20, 10, 640, 950, 4)).getBOX());
        //第1行
        tscDll.sendcommand(new BAR(20, 90, 620, 3).getBAR());
        //第2行
        tscDll.sendcommand(new BAR(20, 155, 620, 3).getBAR());
        //第3行
        tscDll.sendcommand(new BAR(20, 225, 620, 3).getBAR());
        //第4行
        tscDll.sendcommand(new BAR(20, 285, 620, 3).getBAR());
        //第5行
        tscDll.sendcommand(new BAR(20, 350, 620, 3).getBAR());
        //中间分割线 竖线
        tscDll.sendcommand(new BAR(280, 95, 3, 250).getBAR());
        //中间分割线  横线
        tscDll.sendcommand(new BAR(20, 360, 620, 3).getBAR());
        //第6行
        tscDll.sendcommand(new BAR(20, 425, 620, 3).getBAR());
        //第7行
        tscDll.sendcommand(new BAR(20, 490, 620, 3).getBAR());
        //分割线 竖线
        tscDll.sendcommand(new BAR(540, 425, 3, 185).getBAR());
        //第8行
        tscDll.sendcommand(new BAR(20, 555, 620, 3).getBAR());
        //第9行
        tscDll.sendcommand(new BAR(20, 610, 620, 3).getBAR());
        //第10行
        tscDll.sendcommand(new BAR(20, 685, 410, 3).getBAR());
        //第11行
        tscDll.sendcommand(new BAR(20, 750, 410, 3).getBAR());
        //第12行
        tscDll.sendcommand(new BAR(20, 800, 410, 3).getBAR());
        //第13行
        tscDll.sendcommand(new BAR(20, 850, 410, 3).getBAR());
        //第14行
        tscDll.sendcommand(new BAR(20, 910, 620, 3).getBAR());
        //中间分割线 竖线
        tscDll.sendcommand(new BAR(220, 360, 3, 550).getBAR());
        //中间分割线 竖线
        tscDll.sendcommand(new BAR(430, 425, 3, 485).getBAR());
        //圆形和二维码的分割线
        tscDll.sendcommand(new BAR(430, 750, 210, 3).getBAR());


        //打印第一行
        String span1 = "珠海协隆塑胶电子有限公司";
        byte[] b1 = new byte[1024];
        b1 = span1.getBytes("GB2312");
        tscDll.sendcommand("TEXT 120,20,\"FONT001\",0,3,3,\"");
        tscDll.sendcommand(b1);
        tscDll.sendcommand("\"\n");
        tscDll.printerfont(25, 70, "2", 0, 1, 1, "Zhuhai Xielong Plastics & Electronics Co,Ltd.");
        //打印第2行
        String span2 = "客户 Customer";
        byte[] b2 = new byte[1024];
        b2 = span2.getBytes("GB2312");
        tscDll.sendcommand("TEXT 25,110,\"FONT001\",0,2,2,\"");
        tscDll.sendcommand(b2);
        tscDll.sendcommand("\"\n");
        String span02 = "Ninestar";
        byte[] b02 = new byte[1024];
        b02 = span02.getBytes("GB2312");
        tscDll.sendcommand("TEXT 300,110,\"FONT001\",0,2,2,\"");
        tscDll.sendcommand(b02);
        tscDll.sendcommand("\"\n");
        //打印第3行
        String span3 = "客户订单号 P.O.";
        byte[] b3 = new byte[1024];
        b3 = span3.getBytes("GB2312");
        tscDll.sendcommand("TEXT 25,175,\"FONT001\",0,2,2,\"");
        tscDll.sendcommand(b3);
        tscDll.sendcommand("\"\n");
        String span03 = "30083972";
        byte[] b03 = new byte[1024];
        b03 = span03.getBytes("GB2312");
        tscDll.sendcommand("TEXT 300,175,\"FONT001\",0,2,2,\"");
        tscDll.sendcommand(b03);
        tscDll.sendcommand("\"\n");
        //打印第4行
        String span4 = "客戶料号Customer P/N";
        byte[] b4 = new byte[1024];
        b4 = span4.getBytes("GB2312");
        tscDll.sendcommand("TEXT 25,240,\"FONT001\",0,2,2,\"");
        tscDll.sendcommand(b4);
        tscDll.sendcommand("\"\n");
        String span04 = "85919391321300019";
        byte[] b04 = new byte[1024];
        b04 = span04.getBytes("GB2312");
        tscDll.sendcommand("TEXT 300,240,\"FONT001\",0,2,2,\"");
        tscDll.sendcommand(b04);
        tscDll.sendcommand("\"\n");
        //打印第5行
        String span5 = "利盟料号Lexmark P/N";
        byte[] b5 = new byte[1024];
        b5 = span5.getBytes("GB2312");
        tscDll.sendcommand("TEXT 25,305,\"FONT001\",0,2,2,\"");
        tscDll.sendcommand(b5);
        tscDll.sendcommand("\"\n");
        String span05 = "24F0172";
        byte[] b05 = new byte[1024];
        b05 = span05.getBytes("GB2312");
        tscDll.sendcommand("TEXT 300,305,\"FONT001\",0,2,2,\"");
        tscDll.sendcommand(b05);
        tscDll.sendcommand("\"\n");
        //打印第6行
        String span6 = "料号 P/N";
        byte[] b6 = new byte[1024];
        b6 = span6.getBytes("GB2312");
        tscDll.sendcommand("TEXT 25,380,\"FONT001\",0,2,2,\"");
        tscDll.sendcommand(b6);
        tscDll.sendcommand("\"\n");
        String span06 = "1002-24F0172-1";
        byte[] b06 = new byte[1024];
        b06 = span06.getBytes("GB2312");
        tscDll.sendcommand("TEXT 230,380,\"FONT001\",0,2,2,\"");
        tscDll.sendcommand(b06);
        tscDll.sendcommand("\"\n");
        //打印第7行
        String span7 = "品名Description";
        byte[] b7 = new byte[1024];
        b7 = span7.getBytes("GB2312");
        tscDll.sendcommand("TEXT 25,445,\"FONT001\",0,2,2,\"");
        tscDll.sendcommand(b7);
        tscDll.sendcommand("\"\n");
        String span07 = "碳粉匣本体";
        byte[] b07 = new byte[1024];
        b07 = span07.getBytes("GB2312");
        tscDll.sendcommand("TEXT 230,445,\"FONT001\",0,2,2,\"");
        tscDll.sendcommand(b07);
        tscDll.sendcommand("\"\n");
        String span007 = "碳粉匣本体";
        byte[] b007 = new byte[1024];
        b007 = span007.getBytes("GB2312");
        tscDll.sendcommand("TEXT 230,445,\"FONT001\",0,2,2,\"");
        tscDll.sendcommand(b007);
        tscDll.sendcommand("\"\n");
        String span0007 = "EC/版本";
        byte[] b0007 = new byte[1024];
        b0007 = span0007.getBytes("GB2312");
        tscDll.sendcommand("TEXT 435,430,\"FONT001\",0,2,2,\"");
        tscDll.sendcommand(b0007);
        tscDll.sendcommand("\"\n");
        String span00007 = "REV.";
        byte[] b00007 = new byte[1024];
        b00007 = span00007.getBytes("GB2312");
        tscDll.sendcommand("TEXT 435,460,\"FONT001\",0,2,2,\"");
        tscDll.sendcommand(b00007);
        tscDll.sendcommand("\"\n");
        String span000007 = "019";
        byte[] b000007 = new byte[1024];
        b000007 = span000007.getBytes("GB2312");
        tscDll.sendcommand("TEXT 550,445,\"FONT001\",0,2,2,\"");
        tscDll.sendcommand(b000007);
        tscDll.sendcommand(commit);
        //打印第8行
        String span8 = "数量 QTY";
        byte[] b8 = new byte[1024];
        b8 = span8.getBytes("GB2312");
        tscDll.sendcommand("TEXT 25,510,\"FONT001\",0,2,2,\"");
        tscDll.sendcommand(b8);
        tscDll.sendcommand(commit);
        String span08 = "24  PCS";
        byte[] b08 = new byte[1024];
        b08 = span08.getBytes("GB2312");
        tscDll.sendcommand("TEXT 230,510,\"FONT001\",0,2,2,\"");
        tscDll.sendcommand(b08);
        tscDll.sendcommand(commit);
        String span008 = "穴号 Cav";
        byte[] b008 = new byte[1024];
        b008 = span008.getBytes("GB2312");
        tscDll.sendcommand("TEXT 435,510,\"FONT001\",0,2,2,\"");
        tscDll.sendcommand(b008);
        tscDll.sendcommand(commit);
        String span0008 = "3~4#";
        byte[] b0008 = new byte[1024];
        b0008 = span0008.getBytes("GB2312");
        tscDll.sendcommand("TEXT 550,510,\"FONT001\",0,2,2,\"");
        tscDll.sendcommand(b0008);
        tscDll.sendcommand(commit);
        //打印第9行
        String span9 = "机台号/模號";
        byte[] b9 = new byte[1024];
        b9 = span9.getBytes("GB2312");
        tscDll.sendcommand("TEXT 25,575,\"FONT001\",0,2,2,\"");
        tscDll.sendcommand(b9);
        tscDll.sendcommand(commit);
        String span09 = "HT450T-3 /LX-587";
        byte[] b09 = new byte[1024];
        b09 = span09.getBytes("GB2312");
        tscDll.sendcommand("TEXT 230,575,\"FONT001\",0,2,2,\"");
        tscDll.sendcommand(b09);
        tscDll.sendcommand(commit);
        String span009 = "箱号Box#";
        byte[] b009 = new byte[1024];
        b009 = span009.getBytes("GB2312");
        tscDll.sendcommand("TEXT 435,575,\"FONT001\",0,2,2,\"");
        tscDll.sendcommand(b009);
        tscDll.sendcommand(commit);
        String span0009 = "5";
        byte[] b0009 = new byte[1024];
        b0009 = span0009.getBytes("GB2312");
        tscDll.sendcommand("TEXT 550,575,\"FONT001\",0,2,2,\"");
        tscDll.sendcommand(b0009);
        tscDll.sendcommand(commit);
        //打印第10行
        String span10 = "材料Raw Material";
        byte[] b10 = new byte[1024];
        b10 = span10.getBytes("GB2312");
        tscDll.sendcommand("TEXT 25,640,\"FONT001\",0,2,2,\"");
        tscDll.sendcommand(b10);
        tscDll.sendcommand(commit);
        String span100 = "ABS-0011";
        byte[] b100 = new byte[1024];
        b100 = span100.getBytes("GB2312");
        tscDll.sendcommand("TEXT 230,640,\"FONT001\",0,2,2,\"");
        tscDll.sendcommand(b100);
        tscDll.sendcommand(commit);
        //打印第11行
        String span11 = "材料批号RM Lot#";
        byte[] b11 = new byte[1024];
        b11 = span11.getBytes("GB2312");
        tscDll.sendcommand("TEXT 25,705,\"FONT001\",0,2,2,\"");
        tscDll.sendcommand(b11);
        tscDll.sendcommand(commit);
        String span110 = "ABS-0011-4-12";
        byte[] b110 = new byte[1024];
        b110 = span110.getBytes("GB2312");
        tscDll.sendcommand("TEXT 230,705,\"FONT001\",0,2,2,\"");
        tscDll.sendcommand(b110);
        tscDll.sendcommand(commit);
        //打印第12行
        String span12 = "批次 Lot #";
        byte[] b12 = new byte[1024];
        b12 = span12.getBytes("GB2312");
        tscDll.sendcommand("TEXT 25,770,\"FONT001\",0,2,2,\"");
        tscDll.sendcommand(b12);
        tscDll.sendcommand(commit);
        String span120 = "YYMMDD-###";
        byte[] b120 = new byte[1024];
        b120 = span120.getBytes("GB2312");
        tscDll.sendcommand("TEXT 230,770,\"FONT001\",0,2,2,\"");
        tscDll.sendcommand(b120);
        tscDll.sendcommand(commit);
        //打印第13行
        String span13 = "班別-作业员";
        byte[] b13 = new byte[1024];
        b13 = span13.getBytes("GB2312");
        tscDll.sendcommand("TEXT 25,820,\"FONT001\",0,2,2,\"");
        tscDll.sendcommand(b13);
        tscDll.sendcommand(commit);
        String span130 = "A班-范冰冰";
        byte[] b130 = new byte[1024];
        b130 = span130.getBytes("GB2312");
        tscDll.sendcommand("TEXT 230,820,\"FONT001\",0,2,2,\"");
        tscDll.sendcommand(b130);
        tscDll.sendcommand(commit);
        //打印第14行
        String span14 = "生产日期P. Date";
        byte[] b14 = new byte[1024];
        b14 = span14.getBytes("GB2312");
        tscDll.sendcommand("TEXT 25,875,\"FONT001\",0,2,2,\"");
        tscDll.sendcommand(b14);
        tscDll.sendcommand(commit);
        String span140 = "2020/4/8 20:03:00";
        byte[] b140 = new byte[1024];
        b140 = span140.getBytes("GB2312");
        tscDll.sendcommand("TEXT 225,875,\"FONT001\",0,2,2,\"");
        tscDll.sendcommand(b140);
        tscDll.sendcommand(commit);
        //打印圆圈和圈内的文字
       // tscDll.sendcommand("CIRCLE 465,640,120,5\n");
       // tscDll.printerfont(510,665,"3",0,0,0,"QC");
       // tscDll.printerfont(495,695,"3",0,0,0,"PASS");
        //打印二维码
        tscDll.sendcommand(new QRCODE(460,755,5,"zhuhaixielong123456").getQRCODE());
        //打印最后一行字母
        String span15 = "Made in China";
        byte[] b15 = new byte[1024];
        b15 = span15.getBytes("GB2312");
        tscDll.sendcommand("TEXT 225,920,\"FONT001\",0,2,1,\"");
        tscDll.sendcommand(b15);
        tscDll.sendcommand(commit);
        tscDll.printlabel(1, 1);
        tscDll.closeport();
    }

    /**
     * 打印测试
     *
     * @param view
     */
    public void btnPrint(View view) throws UnsupportedEncodingException {
        mac = inputMac_v.getText().toString();
        x = Integer.parseInt(inputWidth_v.getText().toString());
        y = Integer.parseInt(inputHeight_v.getText().toString());
        Log.i("kfjsfjerifgerog", mac + "    " + x + "   " + y);
        printTest(mac, x, y);
    }

    private void printTest2() throws UnsupportedEncodingException {
        //连接蓝牙打印机  参数：蓝牙的mac地址
        tscDll.openport("00:0C:BF:2C:35:64");
        //打印机设置
        tscDll.setup(100, 80, 4, 5, 0, 2, 0);
        tscDll.clearbuffer(); //清除缓存
        //画表格
        tscDll.sendcommand((new BOX(5, 5, 780, 630, 5)).getBOX());
        tscDll.sendcommand(new BAR(5, 80, 750, 5).getBAR());
        tscDll.sendcommand(new BAR(5, 130, 750, 5).getBAR());
        tscDll.sendcommand(new BAR(5, 180, 750, 5).getBAR());
        tscDll.sendcommand(new BAR(5, 240, 750, 5).getBAR());
        tscDll.sendcommand(new BAR(5, 300, 750, 5).getBAR());
        tscDll.sendcommand(new BAR(5, 360, 750, 5).getBAR());
        tscDll.sendcommand(new BAR(5, 420, 750, 5).getBAR());
        //中间分割线
        tscDll.sendcommand(new BAR(375, 80, 4, 1630).getBAR());
        //二维码
        String cmdsdfds = "QRCODE 430,435,H,7,A,0,M2,S7,\"123456789\"\n";  //正确的
        tscDll.sendcommand(cmdsdfds);
        //打印表头
        String mag = "冠理科技有限公司";
        byte[] b = new byte[1024];
        b = mag.getBytes("GB2312");
       /* tscDll.sendcommand("TEXT 100,100,\"FONT001\",0,3,3,\"");
        tscDll.sendcommand(b);
        tscDll.sendcommand("\"\n");*/

        tscDll.printlabel(1, 1);
        tscDll.closeport();
    }


    @Override
    protected void onStop() {
        super.onStop();
        mac = inputMac_v.getText().toString();
        if (flg && !mac.equals("")) {
            saveMac(mac);
        }

    }
}
