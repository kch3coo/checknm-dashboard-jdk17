package ryd.checknm.dashboard.module.bpm.controller.admin.definition.vo.category;

import ryd.checknm.dashboard.framework.common.enums.CommonStatusEnum;
import ryd.checknm.dashboard.framework.common.validation.InEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Schema(description = "管理后台 - BPM 流程分类新增/修改 Request VO")
@Data
public class BpmCategorySaveReqVO {

    @Schema(description = "分类编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "3167")
    private Long id;

    @Schema(description = "分类名", requiredMode = Schema.RequiredMode.REQUIRED, example = "王五")
    @NotEmpty(message = "分类名不能为空")
    private String name;

    @Schema(description = "分类描述", example = "你猜")
    private String description;

    @Schema(description = "分类标志", requiredMode = Schema.RequiredMode.REQUIRED, example = "OA")
    @NotEmpty(message = "分类标志不能为空")
    private String code;

    @Schema(description = "分类状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "分类状态不能为空")
    @InEnum(CommonStatusEnum.class)
    private Integer status;

    @Schema(description = "分类排序", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "分类排序不能为空")
    private Integer sort;

}