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
    @Test
    public void cache(){
        /*Scanner input = new Scanner(System.in);
        String str = input.nextLine();*/
        String str = "234rtyui";

        char[] chars = str.toCharArray();
        for(int i = 0; i < chars.length - 1; ++i){
            boolean flag = false;
            for(int j = 0; j < chars.length - 1 - i; ++j){

                if(chars[j + 1] < chars[j]){
                    char temp = chars[j];
                    chars[j] = chars[j + 1];
                    chars[j + 1] = temp;
                    flag = true;
                }
            }
            if(!flag){
                System.out.print(chars);
                return;
            }

        }
        System.out.print(chars);


    }
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        List<String[]> loginInfo = new ArrayList<>();
        String line = input.nextLine();
        String userId = null;
        while(input.hasNext() && !line.equals("") && null != line){
            String[] strs = line.split(",");
            loginInfo.add(strs);
            line = input.nextLine();
            if (line.indexOf(",") < 0){
                userId = line;
                input.close();
                break;
            }
        }

        int user1 = cluster(loginInfo, userId);
        System.out.println("res=" + user1);
    }

    public static int cluster(List<String[]> loginInfo, String userId){
        int res = 0;
        List<String> devices = new ArrayList<>();
        for (int i = 0; i < loginInfo.size(); i++) {
            if(userId.equals(loginInfo.get(i)[0])){
                devices.add(loginInfo.get(i)[1]);
            }
        }
        for (int i = 0; i < loginInfo.size(); i++) {
            for (int j = 0; j < devices.size(); j++) {
                String device = devices.get(j);
                if(null != device && device.equals(loginInfo.get(i)[1])){
                    res += 1;
                }
            }

        }
        System.out.println(res);

        return res;
    }

    @Test
    public void testString(){
        String str = "72229,in,20931|72229,in,20931|72229,in,20931|21257,out,68387|21257,out,68387|21257,in,68387|66362,in,31725|66362,in,31725|66362,in,3172";

        int res = maxNumOfCustomer(str);
        System.out.println("res==" + res);

        /*String[]strings1 = new String[]{"user1", "device1"};
        String[]strings2 = new String[]{"user2", "device2"};
        String[]strings3 = new String[]{"user3", "device1"};
        List<String[]> loginInfo = new ArrayList<>();
        loginInfo.add(strings1);
        loginInfo.add(strings2);
        loginInfo.add(strings3);*/

        /*Scanner input = new Scanner(System.in);
        List<String[]> loginInfo = new ArrayList<>();
        while(input.hasNext()){
            String line = input.nextLine();
            String[] strs = line.split(",");
            loginInfo.add(strs);

        }
        int user1 = cluster(loginInfo, "user1");
        System.out.println("res=" + user1);*/

    }

    public int maxNumOfCustomer(String customerLogs){
        String[] strings = customerLogs.split("\\|");
        List<Integer> in = new ArrayList<>();
        List<Integer> out = new ArrayList<>();
        for (int i = 0; i < strings.length; i++) {
            String item = strings[i];
            int e1 = Integer.parseInt(item.substring(item.lastIndexOf(",") + 1));
            if(item.indexOf("in") > 0){
                in.add(e1);
            }else if(item.indexOf("out") > 0){
                out.add(e1);
            }
            System.out.println("m=" + strings[i]);
        }
        // 一天
        // int day = 3600 * 24;
        // sort
        Collections.sort(in);
        Collections.sort(out);
        int inLen = in.size();
        int outLen = out.size();
        System.out.println("in=" + in.toString());
        System.out.println("out=" + out.toString());
        int result = 0;
        List<Integer> inList = new ArrayList<>();
        int i = 0;
        while (i < outLen){
            for (int j = 0; j < inLen; j++) {
                if(i < outLen && j < inLen) {
                    if (out.get(i) > in.get(j)) {
                        result += 1;
                        inList.add(result);
                    } else if (out.get(i) <= in.get(j)) {
                       /* result += 1;
                        inList.add(result);
                        result -= 1;
                        ++i;
                    } else if (out.get(i) < in.get(j)) {*/
                        result -= 1;
                        i++;
                    }
                }
            }
            break;

        }
        if (inList.size() == 0){
            return 0;
        }

        Collections.sort(inList);
        System.out.println("list=" + inList.toString());

        return inList.get(inList.size() - 1);
    }


    public void merge(int[] nums1, int m, int[] nums2, int n) {

        int j = 0;
        for(int i = m; i < n + m; ++i){
            nums1[i] = nums2[j];
            ++j;
        }
        Arrays.sort(nums1);
        System.out.println(Arrays.toString(nums1));
    }


    public int[] calculateStatistics (int[] dataArr) {
        // write code here
        int[] result = new int[4];
        Arrays.sort(dataArr);
        int len = dataArr.length;
        int m = len % 2 == 0 ? dataArr.length / 2 : (len / 2) + 1;
        int top = len % 10 == 0 ? (int) (len * 0.9) : (int)(len  * 0.9);
        result[1] = dataArr[0];
        result[2] = dataArr[len - 1];
        result[0] = dataArr[m];
        result[3] = dataArr[top];

        return result;

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
