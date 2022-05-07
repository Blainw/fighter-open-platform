package ai.wiseloong.open.mapper;

import ai.wiseloong.open.pojo.TOpenAppExtend;
import  ai.wiseloong.open.pojo.vo.TOpenAppExtendVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
/**
 *
 * @ClassName: TOpenAppExtend
 * @Description: 开发平台-应用扩展-Mapper层
 * @author 张小平
 */
@Mapper
public interface TOpenAppExtendMapper extends BaseMapper<TOpenAppExtend> {

    List<Map<String,Object>> list(@Param(Constants.WRAPPER) Wrapper wrapper);

    List<TOpenAppExtendVo> queryList(@Param(Constants.WRAPPER) Wrapper wrapper);

    IPage<TOpenAppExtendVo> query(@Param("page") Page<TOpenAppExtendVo> page, @Param(Constants.WRAPPER) Wrapper wrapper);

    void batchInsert(@Param("list") List<TOpenAppExtend> list);
}