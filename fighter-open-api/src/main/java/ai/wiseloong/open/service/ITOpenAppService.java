package ai.wiseloong.open.service;

import ai.wiseloong.fighter.core.pojo.ResultInfo;
import ai.wiseloong.fighter.db.pojo.QueryCondition;
import ai.wiseloong.open.pojo.TOpenApp;
import ai.wiseloong.open.pojo.dto.TOpenAppDto;
import ai.wiseloong.open.pojo.vo.TOpenAppVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
/**
 *
 * @ClassName: TOpenAppService
 * @Description: 开放平台-应用-Service层
 * @author 张小平
 */
@Validated
public interface  ITOpenAppService {
    /**
     * 分页查询
     */
    IPage<TOpenAppVo> query(QueryCondition<TOpenAppDto> queryCondition,int pageNum,int pageSize);
    /**
     * 批量删除
     */
    ResultInfo<?> delete(@NotEmpty(message = "idList对象不能为空")List<String> idList);
    /**
     * 新增或编辑
     */
    void saveOrUpdate(@NotNull(message = "对象不能为空") TOpenApp tOpenApp);
    /**
     * 查询单条
     */
    TOpenApp get( @NotBlank(message = "开放平台-应用唯一键不能为空") String id);
    /**
     *Get查询list
     */
    List<TOpenAppVo> selectList(TOpenAppDto tOpenAppDto);
    /**
    *Post查询list
    */
    List<TOpenAppVo> queryList(QueryCondition<TOpenAppDto> queryCondition);
    /**
    *导出Excel
    */
    void dataExport(HttpServletResponse response, QueryCondition<TOpenAppDto> queryCondition);
    /**
     *导入数据
     */
    ResultInfo<?> excelImport(MultipartFile file,HttpServletRequest request,HttpServletResponse response);
    /**
     *导出Excel模板
     */
    void templateExport(HttpServletResponse response);
}