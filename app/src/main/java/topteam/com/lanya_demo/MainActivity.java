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
        tscDll.sendcommand((new BOX(20, 10, 645, 950, 4)).getBOX());
        //第1行
        tscDll.sendcommand(new BAR(20, 100, 625, 3).getBAR());
        //第2行
        tscDll.sendcommand(new BAR(20, 165, 625, 3).getBAR());
        //第3行
        tscDll.sendcommand(new BAR(20, 230, 625, 3).getBAR());
        //第4行
        tscDll.sendcommand(new BAR(20, 295, 625, 3).getBAR());
        //第5行
        tscDll.sendcommand(new BAR(20, 360, 625, 3).getBAR());
        //中间分割线 竖线
        tscDll.sendcommand(new BAR(280, 100, 3, 260).getBAR());
        //中间分割线  横线
        tscDll.sendcommand(new BAR(20, 370, 625, 3).getBAR());
        //第6行
        tscDll.sendcommand(new BAR(20, 435, 625, 3).getBAR());
        //第7行
        tscDll.sendcommand(new BAR(20, 500, 625, 3).getBAR());
        //分割线 竖线
        tscDll.sendcommand(new BAR(540, 435, 3, 195).getBAR());
        //第8行
        tscDll.sendcommand(new BAR(20, 565, 625, 3).getBAR());
        //第9行
        tscDll.sendcommand(new BAR(20, 630, 625, 3).getBAR());
        //第10行
        tscDll.sendcommand(new BAR(20, 695, 410, 3).getBAR());
        //第11行
        tscDll.sendcommand(new BAR(20, 760, 410, 3).getBAR());
        //第12行
        tscDll.sendcommand(new BAR(20, 825, 410, 3).getBAR());
        //第13行
        tscDll.sendcommand(new BAR(20, 890, 410, 3).getBAR());
        //第14行
        //  tscDll.sendcommand(new BAR(20, 955, 640, 5).getBAR());
        //中间分割线 竖线
        tscDll.sendcommand(new BAR(220, 370, 3, 575).getBAR());
        //中间分割线 竖线
        tscDll.sendcommand(new BAR(430, 435, 3, 510).getBAR());
        //圆形和二维码的分割线
        tscDll.sendcommand(new BAR(430, 795, 215, 3).getBAR());


        //打印第一行
        String span1 = "珠海协隆塑胶电子有限公司";
        byte[] b1 = new byte[1024];
        b1 = span1.getBytes("GB2312");
        tscDll.sendcommand("TEXT 120,20,\"FONT001\",0,3,3,\"");
        tscDll.sendcommand(b1);
        tscDll.sendcommand("\"\n");
        tscDll.printerfont(25,70,"2",0,1,1,"Zhuhai Xielong Plastics & Electronics Co,Ltd.");


        //二维码
        String cmdsdfds = "QRCODE 440,440,H,7,A,0,M2,S7,\"123456789\"\n";  //正确的
        //  tscDll.sendcommand(cmdsdfds);

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
