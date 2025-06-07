package ryd.checknm.dashboard.module.aiot.service.qrcodeinfo;

import java.util.*;
import jakarta.validation.*;
import ryd.checknm.dashboard.module.aiot.controller.admin.qrcodeinfo.vo.*;
import ryd.checknm.dashboard.module.aiot.dal.dataobject.qrcodeinfo.QrcodeInfoDO;
import ryd.checknm.dashboard.framework.common.pojo.PageResult;
import ryd.checknm.dashboard.framework.common.pojo.PageParam;

/**
 * 二维码信息 Service 接口
 *
 * @author RYD
 */
public interface QrcodeInfoService {

    /**
     * 创建二维码信息
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Integer createQrcodeInfo(@Valid QrcodeInfoSaveReqVO createReqVO);

    /**
     * 更新二维码信息
     *
     * @param updateReqVO 更新信息
     */
    void updateQrcodeInfo(@Valid QrcodeInfoSaveReqVO updateReqVO);

    /**
     * 删除二维码信息
     *
     * @param id 编号
     */
    void deleteQrcodeInfo(Integer id);

    /**
     * 批量删除二维码信息
     *
     * @param ids 编号
     */
    void deleteQrcodeInfoListByIds(List<Integer> ids);

    /**
     * 获得二维码信息
     *
     * @param id 编号
     * @return 二维码信息
     */
    QrcodeInfoDO getQrcodeInfo(Integer id);

    /**
     * 获得二维码信息分页
     *
     * @param pageReqVO 分页查询
     * @return 二维码信息分页
     */
    PageResult<QrcodeInfoDO> getQrcodeInfoPage(QrcodeInfoPageReqVO pageReqVO);

}