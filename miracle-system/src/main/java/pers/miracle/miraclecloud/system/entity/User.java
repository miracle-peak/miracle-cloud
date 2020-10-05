package pers.miracle.miraclecloud.system.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.NullSerializer;

/**
 * 用户
 *
 * @author: 蔡奇峰
 * @date: 2020/8/10 下午3:44
 */
public class User {
    /**
     * 需要在实体类指定主键，否则 removeById(根据主键删除）失败
     */
    @TableId("user_id")
    private String userId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     * 序列化时，不显示密码(查询)，可以被反序列化(新增和修改)
     * @JsonIgnore 过滤掉特定字段不返回或者不解析,即忽略序列化及反序列化，
     * @JsonSerialize(using = NullSerializer.class) 会返回字段但值为null
     */
    @JsonSerialize(using = NullSerializer.class)
    private String password;

    /**
     * 邮箱
     */
    private String mail;

    /**
     * 是否锁定 默认正常 0 锁定 1
     */
    private String locked;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getLocked() {
        return locked;
    }

    public void setLocked(String locked) {
        this.locked = locked;
    }

    public User() {
    }

    public User(String userId, String userName, String mail, String locked) {
        this.userId = userId;
        this.userName = userName;
        this.mail = mail;
        this.locked = locked;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", mail='" + mail + '\'' +
                ", locked='" + locked + '\'' +
                '}';
    }
}
