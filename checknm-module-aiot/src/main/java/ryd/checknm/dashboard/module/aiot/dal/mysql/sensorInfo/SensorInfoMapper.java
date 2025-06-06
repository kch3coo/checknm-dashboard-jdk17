package ryd.checknm.dashboard.module.aiot.dal.mysql.sensorInfo;

import java.util.*;

import ryd.checknm.dashboard.framework.common.pojo.PageResult;
import ryd.checknm.dashboard.framework.mybatis.core.query.LambdaQueryWrapperX;
import ryd.checknm.dashboard.framework.mybatis.core.mapper.BaseMapperX;
import ryd.checknm.dashboard.module.aiot.dal.dataobject.sensorInfo.SensorInfoDO;
import org.apache.ibatis.annotations.Mapper;
import ryd.checknm.dashboard.module.aiot.controller.admin.sensorInfo.vo.*;

/**
 * 传感器信息 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface SensorInfoMapper extends BaseMapperX<SensorInfoDO> {

    default PageResult<SensorInfoDO> selectPage(SensorInfoPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<SensorInfoDO>()
                .eqIfPresent(SensorInfoDO::getUuid, reqVO.getUuid())
                .eqIfPresent(SensorInfoDO::getMac, reqVO.getMac())
                .eqIfPresent(SensorInfoDO::getGatewayMac, reqVO.getGatewayMac())
                .eqIfPresent(SensorInfoDO::getType, reqVO.getType())
                .eqIfPresent(SensorInfoDO::getProducer, reqVO.getProducer())
                .eqIfPresent(SensorInfoDO::getQrCode, reqVO.getQrCode())
                .eqIfPresent(SensorInfoDO::getVersion, reqVO.getVersion())
                .eqIfPresent(SensorInfoDO::getStatus, reqVO.getStatus())
                .orderByDesc(SensorInfoDO::getId));
    }

}