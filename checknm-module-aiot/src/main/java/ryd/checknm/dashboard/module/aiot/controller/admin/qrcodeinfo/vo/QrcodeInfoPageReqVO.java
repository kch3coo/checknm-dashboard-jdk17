package ryd.checknm.dashboard.module.aiot.controller.admin.qrcodeinfo.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import ryd.checknm.dashboard.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static ryd.checknm.dashboard.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 二维码信息分页 Request VO")
@Data
public class QrcodeInfoPageReqVO extends PageParam {

    @Schema(description = "二维码信息")
    private String info;

    @Schema(description = "二维码创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "二维码类型")
    private String type;

    @Schema(description = "二维码状态")
    private Integer status;

}