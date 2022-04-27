package ai.wiseloong.open.sdk.request;

import ai.wiseloong.open.sdk.common.OpenConfig;
import ai.wiseloong.open.sdk.common.RequestForm;
import ai.wiseloong.open.sdk.common.RequestMethod;
import ai.wiseloong.open.sdk.response.BaseResponse;
import com.alibaba.fastjson.JSON;
import ai.wiseloong.open.sdk.util.ClassUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public abstract class BaseRequest<T extends BaseResponse> {

    private static final String EMPTY_JSON = "{}";

    private String method;
    private String version;

    private String bizContent = EMPTY_JSON;
    private Object bizModel;

    private RequestMethod requestMethod = RequestMethod.POST;



    private Class<T> responseClass = (Class<T>) ClassUtil.getSuperClassGenricType(this.getClass(), 0);;

    /**
     * 定义接口名称
     * @return 接口名称
     */
    protected abstract String method();

    public BaseRequest() {
        this.method = method();
        this.version = version();
    }

    protected BaseRequest(String method, String version) {
        this.method = method;
        this.version = version;
    }

    protected String version() {
        return version;
    }


    public RequestForm createRequestForm(OpenConfig openConfig) {
        // 公共请求参数
        Map<String, String> params = new HashMap<String, String>(16);
        params.put(openConfig.getMethodName(), this.method);
        params.put(openConfig.getFormatName(), openConfig.getFormatType());
        params.put(openConfig.getCharsetName(), openConfig.getCharset());
        params.put(openConfig.getSignTypeName(), openConfig.getSignType());
        String timestamp = new SimpleDateFormat(openConfig.getTimestampPattern()).format(new Date());
        params.put(openConfig.getTimestampName(), timestamp);
        String v = this.version == null ? openConfig.getDefaultVersion() : this.version;
        params.put(openConfig.getVersionName(), v);

        // 业务参数
        String biz_content = buildBizContent();

        params.put(openConfig.getDataName(), biz_content);

        RequestForm requestForm = new RequestForm(params);
        requestForm.setRequestMethod(getRequestMethod());
        requestForm.setCharset(openConfig.getCharset());

        return requestForm;
    }

    protected String buildBizContent() {
        if (bizModel != null) {
            return JSON.toJSONString(bizModel);
        } else {
            return this.bizContent;
        }
    }

    public String getMethod() {
        return method;
    }

    /**
     * 指定版本号
     *
     * @param version
     */
    public void setVersion(String version) {
        this.version = version;
    }

    public void setBizContent(String bizContent) {
        this.bizContent = bizContent;
    }

    public void setBizModel(Object bizModel) {
        this.bizModel = bizModel;
    }

    public Class<T> getResponseClass() {
        return responseClass;
    }


    public RequestMethod getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(RequestMethod requestMethod) {
        this.requestMethod = requestMethod;
    }
}
