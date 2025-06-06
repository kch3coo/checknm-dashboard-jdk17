package ryd.checknm.dashboard.module.crm.controller.admin.statistics.vo.funnel;

import ryd.checknm.dashboard.framework.common.enums.DateIntervalEnum;
import ryd.checknm.dashboard.framework.common.pojo.PageParam;
import ryd.checknm.dashboard.framework.common.validation.InEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

import static ryd.checknm.dashboard.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - CRM 销售漏斗 Request VO")
@Data
public class CrmStatisticsFunnelReqVO extends PageParam {

    @Schema(description = "部门 id", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "部门 id 不能为空")
    private Long deptId;

    /**
     * 负责人用户 id, 当用户为空, 则计算部门下用户
     */
    @Schema(description = "负责人用户 id", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "1")
    private Long userId;

    /**
     * userIds 目前不用前端传递，目前是方便后端通过 deptId 读取编号后，设置回来
     * 后续，可能会支持选择部分用户进行查询
     */
    @Schema(description = "负责人用户 id 集合", hidden = true, example = "2")
    private List<Long> userIds;

    @Schema(description = "时间间隔类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @InEnum(value = DateIntervalEnum.class, message = "时间间隔类型，必须是 {value}")
    private Integer interval;

    @Schema(description = "时间范围", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @Size(min = 2, max = 2, message = "请选择时间范围")
    private LocalDateTime[] times;

}
