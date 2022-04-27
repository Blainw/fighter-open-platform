package ai.wiseloong.open.sdk.model;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class GetStoryModel {

    @JSONField(name = "name")
    private String name;
}
