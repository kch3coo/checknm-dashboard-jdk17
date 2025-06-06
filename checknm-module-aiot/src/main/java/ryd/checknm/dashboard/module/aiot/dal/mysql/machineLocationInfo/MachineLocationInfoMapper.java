package ryd.checknm.dashboard.module.aiot.dal.mysql.machineLocationInfo;

import java.util.*;

import ryd.checknm.dashboard.framework.common.pojo.PageResult;
import ryd.checknm.dashboard.framework.common.pojo.PageParam;
import ryd.checknm.dashboard.framework.mybatis.core.query.LambdaQueryWrapperX;
import ryd.checknm.dashboard.framework.mybatis.core.mapper.BaseMapperX;
import ryd.checknm.dashboard.module.aiot.dal.dataobject.machineLocationInfo.MachineLocationInfoDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 设备位置信息 Mapper
 *
 * @author kch3coo
 */
@Mapper
public interface MachineLocationInfoMapper extends BaseMapperX<MachineLocationInfoDO> {

    default List<MachineLocationInfoDO> selectListByMachineId(Long machineId) {
        return selectList(MachineLocationInfoDO::getMachineId, machineId);
    }

    default int deleteByMachineId(Long machineId) {
        return delete(MachineLocationInfoDO::getMachineId, machineId);
    }

    default int deleteByMachineIds(List<Long> machineIds) {
        return deleteBatch(MachineLocationInfoDO::getMachineId, machineIds);
    }

}