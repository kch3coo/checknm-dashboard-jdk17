package ryd.checknm.dashboard.module.promotion.controller.admin.bargain.vo.activity;

import ryd.checknm.dashboard.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Schema(description = "管理后台 - 砍价活动分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class BargainActivityPageReqVO extends PageParam {

    @Schema(description = "砍价名称", example = "赵六")
    private String name;

    @Schema(description = "活动状态", example = "0")
    private Integer status;

}
