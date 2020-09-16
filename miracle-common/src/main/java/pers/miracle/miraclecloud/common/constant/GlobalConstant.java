package pers.miracle.miraclecloud.common.constant;

/**
 * 全局变量
 *
 * @author 蔡奇峰
 */
public interface GlobalConstant {

    /**
     * 盐值
     */
    String SALT = "miracle_salt";

    /**
     * jwt解密后claims的唯一标识
     */
    String JWT_ID = "miracle_id";

    /**
     * redis存储用户jwt 的 key前缀 方便批量删除和查询
     */
    String USER_KEY_PREFIX = "user-";

    /**
     * 登录方法名
     */
    String LOGIN_METHOD = "login";

    /**
     * jwt秘钥
     */
    String SECRET = "miracle_jwt";
    /**
     * 请求头key 对应jwt
     */
    String HEADER_JWT = "Authorization";
    /**
     * 操作成功
     */
    int SUCCESS = 666;
    /**
     * 操作失败
     */
    int ERROR = 2233;
    /**
     * token 错误
     */
    int TOKEN_ERROR = 20001;
    /**
     * 没有jwt
     */
    int TOKEN_NONE = 3333;
    /**
     * jwt过期
     */
    int JWT_EXPIRE = 13333;
    /**
     * jwt异常
     */
    int JWT_EXCEPTION = 5005;
    /**
     * 频繁访问
     */
    int VISIT_LIMIT = 11111;
    /**
     * 服务器连接失败，如redis连接失败
     */
    int CONNECTION_ERROR = 40001;

    int REDIS_ERROR = 40003;
    /**
     * 异常
     */
    int EXCEPTION_400 = 40000;
    /**
     * 找不到方法
     */
    int EXCEPTION_NOT_METHOD = 4005;
    /**
     * 运行异常
     */
    int EXCEPTION_500 = 5000;
    /**
     * HTTP请求错误
     */
    int HTTP_ERROR_100 = 1000;
    int HTTP_ERROR_300 = 3000;
    int HTTP_ERROR_400 = 4000;
    int NOT_FOUND = 4004;
    /**
     * 不在100-500的其他错误
     */
    int SYSTEM_ERROR = 7777;


}
