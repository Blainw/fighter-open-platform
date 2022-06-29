package ai.wiseloong.open.request;

import ai.wiseloong.open.response.GetStoryResponse;

public class GetStoryRequest extends BaseRequest<GetStoryResponse> {
    @Override
    protected String method() {
        return "story.get";
    }

}
