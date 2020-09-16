package pers.miracle.miraclecloud.common;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pers.miracle.miraclecloud.common.utils.RedisUtil;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.text.DecimalFormat;

//@SpringBootTest
class MiracleCommonApplicationTests {

    @Test
    void contextLoads() {
        // 当前文件系统类
        FileSystemView fsv = FileSystemView.getFileSystemView();
        // 列出所有windows 磁盘
        File[] fs = File.listRoots();
        // 显示磁盘卷标
        for (int i = 0; i < fs.length; i++) {
            System.out.println(fsv.getSystemDisplayName(fs[i]));
            System.out.print("总大小" + FormetFileSize(fs[i].getTotalSpace()));
            System.out.println("剩余" + FormetFileSize(fs[i].getFreeSpace()));
        }

    }

    public  String FormetFileSize(long fileS) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        if (fileS < 1000) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1000000) {
            fileSizeString = df.format((double) fileS / 1000) + "K";
        } else if (fileS < 1000000000) {
            fileSizeString = df.format((double) fileS / 1000000) + "M";
        } else {
            fileSizeString = df.format((double) fileS / 1000000000) + "G";
        }
        return fileSizeString;
    }

}
