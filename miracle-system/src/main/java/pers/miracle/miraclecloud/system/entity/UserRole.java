package pers.miracle.miraclecloud.system.entity;

/**
 * @author: 蔡奇峰
 * @date: 2020/8/10 下午4:25
 */
public class UserRole {

    private Integer userId;

    private Integer roleId;


    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }


    @Override
    public String toString() {
        return "UserRole{" +
                "userId=" + userId +
                ", roleId=" + roleId +
                '}';
    }
}
