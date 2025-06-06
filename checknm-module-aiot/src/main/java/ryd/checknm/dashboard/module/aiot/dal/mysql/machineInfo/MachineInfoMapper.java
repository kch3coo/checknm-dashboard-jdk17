package ryd.checknm.dashboard.module.aiot.dal.mysql.machineInfo;

import java.util.*;

import ryd.checknm.dashboard.framework.common.pojo.PageResult;
import ryd.checknm.dashboard.framework.mybatis.core.query.LambdaQueryWrapperX;
import ryd.checknm.dashboard.framework.mybatis.core.mapper.BaseMapperX;
import ryd.checknm.dashboard.module.aiot.dal.dataobject.machineInfo.MachineInfoDO;
import org.apache.ibatis.annotations.Mapper;
import ryd.checknm.dashboard.module.aiot.controller.admin.machineInfo.vo.*;

/**
 * 设备信息 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface MachineInfoMapper extends BaseMapperX<MachineInfoDO> {

    default PageResult<MachineInfoDO> selectPage(MachineInfoPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<MachineInfoDO>()
                .likeIfPresent(MachineInfoDO::getCompanyName, reqVO.getCompanyName())
                .likeIfPresent(MachineInfoDO::getFactoryName, reqVO.getFactoryName())
                .likeIfPresent(MachineInfoDO::getProductLine, reqVO.getProductLine())
                .likeIfPresent(MachineInfoDO::getDeviceName, reqVO.getDeviceName())
                .eqIfPresent(MachineInfoDO::getDeviceType, reqVO.getDeviceType())
                .betweenIfPresent(MachineInfoDO::getLastCheckTime, reqVO.getLastCheckTime())
                .eqIfPresent(MachineInfoDO::getLastMaintainer, reqVO.getLastMaintainer())
                .betweenIfPresent(MachineInfoDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(MachineInfoDO::getId));
    }

}