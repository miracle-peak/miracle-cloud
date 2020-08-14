package pers.miracle.miraclecloud.system.vo;

import pers.miracle.miraclecloud.system.entity.Role;
import pers.miracle.miraclecloud.system.entity.User;

import java.util.List;

/**
 * @author: 蔡奇峰
 * @date: 2020/8/14 下午5:59
 */
public class UserRoleVO extends User {

    private List<Role> roles;

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "UserRoleVO{" +
                "roles=" + roles +
                '}';
    }
}
