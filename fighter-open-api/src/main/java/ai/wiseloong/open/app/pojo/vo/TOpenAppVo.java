package ai.wiseloong.open.app.pojo.vo;

import ai.wiseloong.open.app.pojo.TOpenApp;
import io.swagger.annotations.ApiModel;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


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
public class TOpenAppVo  extends TOpenApp {

   private static final long serialVersionUID = 1L;


}