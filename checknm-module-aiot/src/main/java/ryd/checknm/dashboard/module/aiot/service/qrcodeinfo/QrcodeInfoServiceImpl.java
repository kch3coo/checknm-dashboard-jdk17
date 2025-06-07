package ryd.checknm.dashboard.module.aiot.service.qrcodeinfo;

import cn.hutool.core.collection.CollUtil;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import ryd.checknm.dashboard.module.aiot.controller.admin.qrcodeinfo.vo.*;
import ryd.checknm.dashboard.module.aiot.dal.dataobject.qrcodeinfo.QrcodeInfoDO;
import ryd.checknm.dashboard.framework.common.pojo.PageResult;
import ryd.checknm.dashboard.framework.common.pojo.PageParam;
import ryd.checknm.dashboard.framework.common.util.object.BeanUtils;

import ryd.checknm.dashboard.module.aiot.dal.mysql.qrcodeinfo.QrcodeInfoMapper;

import static ryd.checknm.dashboard.framework.common.exception.util.ServiceExceptionUtil.exception;
import static ryd.checknm.dashboard.framework.common.util.collection.CollectionUtils.convertList;
import static ryd.checknm.dashboard.framework.common.util.collection.CollectionUtils.diffList;
import static ryd.checknm.dashboard.module.aiot.enums.ErrorCodeConstants.*;

/**
 * 二维码信息 Service 实现类
 *
 * @author RYD
 */
@Service
@Validated
public class QrcodeInfoServiceImpl implements QrcodeInfoService {

    @Resource
    private QrcodeInfoMapper qrcodeInfoMapper;

    @Override
    public Integer createQrcodeInfo(QrcodeInfoSaveReqVO createReqVO) {
        // 插入
        QrcodeInfoDO qrcodeInfo = BeanUtils.toBean(createReqVO, QrcodeInfoDO.class);
        qrcodeInfoMapper.insert(qrcodeInfo);
        // 返回
        return qrcodeInfo.getId();
    }

    @Override
    public void updateQrcodeInfo(QrcodeInfoSaveReqVO updateReqVO) {
        // 校验存在
        validateQrcodeInfoExists(updateReqVO.getId());
        // 更新
        QrcodeInfoDO updateObj = BeanUtils.toBean(updateReqVO, QrcodeInfoDO.class);
        qrcodeInfoMapper.updateById(updateObj);
    }

    @Override
    public void deleteQrcodeInfo(Integer id) {
        // 校验存在
        validateQrcodeInfoExists(id);
        // 删除
        qrcodeInfoMapper.deleteById(id);
    }

    @Override
    public void deleteQrcodeInfoListByIds(List<Integer> ids) {
        // 校验存在
        validateQrcodeInfoExists(ids);
        // 删除
        qrcodeInfoMapper.deleteByIds(ids);
    }

    private void validateQrcodeInfoExists(List<Integer> ids) {
        List<QrcodeInfoDO> list = qrcodeInfoMapper.selectByIds(ids);
        if (CollUtil.isEmpty(list) || list.size() != ids.size()) {
            throw exception(QRCODE_INFO_NOT_EXISTS);
        }
    }

    private void validateQrcodeInfoExists(Integer id) {
        if (qrcodeInfoMapper.selectById(id) == null) {
            throw exception(QRCODE_INFO_NOT_EXISTS);
        }
    }

    @Override
    public QrcodeInfoDO getQrcodeInfo(Integer id) {
        return qrcodeInfoMapper.selectById(id);
    }

    @Override
    public PageResult<QrcodeInfoDO> getQrcodeInfoPage(QrcodeInfoPageReqVO pageReqVO) {
        return qrcodeInfoMapper.selectPage(pageReqVO);
    }

}