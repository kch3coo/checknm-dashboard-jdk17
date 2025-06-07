package ryd.checknm.dashboard.module.aiot.dal.mysql.qrcodeinfo;

import java.util.*;

import ryd.checknm.dashboard.framework.common.pojo.PageResult;
import ryd.checknm.dashboard.framework.mybatis.core.query.LambdaQueryWrapperX;
import ryd.checknm.dashboard.framework.mybatis.core.mapper.BaseMapperX;
import ryd.checknm.dashboard.module.aiot.dal.dataobject.qrcodeinfo.QrcodeInfoDO;
import org.apache.ibatis.annotations.Mapper;
import ryd.checknm.dashboard.module.aiot.controller.admin.qrcodeinfo.vo.*;

/**
 * 二维码信息 Mapper
 *
 * @author RYD
 */
@Mapper
public interface QrcodeInfoMapper extends BaseMapperX<QrcodeInfoDO> {

    default PageResult<QrcodeInfoDO> selectPage(QrcodeInfoPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<QrcodeInfoDO>()
                .eqIfPresent(QrcodeInfoDO::getInfo, reqVO.getInfo())
                .betweenIfPresent(QrcodeInfoDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(QrcodeInfoDO::getType, reqVO.getType())
                .eqIfPresent(QrcodeInfoDO::getStatus, reqVO.getStatus())
                .orderByDesc(QrcodeInfoDO::getId));
    }

}