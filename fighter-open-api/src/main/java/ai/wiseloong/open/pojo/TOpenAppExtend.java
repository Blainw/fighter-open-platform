package ai.wiseloong.open.pojo;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.groups.Default;
import javax.validation.constraints.*;
import java.util.Date;
import java.time.LocalDate;
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
 * @ClassName: TOpenAppExtend
 * @Description: 开发平台-应用扩展-实体对象
 * @author 张小平
 */
@ApiModel(description = "开发平台-应用扩展")
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_open_app_extend")
@SuperBuilder(toBuilder = true)
public class TOpenAppExtend implements Serializable{

private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "主键Id")
    @TableId(type = IdType.ASSIGN_UUID)
                @Length(max = 32, message = "主键Id长度不能超过{max}")
    private String  id;


    @ApiModelProperty(value = "应用Id")
            @Length(max = 32, message = "应用Id长度不能超过{max}")
    @NotBlank(message ="应用Id为空，或者类型错误")
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
            private String  kzdx;


    @ApiModelProperty(value = "租户ID")
            @Length(max = 32, message = "租户ID长度不能超过{max}")
    private String  tenantId;


    @ApiModelProperty(value = "创建人唯一健",hidden = true)
    @TableField(fill = FieldFill.INSERT)
            @Length(max = 32, message = "创建人唯一健长度不能超过{max}")
    private String  createId;


    @ApiModelProperty(value = "创建时间",hidden = true)
    @TableField(fill = FieldFill.INSERT)
            private  Date  createTime;


    @ApiModelProperty(value = "最后更新人唯一健",hidden = true)
    @TableField(fill = FieldFill.UPDATE)
            @Length(max = 32, message = "最后更新人唯一健长度不能超过{max}")
    private String  updateId;


    @ApiModelProperty(value = "最后更新时间",hidden = true)
    @TableField(fill = FieldFill.UPDATE)
            private  Date  updateTime;


    public interface Save extends Default {
    }

    public interface Update extends Default {
    }
}