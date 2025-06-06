package ryd.checknm.dashboard.module.aiot.service.machineInfo;

import java.util.*;
import jakarta.validation.*;
import ryd.checknm.dashboard.module.aiot.controller.admin.machineInfo.vo.*;
import ryd.checknm.dashboard.module.aiot.dal.dataobject.machineInfo.MachineInfoDO;
import ryd.checknm.dashboard.module.aiot.dal.dataobject.machineLocationInfo.MachineLocationInfoDO;
import ryd.checknm.dashboard.framework.common.pojo.PageResult;
import ryd.checknm.dashboard.framework.common.pojo.PageParam;

/**
 * 设备信息 Service 接口
 *
 * @author kch3coo
 */
public interface MachineInfoService {

    /**
     * 创建设备信息
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createMachineInfo(@Valid MachineInfoSaveReqVO createReqVO);

    /**
     * 更新设备信息
     *
     * @param updateReqVO 更新信息
     */
    void updateMachineInfo(@Valid MachineInfoSaveReqVO updateReqVO);

    /**
     * 删除设备信息
     *
     * @param id 编号
     */
    void deleteMachineInfo(Long id);

    /**
     * 批量删除设备信息
     *
     * @param ids 编号
     */
    void deleteMachineInfoListByIds(List<Long> ids);

    /**
     * 获得设备信息
     *
     * @param id 编号
     * @return 设备信息
     */
    MachineInfoDO getMachineInfo(Long id);

    /**
     * 获得设备信息分页
     *
     * @param pageReqVO 分页查询
     * @return 设备信息分页
     */
    PageResult<MachineInfoDO> getMachineInfoPage(MachineInfoPageReqVO pageReqVO);

    // ==================== 子表（设备位置信息） ====================

    /**
     * 获得设备位置信息列表
     *
     * @param machineId 设备id
     * @return 设备位置信息列表
     */
    List<MachineLocationInfoDO> getMachineLocationInfoListByMachineId(Long machineId);

}