package ai.wiseloong.open.pojo;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.groups.Default;
import javax.validation.constraints.*;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

/**
 *
 * @ClassName: TOpenApp
 * @Description: 开放平台-应用-实体对象
 * @author 张小平
 */
@ApiModel(description = "开放平台-应用")
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_open_app")
@SuperBuilder(toBuilder = true)
public class TOpenApp implements Serializable{

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "主键Id")
    @TableId(type = IdType.ASSIGN_UUID)
    @Length(max = 32, message = "主键Id长度不能超过{max}")
    private String  id;


    @ApiModelProperty(value = "应用名称")
    @Length(max = 100, message = "应用名称长度不能超过{max}")
    @NotBlank(message ="应用名称为空，或者类型错误")
    private String  mc;


    @ApiModelProperty(value = "pc端url")
    @Length(max = 400, message = "pc端url长度不能超过{max}")
    private String  pcUrl;


    @ApiModelProperty(value = "手机端url")
    @Length(max = 400, message = "手机端url长度不能超过{max}")
    private String  sjUrl;


    @ApiModelProperty(value = "图标")
    @Length(max = 100, message = "图标长度不能超过{max}")
    @NotBlank(message ="图标为空，或者类型错误")
    private String  icons;


    @ApiModelProperty(value = "应用分类")
    @Length(max = 100, message = "应用分类长度不能超过{max}")
    @NotBlank(message ="应用分类为空，或者类型错误")
    private String  yyfl;


    @ApiModelProperty(value = "所属分类")
    @Length(max = 100, message = "所属分类长度不能超过{max}")
    @NotBlank(message ="所属分类为空，或者类型错误")
    private String  ssfl;


    @ApiModelProperty(value = "应用类型")
    @Length(max = 100, message = "应用类型长度不能超过{max}")
    @NotBlank(message ="应用类型为空，或者类型错误")
    private String  yylx;


    @ApiModelProperty(value = "办理方式")
    @Length(max = 100, message = "办理方式长度不能超过{max}")
    @NotBlank(message ="办理方式为空，或者类型错误")
    private String  blfs;


    @ApiModelProperty(value = "所属部门")
    @Length(max = 100, message = "所属部门长度不能超过{max}")
    @NotBlank(message ="所属部门为空，或者类型错误")
    private String  ssbm;


    @ApiModelProperty(value = "关键字")
    @Length(max = 100, message = "关键字长度不能超过{max}")
    @NotBlank(message ="关键字为空，或者类型错误")
    private String  gjz;


    @ApiModelProperty(value = "英文缩写")
    @Length(max = 100, message = "英文缩写长度不能超过{max}")
    private String  ywsx;


    @ApiModelProperty(value = "办理地点")
    @Length(max = 100, message = "办理地点长度不能超过{max}")
    private String  bldd;


    @ApiModelProperty(value = "咨询电话")
    @Length(max = 100, message = "咨询电话长度不能超过{max}")
    private String  zxdh;


    @ApiModelProperty(value = "打开方式")
    @Length(max = 2, message = "打开方式长度不能超过{max}")
    @NotBlank(message ="打开方式为空，或者类型错误")
    private String  dkfs;


    @ApiModelProperty(value = "是否启用")
    @Length(max = 2, message = "是否启用长度不能超过{max}")
    @NotBlank(message ="是否启用为空，或者类型错误")
    private String  sfqy;


    @ApiModelProperty(value = "加载方式")
    @Length(max = 2, message = "加载方式长度不能超过{max}")
    @NotBlank(message ="加载方式为空，或者类型错误")
    private String  jzfs;


    @ApiModelProperty(value = "是否登录")
    @Length(max = 2, message = "是否登录长度不能超过{max}")
    @NotBlank(message ="是否登录为空，或者类型错误")
    private String  isLogin;


    @ApiModelProperty(value = "首页显示")
    @Length(max = 2, message = "首页显示长度不能超过{max}")
    @NotBlank(message ="首页显示为空，或者类型错误")
    private String  syxs;


    @ApiModelProperty(value = "分组推荐")
    @Length(max = 100, message = "分组推荐长度不能超过{max}")
    private String  fztj;


    @ApiModelProperty(value = "排序")
    @Range(min = 0, max = 2147483647, message = "排序取值范围为0-2147483647")
    private Integer  sort;


    @ApiModelProperty(value = "备注")
    @Length(max = 2000, message = "备注长度不能超过{max}")
    private String  bz;


    @ApiModelProperty(value = "是否显示简介")
    @Length(max = 2, message = "是否显示简介长度不能超过{max}")
    @NotBlank(message ="是否显示简介为空，或者类型错误")
    private String  sfxsjj;


    @ApiModelProperty(value = "应用简介")
    private String  yyjj;


    @ApiModelProperty(value = "应用来源")
    @Length(max = 32, message = "应用来源长度不能超过{max}")
    @NotBlank(message ="应用来源为空，或者类型错误")
    private String  yyly;


    @ApiModelProperty(value = "第三方应用Id")
    @Length(max = 32, message = "第三方应用Id长度不能超过{max}")
    private String  dsfyyId;


    @ApiModelProperty(value = "资源组id")
    @Length(max = 32, message = "资源组id长度不能超过{max}")
    private String  zyzid;


    @ApiModelProperty(value = "资源id")
    @Length(max = 32, message = "资源id长度不能超过{max}")
    private String  zyid;


    @ApiModelProperty(value = "租户ID")
    @Length(max = 32, message = "租户ID长度不能超过{max}")
    private String  tenantId;


    @ApiModelProperty(value = "创建人唯一健",hidden = true)
    @TableField(fill = FieldFill.INSERT)
    @Length(max = 32, message = "创建人唯一健长度不能超过{max}")
    private String  createId;


    @ApiModelProperty(value = "创建时间",hidden = true)
    @TableField(fill = FieldFill.INSERT)
    @Length(max = 20, message = "创建时间长度不能超过{max}")
    private Date  createTime;


    @ApiModelProperty(value = "最后更新人唯一健",hidden = true)
    @TableField(fill = FieldFill.UPDATE)
    @Length(max = 32, message = "最后更新人唯一健长度不能超过{max}")
    private String  updateId;


    @ApiModelProperty(value = "最后更新时间",hidden = true)
    @TableField(fill = FieldFill.UPDATE)
    @Length(max = 20, message = "最后更新时间长度不能超过{max}")
    private Date  updateTime;


    public interface Save extends Default {
    }

    public interface Update extends Default {
    }
}