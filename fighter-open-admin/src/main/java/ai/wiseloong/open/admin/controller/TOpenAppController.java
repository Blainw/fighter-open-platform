package ai.wiseloong.open.admin.controller;

import ai.wiseloong.extend.utils.RestResultCode;
import ai.wiseloong.open.pojo.TOpenApp;
import ai.wiseloong.open.pojo.vo.TOpenAppVo;
import ai.wiseloong.open.pojo.dto.TOpenAppDto;

import ai.wiseloong.fighter.core.pojo.ResultInfo;
import ai.wiseloong.fighter.core.util.ResultInfoBuilder;
import ai.wiseloong.fighter.db.pojo.QueryCondition;
import ai.wiseloong.open.service.ITOpenAppService;
import com.baomidou.mybatisplus.core.metadata.IPage;


import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.swagger.annotations.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;


/**
 *
 * @ClassName: TOpenAppController
 * @Description: 开放平台-应用-controller层
 * @author 张小平
 */
@Api(tags = "开放平台-应用", value = "TOpenAppController")
@RestController
@RequestMapping(value = "/tOpenApp")
public class TOpenAppController {

    @Resource
    private ITOpenAppService tOpenAppService;

    @ApiOperation("分页查询")
    @RequestMapping(method = RequestMethod.POST, value = "/query")
    public ResultInfo<IPage<TOpenAppVo>> page(HttpServletRequest request, HttpServletResponse response,@RequestBody QueryCondition<TOpenAppDto> queryCondition,int pageNum,int pageSize) {
        IPage<TOpenAppVo> pageInfo = tOpenAppService.query(queryCondition,pageNum,pageSize);
        return ResultInfoBuilder.success(RestResultCode.CODE_200.getMessage(), pageInfo);
    }


    @ApiOperation("新增或编辑")
    @RequestMapping(method = RequestMethod.POST, value = "/saveOrUpdate")
    public ResultInfo<?> saveOrUpdate(@RequestBody TOpenApp tOpenApp, HttpServletRequest request,HttpServletResponse response) {
         tOpenAppService.saveOrUpdate(tOpenApp);
        return ResultInfoBuilder.success(RestResultCode.CODE_200.getMessage());
    }

    @ApiOperation(value = "删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "idList", value = "开放平台-应用唯一健集合",dataType = "list", required = true, allowMultiple = true)
    })
    @DeleteMapping(value = "/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResultInfo<?> delete(HttpServletRequest request, HttpServletResponse response,@RequestParam(required = false) List<String> idList) {
       return tOpenAppService.delete(idList);
    }

    @ApiOperation(value = "查看")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "开放平台-应用唯一健", dataType = "string", required = true)
    })
    @RequestMapping(method = RequestMethod.GET, value = "/get")
    public ResultInfo<TOpenApp> detail(HttpServletRequest request, HttpServletResponse response,String id) {
        TOpenApp tOpenApp = tOpenAppService.get(id);
        return ResultInfoBuilder.success(RestResultCode.CODE_200.getMessage(),tOpenApp);
    }

    @ApiOperation(value = "GET获取集合")
    @RequestMapping(method = RequestMethod.GET, value = "/list")
    public ResultInfo<List< TOpenAppVo>> queryList(TOpenAppDto tOpenAppDto,HttpServletRequest request, HttpServletResponse response) {
        List<TOpenAppVo> list = tOpenAppService.selectList(tOpenAppDto);
        return ResultInfoBuilder.success(RestResultCode.CODE_200.getMessage(),list);
    }

    @ApiOperation(value = "POST获取集合")
    @RequestMapping(method = RequestMethod.POST, value = "/queryList")
    public ResultInfo<List<TOpenAppVo>> queryList(@RequestBody(required = false)QueryCondition<TOpenAppDto> queryCondition,HttpServletRequest request, HttpServletResponse response) {
        List<TOpenAppVo> list = tOpenAppService.queryList(queryCondition);
        return ResultInfoBuilder.success(RestResultCode.CODE_200.getMessage(),list);
    }

    @ApiOperation("数据导出Excel")
    @RequestMapping(method = RequestMethod.POST, value = "/dataExport")
    public ResultInfo<?> dataExport(@RequestBody(required = false)QueryCondition<TOpenAppDto> queryCondition,HttpServletRequest request,HttpServletResponse response) {
        tOpenAppService.dataExport(response, queryCondition);
        return null;
    }

    @ApiOperation("模板导出")
    @RequestMapping(method = RequestMethod.GET, value = "/templateExport")
    public ResultInfo<?> templateExport(HttpServletRequest request,HttpServletResponse response){
        tOpenAppService.templateExport(response);
        return null;
    }

    @ApiOperation("数据导入")
    @RequestMapping(method = RequestMethod.POST, value = "/excelImport")
    public ResultInfo<?> excelImport(@RequestParam(value = "file",required = false) MultipartFile file,HttpServletRequest request,HttpServletResponse response){

        return tOpenAppService.excelImport(file,request,response);
    }
}