package ryd.checknm.dashboard.module.promotion.dal.mysql.diy;

import ryd.checknm.dashboard.framework.common.pojo.PageResult;
import ryd.checknm.dashboard.framework.mybatis.core.mapper.BaseMapperX;
import ryd.checknm.dashboard.framework.mybatis.core.query.LambdaQueryWrapperX;
import ryd.checknm.dashboard.module.promotion.controller.admin.diy.vo.template.DiyTemplatePageReqVO;
import ryd.checknm.dashboard.module.promotion.dal.dataobject.diy.DiyTemplateDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 装修模板 Mapper
 *
 * @author owen
 */
@Mapper
public interface DiyTemplateMapper extends BaseMapperX<DiyTemplateDO> {

    default PageResult<DiyTemplateDO> selectPage(DiyTemplatePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<DiyTemplateDO>()
                .likeIfPresent(DiyTemplateDO::getName, reqVO.getName())
                .eqIfPresent(DiyTemplateDO::getUsed, reqVO.getUsed())
                .betweenIfPresent(DiyTemplateDO::getUsedTime, reqVO.getUsedTime())
                .betweenIfPresent(DiyTemplateDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(DiyTemplateDO::getUsed) // 排序规则1：已使用的排到最前面
                .orderByDesc(DiyTemplateDO::getId)); // 排序规则2：新创建的排到前面
    }

    default DiyTemplateDO selectByUsed(boolean used) {
        return selectOne(DiyTemplateDO::getUsed, used);
    }

    default DiyTemplateDO selectByName(String name) {
        return selectOne(DiyTemplateDO::getName, name);
    }

}
