package ryd.checknm.dashboard.module.product.controller.admin.comment;

import ryd.checknm.dashboard.framework.common.pojo.CommonResult;
import ryd.checknm.dashboard.framework.common.pojo.PageResult;
import ryd.checknm.dashboard.framework.common.util.object.BeanUtils;
import ryd.checknm.dashboard.module.product.controller.admin.comment.vo.*;
import ryd.checknm.dashboard.module.product.dal.dataobject.comment.ProductCommentDO;
import ryd.checknm.dashboard.module.product.service.comment.ProductCommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static ryd.checknm.dashboard.framework.common.pojo.CommonResult.success;
import static ryd.checknm.dashboard.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

@Tag(name = "管理后台 - 商品评价")
@RestController
@RequestMapping("/product/comment")
@Validated
public class ProductCommentController {

    @Resource
    private ProductCommentService productCommentService;

    @GetMapping("/page")
    @Operation(summary = "获得商品评价分页")
    @PreAuthorize("@ss.hasPermission('product:comment:query')")
    public CommonResult<PageResult<ProductCommentRespVO>> getCommentPage(@Valid ProductCommentPageReqVO pageVO) {
        PageResult<ProductCommentDO> pageResult = productCommentService.getCommentPage(pageVO);
        return success(BeanUtils.toBean(pageResult, ProductCommentRespVO.class));
    }

    @PutMapping("/update-visible")
    @Operation(summary = "显示 / 隐藏评论")
    @PreAuthorize("@ss.hasPermission('product:comment:update')")
    public CommonResult<Boolean> updateCommentVisible(@Valid @RequestBody ProductCommentUpdateVisibleReqVO updateReqVO) {
        productCommentService.updateCommentVisible(updateReqVO);
        return success(true);
    }

    @PutMapping("/reply")
    @Operation(summary = "商家回复")
    @PreAuthorize("@ss.hasPermission('product:comment:update')")
    public CommonResult<Boolean> commentReply(@Valid @RequestBody ProductCommentReplyReqVO replyVO) {
        productCommentService.replyComment(replyVO, getLoginUserId());
        return success(true);
    }

    @PostMapping("/create")
    @Operation(summary = "添加自评")
    @PreAuthorize("@ss.hasPermission('product:comment:update')")
    public CommonResult<Boolean> createComment(@Valid @RequestBody ProductCommentCreateReqVO createReqVO) {
        productCommentService.createComment(createReqVO);
        return success(true);
    }

}
