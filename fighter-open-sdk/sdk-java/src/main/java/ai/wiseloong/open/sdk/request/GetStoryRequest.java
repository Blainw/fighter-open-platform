package ai.wiseloong.open.sdk.request;

import ai.wiseloong.open.sdk.response.GetStoryResponse;

public class GetStoryRequest extends BaseRequest<GetStoryResponse> {
    @Override
    protected String method() {
        return "story.get";
    }

}
