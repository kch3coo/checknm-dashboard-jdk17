package ryd.checknm.dashboard.module.product.controller.app.favorite;

import cn.hutool.core.collection.CollUtil;
import ryd.checknm.dashboard.framework.common.pojo.CommonResult;
import ryd.checknm.dashboard.framework.common.pojo.PageResult;
import ryd.checknm.dashboard.module.product.controller.app.favorite.vo.AppFavoritePageReqVO;
import ryd.checknm.dashboard.module.product.controller.app.favorite.vo.AppFavoriteReqVO;
import ryd.checknm.dashboard.module.product.controller.app.favorite.vo.AppFavoriteRespVO;
import ryd.checknm.dashboard.module.product.convert.favorite.ProductFavoriteConvert;
import ryd.checknm.dashboard.module.product.dal.dataobject.favorite.ProductFavoriteDO;
import ryd.checknm.dashboard.module.product.dal.dataobject.spu.ProductSpuDO;
import ryd.checknm.dashboard.module.product.service.favorite.ProductFavoriteService;
import ryd.checknm.dashboard.module.product.service.spu.ProductSpuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static ryd.checknm.dashboard.framework.common.pojo.CommonResult.success;
import static ryd.checknm.dashboard.framework.common.util.collection.CollectionUtils.convertList;
import static ryd.checknm.dashboard.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

@Tag(name = "用户 APP - 商品收藏")
@RestController
@RequestMapping("/product/favorite")
public class AppFavoriteController {

    @Resource
    private ProductFavoriteService productFavoriteService;
    @Resource
    private ProductSpuService productSpuService;

    @PostMapping(value = "/create")
    @Operation(summary = "添加商品收藏")
    public CommonResult<Long> createFavorite(@RequestBody @Valid AppFavoriteReqVO reqVO) {
        return success(productFavoriteService.createFavorite(getLoginUserId(), reqVO.getSpuId()));
    }

    @DeleteMapping(value = "/delete")
    @Operation(summary = "取消单个商品收藏")
    public CommonResult<Boolean> deleteFavorite(@RequestBody @Valid AppFavoriteReqVO reqVO) {
        productFavoriteService.deleteFavorite(getLoginUserId(), reqVO.getSpuId());
        return success(Boolean.TRUE);
    }

    @GetMapping(value = "/page")
    @Operation(summary = "获得商品收藏分页")
    public CommonResult<PageResult<AppFavoriteRespVO>> getFavoritePage(AppFavoritePageReqVO reqVO) {
        PageResult<ProductFavoriteDO> favoritePage = productFavoriteService.getFavoritePage(getLoginUserId(), reqVO);
        if (CollUtil.isEmpty(favoritePage.getList())) {
            return success(PageResult.empty());
        }

        // 得到商品 spu 信息
        List<ProductFavoriteDO> favorites = favoritePage.getList();
        List<Long> spuIds = convertList(favorites, ProductFavoriteDO::getSpuId);
        List<ProductSpuDO> spus = productSpuService.getSpuList(spuIds);

        // 转换 VO 结果
        PageResult<AppFavoriteRespVO> pageResult = new PageResult<>(favoritePage.getTotal());
        pageResult.setList(ProductFavoriteConvert.INSTANCE.convertList(favorites, spus));
        return success(pageResult);
    }

    @GetMapping(value = "/exits")
    @Operation(summary = "检查是否收藏过商品")
    public CommonResult<Boolean> isFavoriteExists(AppFavoriteReqVO reqVO) {
        ProductFavoriteDO favorite = productFavoriteService.getFavorite(getLoginUserId(), reqVO.getSpuId());
        return success(favorite != null);
    }

    @GetMapping(value = "/get-count")
    @Operation(summary = "获得商品收藏数量")
    public CommonResult<Long> getFavoriteCount() {
        return success(productFavoriteService.getFavoriteCount(getLoginUserId()));
    }

}
