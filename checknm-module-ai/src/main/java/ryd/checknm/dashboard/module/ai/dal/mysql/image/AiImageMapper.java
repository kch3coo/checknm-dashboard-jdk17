package ryd.checknm.dashboard.module.ai.dal.mysql.image;

import ryd.checknm.dashboard.framework.common.pojo.PageResult;
import ryd.checknm.dashboard.framework.mybatis.core.mapper.BaseMapperX;
import ryd.checknm.dashboard.framework.mybatis.core.query.LambdaQueryWrapperX;
import ryd.checknm.dashboard.module.ai.controller.admin.image.vo.AiImagePageReqVO;
import ryd.checknm.dashboard.module.ai.controller.admin.image.vo.AiImagePublicPageReqVO;
import ryd.checknm.dashboard.module.ai.dal.dataobject.image.AiImageDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * AI 绘图 Mapper
 *
 * @author fansili
 */
@Mapper
public interface AiImageMapper extends BaseMapperX<AiImageDO> {

    default AiImageDO selectByTaskId(String taskId) {
        return selectOne(AiImageDO::getTaskId, taskId);
    }

    default PageResult<AiImageDO> selectPage(AiImagePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<AiImageDO>()
                .eqIfPresent(AiImageDO::getUserId, reqVO.getUserId())
                .eqIfPresent(AiImageDO::getPlatform, reqVO.getPlatform())
                .eqIfPresent(AiImageDO::getStatus, reqVO.getStatus())
                .eqIfPresent(AiImageDO::getPublicStatus, reqVO.getPublicStatus())
                .betweenIfPresent(AiImageDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(AiImageDO::getId));
    }

    default PageResult<AiImageDO> selectPageMy(Long userId, AiImagePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<AiImageDO>()
                .likeIfPresent(AiImageDO::getPrompt, reqVO.getPrompt())
                // 情况一：公开
                .eq(Boolean.TRUE.equals(reqVO.getPublicStatus()), AiImageDO::getPublicStatus, reqVO.getPublicStatus())
                // 情况二：私有
                .eq(Boolean.FALSE.equals(reqVO.getPublicStatus()), AiImageDO::getUserId, userId)
                .orderByDesc(AiImageDO::getId));
    }

    default PageResult<AiImageDO> selectPage(AiImagePublicPageReqVO pageReqVO) {
        return selectPage(pageReqVO, new LambdaQueryWrapperX<AiImageDO>()
                .eqIfPresent(AiImageDO::getPublicStatus, Boolean.TRUE)
                .likeIfPresent(AiImageDO::getPrompt, pageReqVO.getPrompt())
                .orderByDesc(AiImageDO::getId));
    }

    default List<AiImageDO> selectListByStatusAndPlatform(Integer status, String platform) {
        return selectList(AiImageDO::getStatus, status,
                AiImageDO::getPlatform, platform);
    }

}
