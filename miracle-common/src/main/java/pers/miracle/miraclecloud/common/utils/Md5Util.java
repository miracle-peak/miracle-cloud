package pers.miracle.miraclecloud.common.utils;

import org.springframework.util.DigestUtils;
import pers.miracle.miraclecloud.common.constant.GlobalConstant;

/**
 * @author: 蔡奇峰
 * @Version V1.0
 **/
public class Md5Util {


    /**
     * md5加密
     *
     * @param str
     * @return
     */
    public static String md5(String str) {
        String asHex = DigestUtils.md5DigestAsHex(str.getBytes());

        return asHex;
    }

    /**
     * md5加盐加密
     *
     * @param password
     * @return
     */
    public static String saltEncryption(String password) {
        password = GlobalConstant.SALT.charAt(2) + password + GlobalConstant.SALT.charAt(3) +
                GlobalConstant.SALT.charAt(1);

        String str = md5(password);

        return md5(str);
    }
}
