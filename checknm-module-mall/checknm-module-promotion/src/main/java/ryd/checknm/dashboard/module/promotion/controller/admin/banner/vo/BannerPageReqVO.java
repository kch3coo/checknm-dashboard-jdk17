package ryd.checknm.dashboard.module.promotion.controller.admin.banner.vo;

import ryd.checknm.dashboard.framework.common.enums.CommonStatusEnum;
import ryd.checknm.dashboard.framework.common.pojo.PageParam;
import ryd.checknm.dashboard.framework.common.validation.InEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static ryd.checknm.dashboard.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - Banner 分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class BannerPageReqVO extends PageParam {

    @Schema(description = "标题", example = "这是一个标题")
    private String title;

    @Schema(description = "状态", example = "1")
    @InEnum(CommonStatusEnum.class)
    private Integer status;

    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @Schema(description = "创建时间")
    private LocalDateTime[] createTime;

}
