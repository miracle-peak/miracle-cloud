package pers.miracle.miraclecloud.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

/**
 * 角色
 *
 * @author: 蔡奇峰
 * @date: 2020/8/10 下午3:51
 */
public class Role {
    @TableId(type = IdType.AUTO)
    private Integer roleId;

    /**
     * 角色名称
     */
    private String name;


    /**
     * 是否锁定 默认正常 0 锁定 1
     */
    private String locked;



    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocked() {
        return locked;
    }

    public void setLocked(String locked) {
        this.locked = locked;
    }


    @Override
    public String toString() {
        return "Role{" +
                "roleId=" + roleId +
                ", name='" + name + '\'' +
                ", locked='" + locked + '\'' +
                '}';
    }
}
