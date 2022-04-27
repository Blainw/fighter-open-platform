package ai.wiseloong.open.sdk.common;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * 请求form
 * @author tanghc
 */
@Getter
@Setter
public class RequestForm  {

    /** 请求表单内容 */
    private Map<String, String> form;

    private String charset;

    private RequestMethod requestMethod = RequestMethod.POST;

    public RequestForm(Map<String, String> m) {
        this.form = m;
    }
}
