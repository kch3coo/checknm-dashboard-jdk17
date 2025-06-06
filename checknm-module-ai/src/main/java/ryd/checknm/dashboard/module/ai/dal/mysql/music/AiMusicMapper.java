package ryd.checknm.dashboard.module.ai.dal.mysql.music;

import ryd.checknm.dashboard.framework.common.pojo.PageResult;
import ryd.checknm.dashboard.framework.mybatis.core.mapper.BaseMapperX;
import ryd.checknm.dashboard.framework.mybatis.core.query.LambdaQueryWrapperX;
import ryd.checknm.dashboard.module.ai.controller.admin.music.vo.AiMusicPageReqVO;
import ryd.checknm.dashboard.module.ai.dal.dataobject.music.AiMusicDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * AI 音乐 Mapper
 *
 * @author xiaoxin
 */
@Mapper
public interface AiMusicMapper extends BaseMapperX<AiMusicDO> {

    default List<AiMusicDO> selectListByStatus(Integer status) {
        return selectList(AiMusicDO::getStatus, status);
    }

    default PageResult<AiMusicDO> selectPage(AiMusicPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<AiMusicDO>()
                .eqIfPresent(AiMusicDO::getUserId, reqVO.getUserId())
                .eqIfPresent(AiMusicDO::getTitle, reqVO.getTitle())
                .eqIfPresent(AiMusicDO::getStatus, reqVO.getStatus())
                .eqIfPresent(AiMusicDO::getGenerateMode, reqVO.getGenerateMode())
                .betweenIfPresent(AiMusicDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(AiMusicDO::getPublicStatus, reqVO.getPublicStatus())
                .orderByDesc(AiMusicDO::getId));
    }

    default PageResult<AiMusicDO> selectPageByMy(AiMusicPageReqVO reqVO, Long userId) {
        return selectPage(reqVO, new LambdaQueryWrapperX<AiMusicDO>()
                // 情况一：公开
                .eq(Boolean.TRUE.equals(reqVO.getPublicStatus()), AiMusicDO::getPublicStatus, reqVO.getPublicStatus())
                // 情况二：私有
                .eq(Boolean.FALSE.equals(reqVO.getPublicStatus()), AiMusicDO::getUserId, userId)
                .orderByAsc(AiMusicDO::getId));
    }
    
}
