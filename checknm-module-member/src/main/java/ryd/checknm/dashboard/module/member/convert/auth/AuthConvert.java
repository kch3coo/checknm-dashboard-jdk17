package ryd.checknm.dashboard.module.member.convert.auth;

import ryd.checknm.dashboard.module.member.controller.app.auth.vo.*;
import ryd.checknm.dashboard.module.member.controller.app.social.vo.AppSocialUserUnbindReqVO;
import ryd.checknm.dashboard.module.member.controller.app.user.vo.AppMemberUserResetPasswordReqVO;
import ryd.checknm.dashboard.framework.common.biz.system.oauth2.dto.OAuth2AccessTokenRespDTO;
import ryd.checknm.dashboard.module.system.api.sms.dto.code.SmsCodeSendReqDTO;
import ryd.checknm.dashboard.module.system.api.sms.dto.code.SmsCodeUseReqDTO;
import ryd.checknm.dashboard.module.system.api.sms.dto.code.SmsCodeValidateReqDTO;
import ryd.checknm.dashboard.module.system.api.social.dto.SocialUserBindReqDTO;
import ryd.checknm.dashboard.module.system.api.social.dto.SocialUserUnbindReqDTO;
import ryd.checknm.dashboard.module.system.api.social.dto.SocialWxJsapiSignatureRespDTO;
import ryd.checknm.dashboard.module.system.enums.sms.SmsSceneEnum;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AuthConvert {

    AuthConvert INSTANCE = Mappers.getMapper(AuthConvert.class);

    SocialUserBindReqDTO convert(Long userId, Integer userType, AppAuthSocialLoginReqVO reqVO);
    SocialUserUnbindReqDTO convert(Long userId, Integer userType, AppSocialUserUnbindReqVO reqVO);

    SmsCodeSendReqDTO convert(AppAuthSmsSendReqVO reqVO);
    SmsCodeUseReqDTO convert(AppMemberUserResetPasswordReqVO reqVO, SmsSceneEnum scene, String usedIp);
    SmsCodeUseReqDTO convert(AppAuthSmsLoginReqVO reqVO, Integer scene, String usedIp);

    AppAuthLoginRespVO convert(OAuth2AccessTokenRespDTO bean, String openid);

    SmsCodeValidateReqDTO convert(AppAuthSmsValidateReqVO bean);

    SocialWxJsapiSignatureRespDTO convert(SocialWxJsapiSignatureRespDTO bean);

}
