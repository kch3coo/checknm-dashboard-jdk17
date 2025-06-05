package ryd.checknm.dashboard.module.system.api.sms;

import ryd.checknm.dashboard.module.system.api.sms.dto.code.SmsCodeValidateReqDTO;
import ryd.checknm.dashboard.module.system.api.sms.dto.code.SmsCodeSendReqDTO;
import ryd.checknm.dashboard.module.system.api.sms.dto.code.SmsCodeUseReqDTO;
import ryd.checknm.dashboard.module.system.service.sms.SmsCodeService;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import jakarta.annotation.Resource;

/**
 * 短信验证码 API 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class SmsCodeApiImpl implements SmsCodeApi {

    @Resource
    private SmsCodeService smsCodeService;

    @Override
    public void sendSmsCode(SmsCodeSendReqDTO reqDTO) {
        smsCodeService.sendSmsCode(reqDTO);
    }

    @Override
    public void useSmsCode(SmsCodeUseReqDTO reqDTO) {
        smsCodeService.useSmsCode(reqDTO);
    }

    @Override
    public void validateSmsCode(SmsCodeValidateReqDTO reqDTO) {
        smsCodeService.validateSmsCode(reqDTO);
    }

}
