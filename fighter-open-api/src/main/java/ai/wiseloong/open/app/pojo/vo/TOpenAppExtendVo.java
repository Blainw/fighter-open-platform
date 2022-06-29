package ai.wiseloong.open.app.pojo.vo;

import ai.wiseloong.open.app.pojo.TOpenAppExtend;
import io.swagger.annotations.ApiModel;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


/**
 *
 * @ClassName: TOpenAppExtendVo
 * @Description: 开发平台-应用扩展-Vo对象
 * @author 张小平
 */

@ApiModel(description = "开发平台-应用扩展VO对象")
@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class TOpenAppExtendVo  extends TOpenAppExtend {

   private static final long serialVersionUID = 1L;


}