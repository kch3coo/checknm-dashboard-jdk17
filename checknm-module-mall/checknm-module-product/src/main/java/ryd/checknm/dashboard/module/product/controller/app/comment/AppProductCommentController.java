package ryd.checknm.dashboard.module.product.controller.app.comment;

import cn.hutool.core.collection.CollUtil;
import ryd.checknm.dashboard.framework.common.pojo.CommonResult;
import ryd.checknm.dashboard.framework.common.pojo.PageResult;
import ryd.checknm.dashboard.framework.common.util.object.BeanUtils;
import ryd.checknm.dashboard.module.product.controller.app.comment.vo.AppCommentPageReqVO;
import ryd.checknm.dashboard.module.product.controller.app.comment.vo.AppProductCommentRespVO;
import ryd.checknm.dashboard.module.product.dal.dataobject.comment.ProductCommentDO;
import ryd.checknm.dashboard.module.product.service.comment.ProductCommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static ryd.checknm.dashboard.framework.common.pojo.CommonResult.success;

@Tag(name = "用户 APP - 商品评价")
@RestController
@RequestMapping("/product/comment")
@Validated
public class AppProductCommentController {

    @Resource
    private ProductCommentService productCommentService;

    @GetMapping("/page")
    @Operation(summary = "获得商品评价分页")
    @PermitAll
    public CommonResult<PageResult<AppProductCommentRespVO>> getCommentPage(@Valid AppCommentPageReqVO pageVO) {
        // 查询评论分页
        PageResult<ProductCommentDO> pageResult = productCommentService.getCommentPage(pageVO, Boolean.TRUE);
        if (CollUtil.isEmpty(pageResult.getList())) {
            return success(PageResult.empty(pageResult.getTotal()));
        }

        // 拼接返回
        pageResult.getList().forEach(item -> {
            if (Boolean.TRUE.equals(item.getAnonymous())) {
                item.setUserNickname(ProductCommentDO.NICKNAME_ANONYMOUS);
            }
        });
        return success(BeanUtils.toBean(pageResult, AppProductCommentRespVO.class));
    }

}
