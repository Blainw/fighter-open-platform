package ai.wiseloong.open.pojo.vo;

import  ai.wiseloong.open.pojo.TOpenApp;
import io.swagger.annotations.ApiModel;
import java.io.Serializable;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import io.swagger.annotations.ApiModelProperty;


/**
 *
 * @ClassName: TOpenAppVo
 * @Description: 开放平台-应用-Vo对象
 * @author 张小平
 */

@ApiModel(description = "开放平台-应用VO对象")
@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class TOpenAppVo  extends TOpenApp{

   private static final long serialVersionUID = 1L;


}