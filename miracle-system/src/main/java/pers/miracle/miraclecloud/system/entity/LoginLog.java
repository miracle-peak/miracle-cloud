package pers.miracle.miraclecloud.system.entity;


import java.io.Serializable;

/**
 * @author 蔡奇峰
 */
public class LoginLog implements Serializable {
    private Long id;
    /**
     * 登录用户名
     */
    private String userName;

    /**
     * 用户的ip
     */
    private String ip;

    /**
     * 登录时间
     */
    private String loginTime;

    /**
     * 用户所在城市
     */
    private String city;

    /**
     * 登录提示信息
     */
    private String msg;

    /**
     * 登录状态 0 成功 1 失败
     */
    private String status;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(String loginTime) {
        this.loginTime = loginTime;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "LoginLog{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", ip='" + ip + '\'' +
                ", loginTime='" + loginTime + '\'' +
                ", city='" + city + '\'' +
                ", msg='" + msg + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}