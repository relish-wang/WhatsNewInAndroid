package wang.relish.whatsnew.q;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import org.apache.http.util.EncodingUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import wang.relish.whatsnew.R;

public class StorageActivity extends AppCompatActivity {

    private final int PERMISSIONS_CODE = 1; // 权限请求代码
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage);
    }
    
    /**
     * 往沙盒写文件
     */
    // /storage/emulated/0/Android/data/com.cold.androidq/files/Documents/test.txt
    // /storage/emulated/0/Android/data/com.gzlok.gamemarket.yueai.show/files/Documents/test.txt
    @TargetApi(19)
    public void onSave(View v) {
        String filePath = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS).getAbsolutePath();
        write("hello", filePath, "test.txt");
    }

    /**
     * 从沙盒读取文件
     */
    @TargetApi(19)
    public void onRead(View v) {
        String filePath = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS).getAbsolutePath();
        Toast.makeText(this, read(filePath, "test.txt"), Toast.LENGTH_LONG).show();
    }

    /**
     * 请求权限
     */
    public void onRequestRead(View v) {
        // READ_MEDIA_IMAGES,READ_MEDIA_VIDEO,READ_MEDIA_AUDIO
        getPermissions(new String[]{"Manifest.permission.READ_MEDIA_IMAGES"});
    }

    /**
     * 请求权限
     */
    public void onRequestWrite(View v) {
        getPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE});
    }

    /**
     * 往MediaStore目录写文件
     */
    @TargetApi(19)
    public void onWriteExternal(View v) {
        String filePath = getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath();
        write("hello111", filePath, "test.txt");
    }

    /**
     * 从MediaStore目录读文件
     */
    @TargetApi(19)
    public void onReadExternal(View v) {
        String filePath = getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath();
        Toast.makeText(this, read(filePath, "test.txt"), Toast.LENGTH_LONG).show();
    }

    /**
     * 文件保存
     */
    private void write(String str, String filePath, String name) {
//        filePath = "/storage/emulated/0/Android/data/com.cold.androidq1/files/Documents"; // 如果直接访问其他包下的文件，会报错
        String fileName = filePath + File.separator +name;
        File file = new File(fileName); // 定义File类对象
        if(!file.getParentFile().exists()) { // 父文件夹不存在
            file.getParentFile().mkdirs(); // 创建文件夹
        }
        try {
            FileOutputStream out = new FileOutputStream(fileName);
            out.write(str.getBytes());
            out.close();
            Toast.makeText(this, "保存成功",Toast.LENGTH_SHORT).show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 文件读取
     */
    private String read(String filePath, String fileName) {
        try {
            FileInputStream in = new FileInputStream(filePath + File.separator +fileName);
            int len= in.available();
            byte[]temp = new byte[len];
            in.read(temp);
            String str = EncodingUtils.getString(temp,"UTF-8");
            in.close();
            return str;
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 检查权限
     * @param
     * @return void
     */
    private void getPermissions(String[] permissions) {
        boolean flag = checkPermissions(permissions);
        if(!flag) {
            requestPermission(permissions);
        }
    }

    /**
     * 1.检查权限
     * @param
     * @return 权限是否允许标志
     */
    private boolean checkPermissions(String[] permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /**
     * 2.如果没有权限，动态请求权限
     * @param
     * @return void
     */
    private void requestPermission(String[] permissions) {
        ActivityCompat.requestPermissions(this, permissions, PERMISSIONS_CODE);
    }

    /**
     * 3.处理返回结果
     * @param grantResults 返回对应权限请求数组，如果成功，则为PERMISSION_GRANTED，否则是PERMISSION_DENIED
     * @return void
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSIONS_CODE) {
            boolean grantedFlag = true;
            // 判断是否所有的权限都已经授予了
            for (int grant : grantResults) {
                if (grant != PackageManager.PERMISSION_GRANTED) {
                    grantedFlag = false;
                    break;
                }
            }

            if (!grantedFlag) {
                Toast.makeText(this, "授权失败", Toast.LENGTH_SHORT).show();
            }
        }
    }

}