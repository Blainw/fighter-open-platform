package ai.wiseloong.open.email.controller;

import ai.wiseloong.extend.utils.RestResultCode;
import ai.wiseloong.fighter.core.pojo.ResultInfo;
import ai.wiseloong.fighter.core.util.ResultInfoBuilder;
import ai.wiseloong.open.app.pojo.TOpenApp;
import ai.wiseloong.open.utils.EmailUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Api(tags = "邮箱", value = "EmailController")
@RestController
@RequestMapping(value = "/email")
public class EmailController {

    @Autowired
    private EmailUtils emailUtils;

    @ApiOperation(value = "发送邮箱")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "email", value = "邮箱地址", dataType = "string", required = true)
    })
    @RequestMapping(method = RequestMethod.GET, value = "/send")
    public ResultInfo<TOpenApp> detail(HttpServletRequest request, HttpServletResponse response, String email) {
        emailUtils.sendMsg(email);
        return ResultInfoBuilder.success(RestResultCode.CODE_200.getMessage());
    }
}
