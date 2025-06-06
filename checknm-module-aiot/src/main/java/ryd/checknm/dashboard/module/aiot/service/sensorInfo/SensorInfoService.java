package ryd.checknm.dashboard.module.aiot.service.sensorInfo;

import java.util.*;
import jakarta.validation.*;
import ryd.checknm.dashboard.module.aiot.controller.admin.sensorInfo.vo.*;
import ryd.checknm.dashboard.module.aiot.dal.dataobject.sensorInfo.SensorInfoDO;
import ryd.checknm.dashboard.framework.common.pojo.PageResult;
import ryd.checknm.dashboard.framework.common.pojo.PageParam;

/**
 * 传感器信息 Service 接口
 *
 * @author 芋道源码
 */
public interface SensorInfoService {

    /**
     * 创建传感器信息
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createSensorInfo(@Valid SensorInfoSaveReqVO createReqVO);

    /**
     * 更新传感器信息
     *
     * @param updateReqVO 更新信息
     */
    void updateSensorInfo(@Valid SensorInfoSaveReqVO updateReqVO);

    /**
     * 删除传感器信息
     *
     * @param id 编号
     */
    void deleteSensorInfo(Long id);

    /**
    * 批量删除传感器信息
    *
    * @param ids 编号
    */
    void deleteSensorInfoListByIds(List<Long> ids);

    /**
     * 获得传感器信息
     *
     * @param id 编号
     * @return 传感器信息
     */
    SensorInfoDO getSensorInfo(Long id);

    /**
     * 获得传感器信息分页
     *
     * @param pageReqVO 分页查询
     * @return 传感器信息分页
     */
    PageResult<SensorInfoDO> getSensorInfoPage(SensorInfoPageReqVO pageReqVO);

}