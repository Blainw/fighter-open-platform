package ai.wiseloong.open.pojo.dto;

import ai.wiseloong.extend.utils.mybatis.annotation.SignQueryLike;
import io.swagger.annotations.ApiModel;
import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import lombok.Data;

/**
 *
 * @ClassName: TOpenAppDto
 * @Description: 开放平台-应用-Dto对象
 * @author 张小平
 */
@ApiModel(description = "开放平台-应用Dto对象")
@Data
public class TOpenAppDto implements Serializable{

private static final long serialVersionUID = 1L;


   @ApiModelProperty(value = "应用名称")
   @SignQueryLike
   private String  mc;

   @ApiModelProperty(value = "pc端url")
   @SignQueryLike
   private String  pcUrl;

   @ApiModelProperty(value = "手机端url")
   @SignQueryLike
   private String  sjUrl;

   @ApiModelProperty(value = "图标")
   @SignQueryLike
   private String  icons;

   @ApiModelProperty(value = "应用分类")
   private String  yyfl;

   @ApiModelProperty(value = "所属分类")
   private String  ssfl;

   @ApiModelProperty(value = "应用类型")
   private String  yylx;

   @ApiModelProperty(value = "办理方式")
   private String  blfs;

   @ApiModelProperty(value = "所属部门")
   @SignQueryLike
   private String  ssbm;

   @ApiModelProperty(value = "关键字")
   @SignQueryLike
   private String  gjz;

   @ApiModelProperty(value = "英文缩写")
   @SignQueryLike
   private String  ywsx;

   @ApiModelProperty(value = "办理地点")
   @SignQueryLike
   private String  bldd;

   @ApiModelProperty(value = "咨询电话")
   @SignQueryLike
   private String  zxdh;

   @ApiModelProperty(value = "打开方式")
   @SignQueryLike
   private String  dkfs;

   @ApiModelProperty(value = "是否启用")
   private String  sfqy;

   @ApiModelProperty(value = "加载方式")
   private String  jzfs;

   @ApiModelProperty(value = "是否登录")
   private String  isLogin;

   @ApiModelProperty(value = "首页显示")
   private String  syxs;

   @ApiModelProperty(value = "分组推荐")
   @SignQueryLike
   private String  fztj;

   @ApiModelProperty(value = "排序")
   private Integer  sort;

   @ApiModelProperty(value = "备注")
   @SignQueryLike
   private String  bz;

   @ApiModelProperty(value = "是否显示简介")
   private String  sfxsjj;

   @ApiModelProperty(value = "应用简介")
   @SignQueryLike
   private String  yyjj;

   @ApiModelProperty(value = "应用来源")
   @SignQueryLike
   private String  yyly;

   @ApiModelProperty(value = "第三方应用Id")
   @SignQueryLike
   private String  dsfyyId;

   @ApiModelProperty(value = "资源组id")
   @SignQueryLike
   private String  zyzid;

   @ApiModelProperty(value = "资源id")
   @SignQueryLike
   private String  zyid;






}