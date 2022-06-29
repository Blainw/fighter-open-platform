package ai.wiseloong.open.app.mapper;

import ai.wiseloong.open.app.pojo.TOpenApp;
import ai.wiseloong.open.app.pojo.vo.TOpenAppVo;
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
 * @ClassName: TOpenApp
 * @Description: 开放平台-应用-Mapper层
 * @author 张小平
 */
@Mapper
public interface TOpenAppMapper extends BaseMapper<TOpenApp> {

    List<Map<String,Object>> list(@Param(Constants.WRAPPER) Wrapper wrapper);

    List<TOpenAppVo> queryList(@Param(Constants.WRAPPER) Wrapper wrapper);

    IPage<TOpenAppVo> query(@Param("page") Page<TOpenAppVo> page, @Param(Constants.WRAPPER) Wrapper wrapper);

    void batchInsert(@Param("list") List<TOpenApp> list);
}