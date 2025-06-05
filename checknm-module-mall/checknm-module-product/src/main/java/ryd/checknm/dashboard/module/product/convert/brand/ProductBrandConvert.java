package ryd.checknm.dashboard.module.product.convert.brand;

import ryd.checknm.dashboard.framework.common.pojo.PageResult;
import ryd.checknm.dashboard.module.product.controller.admin.brand.vo.ProductBrandCreateReqVO;
import ryd.checknm.dashboard.module.product.controller.admin.brand.vo.ProductBrandRespVO;
import ryd.checknm.dashboard.module.product.controller.admin.brand.vo.ProductBrandSimpleRespVO;
import ryd.checknm.dashboard.module.product.controller.admin.brand.vo.ProductBrandUpdateReqVO;
import ryd.checknm.dashboard.module.product.dal.dataobject.brand.ProductBrandDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 品牌 Convert
 *
 * @author 芋道源码
 */
@Mapper
public interface ProductBrandConvert {

    ProductBrandConvert INSTANCE = Mappers.getMapper(ProductBrandConvert.class);

    ProductBrandDO convert(ProductBrandCreateReqVO bean);

    ProductBrandDO convert(ProductBrandUpdateReqVO bean);

    ProductBrandRespVO convert(ProductBrandDO bean);

    List<ProductBrandSimpleRespVO> convertList1(List<ProductBrandDO> list);

    List<ProductBrandRespVO> convertList(List<ProductBrandDO> list);

    PageResult<ProductBrandRespVO> convertPage(PageResult<ProductBrandDO> page);

}
