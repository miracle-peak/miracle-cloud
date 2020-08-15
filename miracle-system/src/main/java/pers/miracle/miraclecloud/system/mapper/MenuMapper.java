package pers.miracle.miraclecloud.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import pers.miracle.miraclecloud.system.entity.Menu;

import java.util.List;

/**
 * @author: 蔡奇峰
 * @date: 2020/8/14 下午4:41
 */
@Repository
public interface MenuMapper extends BaseMapper<Menu> {
    /**
     * 查询菜单列表
     *
     * @param menu
     * @return
     */
    List<Menu> ListByMenu(Menu menu);
}
