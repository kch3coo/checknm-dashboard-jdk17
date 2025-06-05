package ryd.checknm.dashboard.module.iot.dal.mysql.plugin;

import ryd.checknm.dashboard.framework.common.pojo.PageResult;
import ryd.checknm.dashboard.framework.mybatis.core.mapper.BaseMapperX;
import ryd.checknm.dashboard.framework.mybatis.core.query.LambdaQueryWrapperX;
import ryd.checknm.dashboard.module.iot.controller.admin.plugin.vo.config.PluginConfigPageReqVO;
import ryd.checknm.dashboard.module.iot.dal.dataobject.plugin.IotPluginConfigDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IotPluginConfigMapper extends BaseMapperX<IotPluginConfigDO> {

    default PageResult<IotPluginConfigDO> selectPage(PluginConfigPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<IotPluginConfigDO>()
                .likeIfPresent(IotPluginConfigDO::getName, reqVO.getName())
                .eqIfPresent(IotPluginConfigDO::getStatus, reqVO.getStatus())
                .orderByDesc(IotPluginConfigDO::getId));
    }

    default List<IotPluginConfigDO> selectListByStatusAndDeployType(Integer status, Integer deployType) {
        return selectList(new LambdaQueryWrapperX<IotPluginConfigDO>()
                .eq(IotPluginConfigDO::getStatus, status)
                .eq(IotPluginConfigDO::getDeployType, deployType)
                .orderByAsc(IotPluginConfigDO::getId));
    }

    default IotPluginConfigDO selectByPluginKey(String pluginKey) {
        return selectOne(IotPluginConfigDO::getPluginKey, pluginKey);
    }

}