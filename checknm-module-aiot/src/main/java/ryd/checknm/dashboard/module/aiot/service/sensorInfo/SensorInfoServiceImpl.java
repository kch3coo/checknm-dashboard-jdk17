package ryd.checknm.dashboard.module.aiot.service.sensorInfo;

import cn.hutool.core.collection.CollUtil;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import ryd.checknm.dashboard.module.aiot.controller.admin.sensorInfo.vo.*;
import ryd.checknm.dashboard.module.aiot.dal.dataobject.sensorInfo.SensorInfoDO;
import ryd.checknm.dashboard.framework.common.pojo.PageResult;
import ryd.checknm.dashboard.framework.common.pojo.PageParam;
import ryd.checknm.dashboard.framework.common.util.object.BeanUtils;

import ryd.checknm.dashboard.module.aiot.dal.mysql.sensorInfo.SensorInfoMapper;

import static ryd.checknm.dashboard.framework.common.exception.util.ServiceExceptionUtil.exception;
import static ryd.checknm.dashboard.framework.common.util.collection.CollectionUtils.convertList;
import static ryd.checknm.dashboard.framework.common.util.collection.CollectionUtils.diffList;
import static ryd.checknm.dashboard.module.aiot.enums.ErrorCodeConstants.*;

/**
 * 传感器信息 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class SensorInfoServiceImpl implements SensorInfoService {

    @Resource
    private SensorInfoMapper sensorInfoMapper;

    @Override
    public Long createSensorInfo(SensorInfoSaveReqVO createReqVO) {
        // 插入
        SensorInfoDO sensorInfo = BeanUtils.toBean(createReqVO, SensorInfoDO.class);
        try {
            sensorInfoMapper.insert(sensorInfo);
        } catch (DuplicateKeyException e) {
            throw exception(SENSOR_INFO_ALREADY_EXISTS);
        }
        // 返回
        return sensorInfo.getId();
    }

    @Override
    public void updateSensorInfo(SensorInfoSaveReqVO updateReqVO) {
        // 校验存在
        validateSensorInfoExists(updateReqVO.getId());
        // 更新
        SensorInfoDO updateObj = BeanUtils.toBean(updateReqVO, SensorInfoDO.class);
        sensorInfoMapper.updateById(updateObj);
    }

    @Override
    public void deleteSensorInfo(Long id) {
        // 校验存在
        validateSensorInfoExists(id);
        // 删除
        sensorInfoMapper.deleteById(id);
    }

    @Override
        public void deleteSensorInfoListByIds(List<Long> ids) {
        // 校验存在
        validateSensorInfoExists(ids);
        // 删除
        sensorInfoMapper.deleteByIds(ids);
        }

    private void validateSensorInfoExists(List<Long> ids) {
        List<SensorInfoDO> list = sensorInfoMapper.selectByIds(ids);
        if (CollUtil.isEmpty(list) || list.size() != ids.size()) {
            throw exception(SENSOR_INFO_NOT_EXISTS);
        }
    }

    private void validateSensorInfoExists(Long id) {
        if (sensorInfoMapper.selectById(id) == null) {
            throw exception(SENSOR_INFO_NOT_EXISTS);
        }
    }

    @Override
    public SensorInfoDO getSensorInfo(Long id) {
        return sensorInfoMapper.selectById(id);
    }

    @Override
    public PageResult<SensorInfoDO> getSensorInfoPage(SensorInfoPageReqVO pageReqVO) {
        return sensorInfoMapper.selectPage(pageReqVO);
    }

}