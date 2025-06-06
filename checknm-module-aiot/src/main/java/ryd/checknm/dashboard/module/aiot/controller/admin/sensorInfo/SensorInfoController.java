package ryd.checknm.dashboard.module.aiot.controller.admin.sensorInfo;

import org.springframework.web.bind.annotation.*;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;

import jakarta.validation.constraints.*;
import jakarta.validation.*;
import jakarta.servlet.http.*;
import java.util.*;
import java.io.IOException;

import ryd.checknm.dashboard.framework.common.pojo.PageParam;
import ryd.checknm.dashboard.framework.common.pojo.PageResult;
import ryd.checknm.dashboard.framework.common.pojo.CommonResult;
import ryd.checknm.dashboard.framework.common.util.object.BeanUtils;
import static ryd.checknm.dashboard.framework.common.pojo.CommonResult.success;

import ryd.checknm.dashboard.framework.datapermission.core.annotation.DataPermission;
import ryd.checknm.dashboard.framework.excel.core.util.ExcelUtils;

import ryd.checknm.dashboard.framework.apilog.core.annotation.ApiAccessLog;
import static ryd.checknm.dashboard.framework.apilog.core.enums.OperateTypeEnum.*;

import ryd.checknm.dashboard.module.aiot.controller.admin.sensorInfo.vo.*;
import ryd.checknm.dashboard.module.aiot.dal.dataobject.sensorInfo.SensorInfoDO;
import ryd.checknm.dashboard.module.aiot.service.sensorInfo.SensorInfoService;

@Tag(name = "管理后台 - 传感器信息")
@RestController
@RequestMapping("/aiot/sensor-info")
@Validated
public class SensorInfoController {

    @Resource
    private SensorInfoService sensorInfoService;

    @PostMapping("/create")
    @Operation(summary = "创建传感器信息")
    @PreAuthorize("@ss.hasPermission('aiot:sensor-info:create')")
    public CommonResult<Long> createSensorInfo(@Valid @RequestBody SensorInfoSaveReqVO createReqVO) {
        return success(sensorInfoService.createSensorInfo(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新传感器信息")
    @PreAuthorize("@ss.hasPermission('aiot:sensor-info:update')")
    public CommonResult<Boolean> updateSensorInfo(@Valid @RequestBody SensorInfoSaveReqVO updateReqVO) {
        sensorInfoService.updateSensorInfo(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除传感器信息")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('aiot:sensor-info:delete')")
    public CommonResult<Boolean> deleteSensorInfo(@RequestParam("id") Long id) {
        sensorInfoService.deleteSensorInfo(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除传感器信息")
    @PreAuthorize("@ss.hasPermission('aiot:sensor-info:delete')")
    public CommonResult<Boolean> deleteSensorInfoList(@RequestParam("ids") List<Long> ids) {
        sensorInfoService.deleteSensorInfoListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得传感器信息")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('aiot:sensor-info:query')")
    public CommonResult<SensorInfoRespVO> getSensorInfo(@RequestParam("id") Long id) {
        SensorInfoDO sensorInfo = sensorInfoService.getSensorInfo(id);
        return success(BeanUtils.toBean(sensorInfo, SensorInfoRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得传感器信息分页")
    @PreAuthorize("@ss.hasPermission('aiot:sensor-info:query')")
    public CommonResult<PageResult<SensorInfoRespVO>> getSensorInfoPage(@Valid SensorInfoPageReqVO pageReqVO) {
        PageResult<SensorInfoDO> pageResult = sensorInfoService.getSensorInfoPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, SensorInfoRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出传感器信息 Excel")
    @PreAuthorize("@ss.hasPermission('aiot:sensor-info:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportSensorInfoExcel(@Valid SensorInfoPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<SensorInfoDO> list = sensorInfoService.getSensorInfoPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "传感器信息.xls", "数据", SensorInfoRespVO.class,
                        BeanUtils.toBean(list, SensorInfoRespVO.class));
    }

}