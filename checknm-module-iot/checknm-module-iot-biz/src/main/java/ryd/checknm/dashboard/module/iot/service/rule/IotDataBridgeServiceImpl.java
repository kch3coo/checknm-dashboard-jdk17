package ryd.checknm.dashboard.module.iot.service.rule;

import ryd.checknm.dashboard.framework.common.pojo.PageResult;
import ryd.checknm.dashboard.framework.common.util.object.BeanUtils;
import ryd.checknm.dashboard.module.iot.controller.admin.rule.vo.databridge.IotDataBridgePageReqVO;
import ryd.checknm.dashboard.module.iot.controller.admin.rule.vo.databridge.IotDataBridgeSaveReqVO;
import ryd.checknm.dashboard.module.iot.dal.dataobject.rule.IotDataBridgeDO;
import ryd.checknm.dashboard.module.iot.dal.mysql.rule.IotDataBridgeMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import static ryd.checknm.dashboard.framework.common.exception.util.ServiceExceptionUtil.exception;
import static ryd.checknm.dashboard.module.iot.enums.ErrorCodeConstants.DATA_BRIDGE_NOT_EXISTS;

/**
 * IoT 数据桥梁 Service 实现类
 *
 * @author HUIHUI
 */
@Service
@Validated
public class IotDataBridgeServiceImpl implements IotDataBridgeService {

    @Resource
    private IotDataBridgeMapper dataBridgeMapper;

    @Override
    public Long createDataBridge(IotDataBridgeSaveReqVO createReqVO) {
        // 插入
        IotDataBridgeDO dataBridge = BeanUtils.toBean(createReqVO, IotDataBridgeDO.class);
        dataBridgeMapper.insert(dataBridge);
        // 返回
        return dataBridge.getId();
    }

    @Override
    public void updateDataBridge(IotDataBridgeSaveReqVO updateReqVO) {
        // 校验存在
        validateDataBridgeExists(updateReqVO.getId());
        // 更新
        IotDataBridgeDO updateObj = BeanUtils.toBean(updateReqVO, IotDataBridgeDO.class);
        dataBridgeMapper.updateById(updateObj);
    }

    @Override
    public void deleteDataBridge(Long id) {
        // 校验存在
        validateDataBridgeExists(id);
        // 删除
        dataBridgeMapper.deleteById(id);
    }

    private void validateDataBridgeExists(Long id) {
        if (dataBridgeMapper.selectById(id) == null) {
            throw exception(DATA_BRIDGE_NOT_EXISTS);
        }
    }

    @Override
    public IotDataBridgeDO getDataBridge(Long id) {
        return dataBridgeMapper.selectById(id);
    }

    @Override
    public PageResult<IotDataBridgeDO> getDataBridgePage(IotDataBridgePageReqVO pageReqVO) {
        return dataBridgeMapper.selectPage(pageReqVO);
    }

}