package ryd.checknm.dashboard.module.promotion.convert.coupon;

import cn.hutool.core.map.MapUtil;
import ryd.checknm.dashboard.framework.common.pojo.PageResult;
import ryd.checknm.dashboard.module.promotion.controller.admin.coupon.vo.template.CouponTemplateCreateReqVO;
import ryd.checknm.dashboard.module.promotion.controller.admin.coupon.vo.template.CouponTemplatePageReqVO;
import ryd.checknm.dashboard.module.promotion.controller.admin.coupon.vo.template.CouponTemplateRespVO;
import ryd.checknm.dashboard.module.promotion.controller.admin.coupon.vo.template.CouponTemplateUpdateReqVO;
import ryd.checknm.dashboard.module.promotion.controller.app.coupon.vo.template.AppCouponTemplatePageReqVO;
import ryd.checknm.dashboard.module.promotion.controller.app.coupon.vo.template.AppCouponTemplateRespVO;
import ryd.checknm.dashboard.module.promotion.dal.dataobject.coupon.CouponTemplateDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Map;

/**
 * 优惠劵模板 Convert
 *
 * @author 芋道源码
 */
@Mapper
public interface CouponTemplateConvert {

    CouponTemplateConvert INSTANCE = Mappers.getMapper(CouponTemplateConvert.class);

    CouponTemplateDO convert(CouponTemplateCreateReqVO bean);

    CouponTemplateDO convert(CouponTemplateUpdateReqVO bean);

    CouponTemplateRespVO convert(CouponTemplateDO bean);

    PageResult<CouponTemplateRespVO> convertPage(PageResult<CouponTemplateDO> page);

    CouponTemplatePageReqVO convert(AppCouponTemplatePageReqVO pageReqVO, List<Integer> canTakeTypes, Integer productScope, Long productScopeValue);

    PageResult<AppCouponTemplateRespVO> convertAppPage(PageResult<CouponTemplateDO> pageResult);

    List<AppCouponTemplateRespVO> convertAppList(List<CouponTemplateDO> list);

    default PageResult<AppCouponTemplateRespVO> convertAppPage(PageResult<CouponTemplateDO> pageResult, Map<Long, Boolean> userCanTakeMap) {
        PageResult<AppCouponTemplateRespVO> result = convertAppPage(pageResult);
        copyTo(result.getList(), userCanTakeMap);
        return result;
    }

    default List<AppCouponTemplateRespVO> convertAppList(List<CouponTemplateDO> list, Map<Long, Boolean> userCanTakeMap) {
        List<AppCouponTemplateRespVO> result = convertAppList(list);
        copyTo(result, userCanTakeMap);
        return result;
    }

    default void copyTo(List<AppCouponTemplateRespVO> list, Map<Long, Boolean> userCanTakeMap) {
        for (AppCouponTemplateRespVO template : list) {
            // 检查已领取数量是否超过限领数量
            template.setCanTake(MapUtil.getBool(userCanTakeMap, template.getId(), false));
        }
    }

    List<CouponTemplateRespVO> convertList(List<CouponTemplateDO> list);

}
