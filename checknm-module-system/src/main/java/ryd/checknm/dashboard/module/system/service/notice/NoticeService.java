package ryd.checknm.dashboard.module.system.service.notice;

import ryd.checknm.dashboard.framework.common.pojo.PageResult;
import ryd.checknm.dashboard.module.system.controller.admin.notice.vo.NoticePageReqVO;
import ryd.checknm.dashboard.module.system.controller.admin.notice.vo.NoticeSaveReqVO;
import ryd.checknm.dashboard.module.system.dal.dataobject.notice.NoticeDO;

/**
 * 通知公告 Service 接口
 */
public interface NoticeService {

    /**
     * 创建通知公告
     *
     * @param createReqVO 通知公告
     * @return 编号
     */
    Long createNotice(NoticeSaveReqVO createReqVO);

    /**
     * 更新通知公告
     *
     * @param reqVO 通知公告
     */
    void updateNotice(NoticeSaveReqVO reqVO);

    /**
     * 删除通知公告
     *
     * @param id 编号
     */
    void deleteNotice(Long id);

    /**
     * 获得通知公告分页列表
     *
     * @param reqVO 分页条件
     * @return 部门分页列表
     */
    PageResult<NoticeDO> getNoticePage(NoticePageReqVO reqVO);

    /**
     * 获得通知公告
     *
     * @param id 编号
     * @return 通知公告
     */
    NoticeDO getNotice(Long id);

}
