package ai.wiseloong.open.app.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

import ai.wiseloong.open.app.pojo.TOpenAppExtend;
import ai.wiseloong.open.app.pojo.dto.TOpenAppExtendDto;
import ai.wiseloong.open.app.pojo.vo.TOpenAppExtendVo;
import ai.wiseloong.fighter.db.pojo.QueryCondition;
import ai.wiseloong.fighter.core.pojo.ResultInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @ClassName: TOpenAppExtendService
 * @Description: 开发平台-应用扩展-Service层
 * @author 张小平
 */
@Validated
public interface  ITOpenAppExtendService {
    /**
     * 分页查询
     */
    IPage<TOpenAppExtendVo> query(QueryCondition<TOpenAppExtendDto> queryCondition, int pageNum, int pageSize);
    /**
     * 批量删除
     */
    ResultInfo<?> delete(@NotEmpty(message = "idList对象不能为空") List<String> idList);
    /**
     * 新增或编辑
     */
    void saveOrUpdate(@NotNull(message = "对象不能为空") TOpenAppExtend tOpenAppExtend);
    /**
     * 查询单条
     */
    TOpenAppExtend get(@NotBlank(message = "开发平台-应用扩展唯一键不能为空") String id);
    /**
     *Get查询list
     */
    List<TOpenAppExtendVo> selectList(TOpenAppExtendDto tOpenAppExtendDto);
    /**
    *Post查询list
    */
    List<TOpenAppExtendVo> queryList(QueryCondition<TOpenAppExtendDto> queryCondition);
    /**
    *导出Excel
    */
    void dataExport(HttpServletResponse response, QueryCondition<TOpenAppExtendDto> queryCondition);
    /**
     *导入数据
     */
    ResultInfo<?> excelImport(MultipartFile file, HttpServletRequest request, HttpServletResponse response);
    /**
     *导出Excel模板
     */
    void templateExport(HttpServletResponse response);
}