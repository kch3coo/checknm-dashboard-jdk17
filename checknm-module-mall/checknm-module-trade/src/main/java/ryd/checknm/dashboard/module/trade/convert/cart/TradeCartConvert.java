package ryd.checknm.dashboard.module.trade.convert.cart;

import ryd.checknm.dashboard.framework.common.util.object.BeanUtils;
import ryd.checknm.dashboard.module.product.api.sku.dto.ProductSkuRespDTO;
import ryd.checknm.dashboard.module.product.api.spu.dto.ProductSpuRespDTO;
import ryd.checknm.dashboard.module.product.enums.spu.ProductSpuStatusEnum;
import ryd.checknm.dashboard.module.trade.controller.app.base.sku.AppProductSkuBaseRespVO;
import ryd.checknm.dashboard.module.trade.controller.app.base.spu.AppProductSpuBaseRespVO;
import ryd.checknm.dashboard.module.trade.controller.app.cart.vo.AppCartListRespVO;
import ryd.checknm.dashboard.module.trade.dal.dataobject.cart.CartDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static ryd.checknm.dashboard.framework.common.util.collection.CollectionUtils.convertMap;

@Mapper
public interface TradeCartConvert {

    TradeCartConvert INSTANCE = Mappers.getMapper(TradeCartConvert.class);

    default AppCartListRespVO convertList(List<CartDO> carts,
                                          List<ProductSpuRespDTO> spus, List<ProductSkuRespDTO> skus) {
        Map<Long, ProductSpuRespDTO> spuMap = convertMap(spus, ProductSpuRespDTO::getId);
        Map<Long, ProductSkuRespDTO> skuMap = convertMap(skus, ProductSkuRespDTO::getId);
        // 遍历，开始转换
        List<AppCartListRespVO.Cart> validList = new ArrayList<>(carts.size());
        List<AppCartListRespVO.Cart> invalidList = new ArrayList<>();
        carts.forEach(cart -> {
            AppCartListRespVO.Cart cartVO = new AppCartListRespVO.Cart();
            cartVO.setId(cart.getId()).setCount(cart.getCount()).setSelected(cart.getSelected());
            ProductSpuRespDTO spu = spuMap.get(cart.getSpuId());
            ProductSkuRespDTO sku = skuMap.get(cart.getSkuId());
            cartVO.setSpu(BeanUtils.toBean(spu, AppProductSpuBaseRespVO.class))
                    .setSku(BeanUtils.toBean(sku, AppProductSkuBaseRespVO.class));
            // 如果 SPU 不存在，或者下架，或者库存不足，说明是无效的
            if (spu == null
                || !ProductSpuStatusEnum.isEnable(spu.getStatus())
                || spu.getStock() <= 0) {
                invalidList.add(cartVO);
            } else {
                validList.add(cartVO);
            }
        });
        return new AppCartListRespVO().setValidList(validList).setInvalidList(invalidList);
    }

}
