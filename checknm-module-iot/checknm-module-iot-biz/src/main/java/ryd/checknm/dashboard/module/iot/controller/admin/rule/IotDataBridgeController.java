package ryd.checknm.dashboard.module.iot.controller.admin.rule;

import ryd.checknm.dashboard.framework.common.pojo.CommonResult;
import ryd.checknm.dashboard.framework.common.pojo.PageResult;
import ryd.checknm.dashboard.framework.common.util.object.BeanUtils;
import ryd.checknm.dashboard.module.iot.controller.admin.rule.vo.databridge.IotDataBridgePageReqVO;
import ryd.checknm.dashboard.module.iot.controller.admin.rule.vo.databridge.IotDataBridgeRespVO;
import ryd.checknm.dashboard.module.iot.controller.admin.rule.vo.databridge.IotDataBridgeSaveReqVO;
import ryd.checknm.dashboard.module.iot.dal.dataobject.rule.IotDataBridgeDO;
import ryd.checknm.dashboard.module.iot.service.rule.IotDataBridgeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static ryd.checknm.dashboard.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - IoT 数据桥梁")
@RestController
@RequestMapping("/iot/data-bridge")
@Validated
public class IotDataBridgeController {

    @Resource
    private IotDataBridgeService dataBridgeService;

    @PostMapping("/create")
    @Operation(summary = "创建数据桥梁")
    @PreAuthorize("@ss.hasPermission('iot:data-bridge:create')")
    public CommonResult<Long> createDataBridge(@Valid @RequestBody IotDataBridgeSaveReqVO createReqVO) {
        return success(dataBridgeService.createDataBridge(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新数据桥梁")
    @PreAuthorize("@ss.hasPermission('iot:data-bridge:update')")
    public CommonResult<Boolean> updateDataBridge(@Valid @RequestBody IotDataBridgeSaveReqVO updateReqVO) {
        dataBridgeService.updateDataBridge(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除数据桥梁")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('iot:data-bridge:delete')")
    public CommonResult<Boolean> deleteDataBridge(@RequestParam("id") Long id) {
        dataBridgeService.deleteDataBridge(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得数据桥梁")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('iot:data-bridge:query')")
    public CommonResult<IotDataBridgeRespVO> getDataBridge(@RequestParam("id") Long id) {
        IotDataBridgeDO dataBridge = dataBridgeService.getDataBridge(id);
        return success(BeanUtils.toBean(dataBridge, IotDataBridgeRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得数据桥梁分页")
    @PreAuthorize("@ss.hasPermission('iot:data-bridge:query')")
    public CommonResult<PageResult<IotDataBridgeRespVO>> getDataBridgePage(@Valid IotDataBridgePageReqVO pageReqVO) {
        PageResult<IotDataBridgeDO> pageResult = dataBridgeService.getDataBridgePage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, IotDataBridgeRespVO.class));
    }

}