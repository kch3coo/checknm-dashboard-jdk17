package ryd.checknm.dashboard.module.promotion.controller.admin.banner;

import ryd.checknm.dashboard.framework.common.pojo.CommonResult;
import ryd.checknm.dashboard.framework.common.pojo.PageResult;
import ryd.checknm.dashboard.module.promotion.controller.admin.banner.vo.BannerCreateReqVO;
import ryd.checknm.dashboard.module.promotion.controller.admin.banner.vo.BannerPageReqVO;
import ryd.checknm.dashboard.module.promotion.controller.admin.banner.vo.BannerRespVO;
import ryd.checknm.dashboard.module.promotion.controller.admin.banner.vo.BannerUpdateReqVO;
import ryd.checknm.dashboard.module.promotion.convert.banner.BannerConvert;
import ryd.checknm.dashboard.module.promotion.dal.dataobject.banner.BannerDO;
import ryd.checknm.dashboard.module.promotion.service.banner.BannerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;

import static ryd.checknm.dashboard.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - Banner 管理")
@RestController
@RequestMapping("/promotion/banner")
@Validated
public class BannerController {

    @Resource
    private BannerService bannerService;

    @PostMapping("/create")
    @Operation(summary = "创建 Banner")
    @PreAuthorize("@ss.hasPermission('promotion:banner:create')")
    public CommonResult<Long> createBanner(@Valid @RequestBody BannerCreateReqVO createReqVO) {
        return success(bannerService.createBanner(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新 Banner")
    @PreAuthorize("@ss.hasPermission('promotion:banner:update')")
    public CommonResult<Boolean> updateBanner(@Valid @RequestBody BannerUpdateReqVO updateReqVO) {
        bannerService.updateBanner(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除 Banner")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('promotion:banner:delete')")
    public CommonResult<Boolean> deleteBanner(@RequestParam("id") Long id) {
        bannerService.deleteBanner(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得 Banner")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('promotion:banner:query')")
    public CommonResult<BannerRespVO> getBanner(@RequestParam("id") Long id) {
        BannerDO banner = bannerService.getBanner(id);
        return success(BannerConvert.INSTANCE.convert(banner));
    }

    @GetMapping("/page")
    @Operation(summary = "获得 Banner 分页")
    @PreAuthorize("@ss.hasPermission('promotion:banner:query')")
    public CommonResult<PageResult<BannerRespVO>> getBannerPage(@Valid BannerPageReqVO pageVO) {
        PageResult<BannerDO> pageResult = bannerService.getBannerPage(pageVO);
        return success(BannerConvert.INSTANCE.convertPage(pageResult));
    }

}
