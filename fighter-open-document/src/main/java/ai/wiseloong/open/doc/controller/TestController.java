package ai.wiseloong.open.doc.controller;

import ai.wiseloong.extend.utils.RestResultCode;
import ai.wiseloong.fighter.core.pojo.ResultInfo;
import ai.wiseloong.fighter.core.util.ResultInfoBuilder;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 *
 * @ClassName: TestController
 * @Description: 开放平台-测试-controller层
 * @author 张小平
 */
@Api(tags = "开放平台-测试", value = "TestController")
@RestController
@RequestMapping(value = "/test")
public class TestController {

    @ApiOperation("分页查询")
    @RequestMapping(method = RequestMethod.POST, value = "/query")
    public ResultInfo<IPage<?>> page(HttpServletRequest request, HttpServletResponse response,int pageNum,int pageSize) {

        return ResultInfoBuilder.success(RestResultCode.CODE_200.getMessage(), new Page<>());
    }
}