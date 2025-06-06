package ryd.checknm.dashboard.module.bpm.controller.admin.task.vo.instance;

import ryd.checknm.dashboard.framework.common.pojo.PageParam;
import ryd.checknm.dashboard.framework.common.validation.InEnum;
import ryd.checknm.dashboard.module.bpm.enums.task.BpmProcessInstanceStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static ryd.checknm.dashboard.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 流程实例分页 Request VO")
@Data
public class BpmProcessInstancePageReqVO extends PageParam {

    @Schema(description = "流程名称", example = "芋道")
    private String name;

    @Schema(description = "流程定义的标识", example = "2048")
    private String processDefinitionKey; // 精准匹配

    @Schema(description = "流程实例的状态", example = "1")
    @InEnum(BpmProcessInstanceStatusEnum.class)
    private Integer status;

    @Schema(description = "流程分类", example = "1")
    private String category;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "结束时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] endTime;

    @Schema(description = "发起用户编号", example = "1024")
    private Long startUserId; // 注意，只有在【流程实例】菜单，才使用该参数

    @Schema(description = "动态表单字段查询 JSON Str", example = "{}")
    private String formFieldsParams; // SpringMVC 在 get 请求下，无法方便的定义 Map 类型的参数，所以通过 String 接收后，逻辑里面转换

}
