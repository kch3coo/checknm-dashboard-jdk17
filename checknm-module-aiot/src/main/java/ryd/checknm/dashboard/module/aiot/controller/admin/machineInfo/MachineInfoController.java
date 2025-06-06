package ryd.checknm.dashboard.module.aiot.controller.admin.machineInfo;

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

import ryd.checknm.dashboard.framework.excel.core.util.ExcelUtils;

import ryd.checknm.dashboard.framework.apilog.core.annotation.ApiAccessLog;
import static ryd.checknm.dashboard.framework.apilog.core.enums.OperateTypeEnum.*;

import ryd.checknm.dashboard.module.aiot.controller.admin.machineInfo.vo.*;
import ryd.checknm.dashboard.module.aiot.dal.dataobject.machineInfo.MachineInfoDO;
import ryd.checknm.dashboard.module.aiot.dal.dataobject.machineLocationInfo.MachineLocationInfoDO;
import ryd.checknm.dashboard.module.aiot.service.machineInfo.MachineInfoService;

@Tag(name = "管理后台 - 设备信息")
@RestController
@RequestMapping("/aiot/machine-info")
@Validated
public class MachineInfoController {

    @Resource
    private MachineInfoService machineInfoService;

    @PostMapping("/create")
    @Operation(summary = "创建设备信息")
    @PreAuthorize("@ss.hasPermission('aiot:machine-info:create')")
    public CommonResult<Long> createMachineInfo(@Valid @RequestBody MachineInfoSaveReqVO createReqVO) {
        return success(machineInfoService.createMachineInfo(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新设备信息")
    @PreAuthorize("@ss.hasPermission('aiot:machine-info:update')")
    public CommonResult<Boolean> updateMachineInfo(@Valid @RequestBody MachineInfoSaveReqVO updateReqVO) {
        machineInfoService.updateMachineInfo(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除设备信息")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('aiot:machine-info:delete')")
    public CommonResult<Boolean> deleteMachineInfo(@RequestParam("id") Long id) {
        machineInfoService.deleteMachineInfo(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除设备信息")
    @PreAuthorize("@ss.hasPermission('aiot:machine-info:delete')")
    public CommonResult<Boolean> deleteMachineInfoList(@RequestParam("ids") List<Long> ids) {
        machineInfoService.deleteMachineInfoListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得设备信息")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('aiot:machine-info:query')")
    public CommonResult<MachineInfoRespVO> getMachineInfo(@RequestParam("id") Long id) {
        MachineInfoDO machineInfo = machineInfoService.getMachineInfo(id);
        return success(BeanUtils.toBean(machineInfo, MachineInfoRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得设备信息分页")
    @PreAuthorize("@ss.hasPermission('aiot:machine-info:query')")
    public CommonResult<PageResult<MachineInfoRespVO>> getMachineInfoPage(@Valid MachineInfoPageReqVO pageReqVO) {
        PageResult<MachineInfoDO> pageResult = machineInfoService.getMachineInfoPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, MachineInfoRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出设备信息 Excel")
    @PreAuthorize("@ss.hasPermission('aiot:machine-info:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportMachineInfoExcel(@Valid MachineInfoPageReqVO pageReqVO,
                                       HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<MachineInfoDO> list = machineInfoService.getMachineInfoPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "设备信息.xls", "数据", MachineInfoRespVO.class,
                BeanUtils.toBean(list, MachineInfoRespVO.class));
    }

    // ==================== 子表（设备位置信息） ====================

    @GetMapping("/machine-location-info/list-by-machine-id")
    @Operation(summary = "获得设备位置信息列表")
    @Parameter(name = "machineId", description = "设备id")
    @PreAuthorize("@ss.hasPermission('aiot:machine-info:query')")
    public CommonResult<List<MachineLocationInfoDO>> getMachineLocationInfoListByMachineId(@RequestParam("machineId") Long machineId) {
        return success(machineInfoService.getMachineLocationInfoListByMachineId(machineId));
    }

}