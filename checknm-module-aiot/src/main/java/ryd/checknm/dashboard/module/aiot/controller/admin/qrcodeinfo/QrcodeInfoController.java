package ryd.checknm.dashboard.module.aiot.controller.admin.qrcodeinfo;

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

import ryd.checknm.dashboard.module.aiot.controller.admin.qrcodeinfo.vo.*;
import ryd.checknm.dashboard.module.aiot.dal.dataobject.qrcodeinfo.QrcodeInfoDO;
import ryd.checknm.dashboard.module.aiot.service.qrcodeinfo.QrcodeInfoService;

@Tag(name = "管理后台 - 二维码信息")
@RestController
@RequestMapping("/aiot/qrcode-info")
@Validated
public class QrcodeInfoController {

    @Resource
    private QrcodeInfoService qrcodeInfoService;

    @PostMapping("/create")
    @Operation(summary = "创建二维码信息")
    @PreAuthorize("@ss.hasPermission('aiot:qrcode-info:create')")
    public CommonResult<Integer> createQrcodeInfo(@Valid @RequestBody QrcodeInfoSaveReqVO createReqVO) {
        return success(qrcodeInfoService.createQrcodeInfo(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新二维码信息")
    @PreAuthorize("@ss.hasPermission('aiot:qrcode-info:update')")
    public CommonResult<Boolean> updateQrcodeInfo(@Valid @RequestBody QrcodeInfoSaveReqVO updateReqVO) {
        qrcodeInfoService.updateQrcodeInfo(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除二维码信息")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('aiot:qrcode-info:delete')")
    public CommonResult<Boolean> deleteQrcodeInfo(@RequestParam("id") Integer id) {
        qrcodeInfoService.deleteQrcodeInfo(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除二维码信息")
    @PreAuthorize("@ss.hasPermission('aiot:qrcode-info:delete')")
    public CommonResult<Boolean> deleteQrcodeInfoList(@RequestParam("ids") List<Integer> ids) {
        qrcodeInfoService.deleteQrcodeInfoListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得二维码信息")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('aiot:qrcode-info:query')")
    public CommonResult<QrcodeInfoRespVO> getQrcodeInfo(@RequestParam("id") Integer id) {
        QrcodeInfoDO qrcodeInfo = qrcodeInfoService.getQrcodeInfo(id);
        return success(BeanUtils.toBean(qrcodeInfo, QrcodeInfoRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得二维码信息分页")
    @PreAuthorize("@ss.hasPermission('aiot:qrcode-info:query')")
    public CommonResult<PageResult<QrcodeInfoRespVO>> getQrcodeInfoPage(@Valid QrcodeInfoPageReqVO pageReqVO) {
        PageResult<QrcodeInfoDO> pageResult = qrcodeInfoService.getQrcodeInfoPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, QrcodeInfoRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出二维码信息 Excel")
    @PreAuthorize("@ss.hasPermission('aiot:qrcode-info:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportQrcodeInfoExcel(@Valid QrcodeInfoPageReqVO pageReqVO,
                                      HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<QrcodeInfoDO> list = qrcodeInfoService.getQrcodeInfoPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "二维码信息.xls", "数据", QrcodeInfoRespVO.class,
                BeanUtils.toBean(list, QrcodeInfoRespVO.class));
    }

}