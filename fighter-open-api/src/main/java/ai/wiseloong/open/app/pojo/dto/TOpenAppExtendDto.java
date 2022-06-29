package ai.wiseloong.open.app.pojo.dto;

import ai.wiseloong.extend.utils.mybatis.annotation.SignQueryLike;
import io.swagger.annotations.ApiModel;
import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import lombok.Data;

/**
 *
 * @ClassName: TOpenAppExtendDto
 * @Description: 开发平台-应用扩展-Dto对象
 * @author 张小平
 */
@ApiModel(description = "开发平台-应用扩展Dto对象")
@Data
public class TOpenAppExtendDto implements Serializable{

private static final long serialVersionUID = 1L;


   @ApiModelProperty(value = "应用Id")
   @SignQueryLike
   private String  appId;

   @ApiModelProperty(value = "浏览次数")
   private Long  llcs;

   @ApiModelProperty(value = "使用次数")
   private Long  sycs;

   @ApiModelProperty(value = "收藏次数")
   private Long  sccs;

   @ApiModelProperty(value = "平均评分")
   private Double  pjfs;

   @ApiModelProperty(value = "扩展json")
   @SignQueryLike
   private String  kzdx;






}