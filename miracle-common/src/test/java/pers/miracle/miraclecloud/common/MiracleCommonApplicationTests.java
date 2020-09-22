package pers.miracle.miraclecloud.common;

import com.sun.management.OperatingSystemMXBean;
import org.junit.jupiter.api.Test;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.lang.management.ManagementFactory;
import java.text.DecimalFormat;
import java.util.Arrays;

//@SpringBootTest
class MiracleCommonApplicationTests {

    @Test
    void contextLoads() {
        int[] nums = new int[]{3, 4, -1, 1};
        int i = firstMissingPositive(nums);

        System.out.println("i=" + i);
    }

    public int firstMissingPositive(int[] nums) {
        int len = nums.length;
        for(int i = 0; i < len; ++i){
            if(nums[i] <= 0){
                nums[i] = len + 1;
            }
        }
        System.out.println("nums:" + Arrays.toString(nums));
        for(int i = 0; i < len; ++i){
            int n = Math.abs(nums[i]);
            if(n <= len){
                nums[n - 1] = -Math.abs(nums[n - 1]);
            }
        }

        for(int i = 0; i < len; ++i){
            if(nums[i] > 0){
                return i + 1;
            }
        }
        return len + 1;
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
