package ryd.checknm.dashboard.module.system.api.notify;

import ryd.checknm.dashboard.module.system.api.notify.dto.NotifySendSingleToUserReqDTO;
import ryd.checknm.dashboard.module.system.service.notify.NotifySendService;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;

/**
 * 站内信发送 API 实现类
 *
 * @author xrcoder
 */
@Service
public class NotifyMessageSendApiImpl implements NotifyMessageSendApi {

    @Resource
    private NotifySendService notifySendService;

    @Override
    public Long sendSingleMessageToAdmin(NotifySendSingleToUserReqDTO reqDTO) {
        return notifySendService.sendSingleNotifyToAdmin(reqDTO.getUserId(),
                reqDTO.getTemplateCode(), reqDTO.getTemplateParams());
    }

    @Override
    public Long sendSingleMessageToMember(NotifySendSingleToUserReqDTO reqDTO) {
        return notifySendService.sendSingleNotifyToMember(reqDTO.getUserId(),
                reqDTO.getTemplateCode(), reqDTO.getTemplateParams());
    }

}
