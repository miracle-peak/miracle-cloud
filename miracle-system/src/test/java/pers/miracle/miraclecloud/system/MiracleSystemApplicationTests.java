package pers.miracle.miraclecloud.system;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import pers.miracle.miraclecloud.common.utils.Md5Util;

import java.util.*;

//@SpringBootTest
class MiracleSystemApplicationTests {

    @Test
    void contextLoads() {
        Map map = new HashMap(16);
        for (int i = 0; i < 15; i++) {
            map.put(i, new String(new char[]{(char) ('A'+ i)}));
        }
        System.out.println("======keySet=======");
        Set set = map.keySet();
        Iterator iterator = set.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
        System.out.println("======values=======");
        Collection values = map.values();
        Iterator stringIterator=values.iterator();
        while (stringIterator.hasNext()) {
            System.out.println(stringIterator.next());
        }
        System.out.println("======entrySet=======");
//        for (Map.Entry entry : map.entrySet()) {
//            System.out.println(entry);
//        }
    }

    @Test
    public void test(){
        List<String> list = new ArrayList<>();
        // 加同步锁
        Collections.synchronizedList(list);

        StringBuilder builder = new StringBuilder();

        builder.toString();

    }




}
