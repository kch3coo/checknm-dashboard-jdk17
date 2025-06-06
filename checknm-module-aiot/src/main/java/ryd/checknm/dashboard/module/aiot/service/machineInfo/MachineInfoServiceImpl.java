package ryd.checknm.dashboard.module.aiot.service.machineInfo;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import ryd.checknm.dashboard.module.aiot.controller.admin.machineInfo.vo.*;
import ryd.checknm.dashboard.module.aiot.dal.dataobject.machineInfo.MachineInfoDO;
import ryd.checknm.dashboard.module.aiot.dal.dataobject.machineLocationInfo.MachineLocationInfoDO;
import ryd.checknm.dashboard.framework.common.pojo.PageResult;
import ryd.checknm.dashboard.framework.common.pojo.PageParam;
import ryd.checknm.dashboard.framework.common.util.object.BeanUtils;

import ryd.checknm.dashboard.module.aiot.dal.mysql.machineInfo.MachineInfoMapper;
import ryd.checknm.dashboard.module.aiot.dal.mysql.machineLocationInfo.MachineLocationInfoMapper;

import static ryd.checknm.dashboard.framework.common.exception.util.ServiceExceptionUtil.exception;
import static ryd.checknm.dashboard.framework.common.util.collection.CollectionUtils.convertList;
import static ryd.checknm.dashboard.framework.common.util.collection.CollectionUtils.diffList;
import static ryd.checknm.dashboard.module.aiot.enums.ErrorCodeConstants.*;

/**
 * 设备信息 Service 实现类
 *
 * @author kch3coo
 */
@Service
@Validated
public class MachineInfoServiceImpl implements MachineInfoService {

    @Resource
    private MachineInfoMapper machineInfoMapper;
    @Resource
    private MachineLocationInfoMapper machineLocationInfoMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createMachineInfo(MachineInfoSaveReqVO createReqVO) {
        // 插入
        MachineInfoDO machineInfo = BeanUtils.toBean(createReqVO, MachineInfoDO.class);
        machineInfoMapper.insert(machineInfo);

        // 插入子表
        createMachineLocationInfoList(machineInfo.getId(), createReqVO.getMachineLocationInfos());
        // 返回
        return machineInfo.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateMachineInfo(MachineInfoSaveReqVO updateReqVO) {
        // 校验存在
        validateMachineInfoExists(updateReqVO.getId());
        // 更新
        MachineInfoDO updateObj = BeanUtils.toBean(updateReqVO, MachineInfoDO.class);
        machineInfoMapper.updateById(updateObj);

        // 更新子表
        updateMachineLocationInfoList(updateReqVO.getId(), updateReqVO.getMachineLocationInfos());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteMachineInfo(Long id) {
        // 校验存在
        validateMachineInfoExists(id);
        // 删除
        machineInfoMapper.deleteById(id);

        // 删除子表
        deleteMachineLocationInfoByMachineId(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteMachineInfoListByIds(List<Long> ids) {
        // 校验存在
        validateMachineInfoExists(ids);
        // 删除
        machineInfoMapper.deleteByIds(ids);

        // 删除子表
        deleteMachineLocationInfoByMachineIds(ids);
    }

    private void validateMachineInfoExists(List<Long> ids) {
        List<MachineInfoDO> list = machineInfoMapper.selectByIds(ids);
        if (CollUtil.isEmpty(list) || list.size() != ids.size()) {
            throw exception(MACHINE_INFO_NOT_EXISTS);
        }
    }

    private void validateMachineInfoExists(Long id) {
        if (machineInfoMapper.selectById(id) == null) {
            throw exception(MACHINE_INFO_NOT_EXISTS);
        }
    }

    @Override
    public MachineInfoDO getMachineInfo(Long id) {
        return machineInfoMapper.selectById(id);
    }

    @Override
    public PageResult<MachineInfoDO> getMachineInfoPage(MachineInfoPageReqVO pageReqVO) {
        return machineInfoMapper.selectPage(pageReqVO);
    }

    // ==================== 子表（设备位置信息） ====================

    @Override
    public List<MachineLocationInfoDO> getMachineLocationInfoListByMachineId(Long machineId) {
        return machineLocationInfoMapper.selectListByMachineId(machineId);
    }

    private void createMachineLocationInfoList(Long machineId, List<MachineLocationInfoDO> list) {
        list.forEach(o -> o.setMachineId(machineId).clean());
        machineLocationInfoMapper.insertBatch(list);
    }

    private void updateMachineLocationInfoList(Long machineId, List<MachineLocationInfoDO> list) {
        list.forEach(o -> o.setMachineId(machineId).clean());
        List<MachineLocationInfoDO> oldList = machineLocationInfoMapper.selectListByMachineId(machineId);
        List<List<MachineLocationInfoDO>> diffList = diffList(oldList, list, (oldVal, newVal) -> {
            boolean same = ObjectUtil.equal(oldVal.getId(), newVal.getId());
            if (same) {
                newVal.setId(oldVal.getId()).clean(); // 解决更新情况下：updateTime 不更新
            }
            return same;
        });

        // 第二步，批量添加、修改、删除
        if (CollUtil.isNotEmpty(diffList.get(0))) {
            machineLocationInfoMapper.insertBatch(diffList.get(0));
        }
        if (CollUtil.isNotEmpty(diffList.get(1))) {
            machineLocationInfoMapper.updateBatch(diffList.get(1));
        }
        if (CollUtil.isNotEmpty(diffList.get(2))) {
            machineLocationInfoMapper.deleteByIds(convertList(diffList.get(2), MachineLocationInfoDO::getId));
        }
    }

    private void deleteMachineLocationInfoByMachineId(Long machineId) {
        machineLocationInfoMapper.deleteByMachineId(machineId);
    }

    private void deleteMachineLocationInfoByMachineIds(List<Long> machineIds) {
        machineLocationInfoMapper.deleteByMachineIds(machineIds);
    }

}