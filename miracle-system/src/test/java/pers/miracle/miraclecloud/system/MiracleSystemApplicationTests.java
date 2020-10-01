package pers.miracle.miraclecloud.system;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import pers.miracle.miraclecloud.common.utils.Md5Util;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantReadWriteLock;

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

    private int count = 0;
    private AtomicInteger num = new AtomicInteger(0);
    // 必须作为成员变量，否则获取的不是这个实列对象的锁，而是某个实列对象的方法，则无法达到同步效果
    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    ReentrantReadWriteLock.WriteLock writeLock = lock.writeLock();
    ReentrantReadWriteLock.ReadLock readLock = lock.readLock();

    @Test
    public void readWriteLock(){

        for (int i = 0; i < 10618; i++) {
            new Thread(() ->{

                add();
//                num.getAndIncrement();
            }).start();
        }

        System.out.println("count=" + count);
        System.out.println("num=" + num);

    }

    public synchronized void add(){
//        writeLock.lock();
//        readLock.lock();
        try {
            // 模拟延迟
            try {
                TimeUnit.MICROSECONDS.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            ++count;
        } finally {
//            readLock.unlock();
//            writeLock.unlock();
        }

    }

}
