package ryd.checknm.dashboard.module.iot.controller.admin.plugin.vo.config;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "管理后台 - IoT 插件配置 Response VO")
@Data
public class PluginConfigRespVO {

    @Schema(description = "主键 ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "11546")
    private Long id;

    @Schema(description = "插件包标识符", requiredMode = Schema.RequiredMode.REQUIRED, example = "24627")
    private String pluginKey;

    @Schema(description = "插件名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "赵六")
    private String name;

    @Schema(description = "描述", example = "你猜")
    private String description;

    @Schema(description = "部署方式", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    private Integer deployType;

    @Schema(description = "插件包文件名", requiredMode = Schema.RequiredMode.REQUIRED)
    private String fileName;

    @Schema(description = "插件版本", requiredMode = Schema.RequiredMode.REQUIRED)
    private String version;

    @Schema(description = "插件类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    private Integer type;

    @Schema(description = "设备插件协议类型")
    private String protocol;

    @Schema(description = "状态", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer status;

    @Schema(description = "插件配置项描述信息")
    private String configSchema;

    @Schema(description = "插件配置信息")
    private String config;

    @Schema(description = "插件脚本")
    private String script;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

}