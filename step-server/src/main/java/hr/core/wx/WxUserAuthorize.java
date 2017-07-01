package hr.core.wx;

import com.hr.pb.WX.PBWxUserAccessToken;

public class WxUserAuthorize {

	public WxUserAuthorize(PBWxUserAccessToken userAccessToken) {
		super();
		this.userAccessToken = userAccessToken;
	}

	private PBWxUserAccessToken userAccessToken;

	public PBWxUserAccessToken getUserAccessToken() {
		return userAccessToken;
	}

}
