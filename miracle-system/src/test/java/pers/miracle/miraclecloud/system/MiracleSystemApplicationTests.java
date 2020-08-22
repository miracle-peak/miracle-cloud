package pers.miracle.miraclecloud.system;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;

//@SpringBootTest
class MiracleSystemApplicationTests {

    @Test
    void contextLoads() {
        int i = lengthOfLongestSubstring("dvdf");
        System.out.println("i->" + i);
    }

    public int lengthOfLongestSubstring(String s) {
        HashSet<String> set = new HashSet<>();
        int n = 0, max = 0;
        for(int i = 0; i < s.length(); i++){
            boolean flag = set.add(s.charAt(i) + "");
            System.out.println("flag:" + flag);
            n++;
            if(! flag && i != s.length() - 1){
                set = new HashSet<>();
                set.add(s.charAt(i) + "");
                n = 1;
            }
            max = max > n ? max : n;
        }
        System.out.println("n:" + n);

        return max;
    }

}
