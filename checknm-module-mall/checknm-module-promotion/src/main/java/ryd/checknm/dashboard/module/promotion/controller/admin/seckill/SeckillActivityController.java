package ryd.checknm.dashboard.module.promotion.controller.admin.seckill;

import cn.hutool.core.collection.CollUtil;
import ryd.checknm.dashboard.framework.common.enums.CommonStatusEnum;
import ryd.checknm.dashboard.framework.common.pojo.CommonResult;
import ryd.checknm.dashboard.framework.common.pojo.PageResult;
import ryd.checknm.dashboard.module.product.api.spu.ProductSpuApi;
import ryd.checknm.dashboard.module.product.api.spu.dto.ProductSpuRespDTO;
import ryd.checknm.dashboard.module.promotion.controller.admin.seckill.vo.activity.*;
import ryd.checknm.dashboard.module.promotion.convert.seckill.SeckillActivityConvert;
import ryd.checknm.dashboard.module.promotion.dal.dataobject.seckill.SeckillActivityDO;
import ryd.checknm.dashboard.module.promotion.dal.dataobject.seckill.SeckillProductDO;
import ryd.checknm.dashboard.module.promotion.service.seckill.SeckillActivityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

import static ryd.checknm.dashboard.framework.common.pojo.CommonResult.success;
import static ryd.checknm.dashboard.framework.common.util.collection.CollectionUtils.convertList;
import static ryd.checknm.dashboard.framework.common.util.collection.CollectionUtils.convertSet;

@Tag(name = "管理后台 - 秒杀活动")
@RestController
@RequestMapping("/promotion/seckill-activity")
@Validated
public class SeckillActivityController {

    @Resource
    private SeckillActivityService seckillActivityService;
    @Resource
    private ProductSpuApi productSpuApi;

    @PostMapping("/create")
    @Operation(summary = "创建秒杀活动")
    @PreAuthorize("@ss.hasPermission('promotion:seckill-activity:create')")
    public CommonResult<Long> createSeckillActivity(@Valid @RequestBody SeckillActivityCreateReqVO createReqVO) {
        return success(seckillActivityService.createSeckillActivity(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新秒杀活动")
    @PreAuthorize("@ss.hasPermission('promotion:seckill-activity:update')")
    public CommonResult<Boolean> updateSeckillActivity(@Valid @RequestBody SeckillActivityUpdateReqVO updateReqVO) {
        seckillActivityService.updateSeckillActivity(updateReqVO);
        return success(true);
    }

    @PutMapping("/close")
    @Operation(summary = "关闭秒杀活动")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('promotion:seckill-activity:close')")
    public CommonResult<Boolean> closeSeckillActivity(@RequestParam("id") Long id) {
        seckillActivityService.closeSeckillActivity(id);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除秒杀活动")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('promotion:seckill-activity:delete')")
    public CommonResult<Boolean> deleteSeckillActivity(@RequestParam("id") Long id) {
        seckillActivityService.deleteSeckillActivity(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得秒杀活动")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('promotion:seckill-activity:query')")
    public CommonResult<SeckillActivityDetailRespVO> getSeckillActivity(@RequestParam("id") Long id) {
        SeckillActivityDO activity = seckillActivityService.getSeckillActivity(id);
        List<SeckillProductDO> products = seckillActivityService.getSeckillProductListByActivityId(id);
        return success(SeckillActivityConvert.INSTANCE.convert(activity, products));
    }

    @GetMapping("/page")
    @Operation(summary = "获得秒杀活动分页")
    @PreAuthorize("@ss.hasPermission('promotion:seckill-activity:query')")
    public CommonResult<PageResult<SeckillActivityRespVO>> getSeckillActivityPage(@Valid SeckillActivityPageReqVO pageVO) {
        // 查询活动列表
        PageResult<SeckillActivityDO> pageResult = seckillActivityService.getSeckillActivityPage(pageVO);
        if (CollUtil.isEmpty(pageResult.getList())) {
            return success(PageResult.empty(pageResult.getTotal()));
        }

        // 拼接数据
        List<SeckillProductDO> products = seckillActivityService.getSeckillProductListByActivityIds(
                convertSet(pageResult.getList(), SeckillActivityDO::getId));
        List<ProductSpuRespDTO> spuList = productSpuApi.getSpuList(
                convertSet(pageResult.getList(), SeckillActivityDO::getSpuId));
        return success(SeckillActivityConvert.INSTANCE.convertPage(pageResult, products, spuList));
    }

    @GetMapping("/list-by-ids")
    @Operation(summary = "获得秒杀活动列表，基于活动编号数组")
    @Parameter(name = "ids", description = "活动编号数组", required = true, example = "[1024, 1025]")
    public CommonResult<List<SeckillActivityRespVO>> getSeckillActivityListByIds(@RequestParam("ids") List<Long> ids) {
        // 1. 获得开启的活动列表
        List<SeckillActivityDO> activityList = seckillActivityService.getSeckillActivityListByIds(ids);
        activityList.removeIf(activity -> CommonStatusEnum.isDisable(activity.getStatus()));
        if (CollUtil.isEmpty(activityList)) {
            return success(Collections.emptyList());
        }
        // 2. 拼接返回
        List<SeckillProductDO> productList = seckillActivityService.getSeckillProductListByActivityIds(
                convertList(activityList, SeckillActivityDO::getId));
        List<ProductSpuRespDTO> spuList = productSpuApi.getSpuList(convertList(activityList, SeckillActivityDO::getSpuId));
        return success(SeckillActivityConvert.INSTANCE.convertList(activityList, productList, spuList));
    }

}
