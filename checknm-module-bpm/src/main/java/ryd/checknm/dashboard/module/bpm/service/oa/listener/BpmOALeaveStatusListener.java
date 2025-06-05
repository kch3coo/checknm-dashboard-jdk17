package ryd.checknm.dashboard.module.bpm.service.oa.listener;

import ryd.checknm.dashboard.module.bpm.api.event.BpmProcessInstanceStatusEvent;
import ryd.checknm.dashboard.module.bpm.api.event.BpmProcessInstanceStatusEventListener;
import ryd.checknm.dashboard.module.bpm.service.oa.BpmOALeaveService;
import ryd.checknm.dashboard.module.bpm.service.oa.BpmOALeaveServiceImpl;
import org.springframework.stereotype.Component;

import jakarta.annotation.Resource;

/**
 * OA 请假单的结果的监听器实现类
 *
 * @author 芋道源码
 */
@Component
public class BpmOALeaveStatusListener extends BpmProcessInstanceStatusEventListener {

    @Resource
    private BpmOALeaveService leaveService;

    @Override
    protected String getProcessDefinitionKey() {
        return BpmOALeaveServiceImpl.PROCESS_KEY;
    }

    @Override
    protected void onEvent(BpmProcessInstanceStatusEvent event) {
        leaveService.updateLeaveStatus(Long.parseLong(event.getBusinessKey()), event.getStatus());
    }

}
