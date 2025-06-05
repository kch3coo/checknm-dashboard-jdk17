package ryd.checknm.dashboard.module.iot.dal.mysql.plugin;

import ryd.checknm.dashboard.framework.mybatis.core.mapper.BaseMapperX;
import ryd.checknm.dashboard.module.iot.dal.dataobject.plugin.IotPluginInstanceDO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;
import java.util.List;

// TODO @li：参考 IotOtaUpgradeRecordMapper 的写法
@Mapper
public interface IotPluginInstanceMapper extends BaseMapperX<IotPluginInstanceDO> {

    default IotPluginInstanceDO selectByProcessId(String processId) {
        return selectOne(IotPluginInstanceDO::getProcessId, processId);
    }

    default List<IotPluginInstanceDO> selectListByHeartbeatTimeLt(LocalDateTime heartbeatTime) {
        return selectList(new LambdaQueryWrapper<IotPluginInstanceDO>()
                .lt(IotPluginInstanceDO::getHeartbeatTime, heartbeatTime));
    }

}