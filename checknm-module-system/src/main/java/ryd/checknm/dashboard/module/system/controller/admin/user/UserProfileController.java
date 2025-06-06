package ryd.checknm.dashboard.module.system.controller.admin.user;

import cn.hutool.core.collection.CollUtil;
import ryd.checknm.dashboard.framework.common.pojo.CommonResult;
import ryd.checknm.dashboard.framework.datapermission.core.annotation.DataPermission;
import ryd.checknm.dashboard.module.system.controller.admin.user.vo.profile.UserProfileRespVO;
import ryd.checknm.dashboard.module.system.controller.admin.user.vo.profile.UserProfileUpdatePasswordReqVO;
import ryd.checknm.dashboard.module.system.controller.admin.user.vo.profile.UserProfileUpdateReqVO;
import ryd.checknm.dashboard.module.system.convert.user.UserConvert;
import ryd.checknm.dashboard.module.system.dal.dataobject.dept.DeptDO;
import ryd.checknm.dashboard.module.system.dal.dataobject.dept.PostDO;
import ryd.checknm.dashboard.module.system.dal.dataobject.permission.RoleDO;
import ryd.checknm.dashboard.module.system.dal.dataobject.user.AdminUserDO;
import ryd.checknm.dashboard.module.system.service.dept.DeptService;
import ryd.checknm.dashboard.module.system.service.dept.PostService;
import ryd.checknm.dashboard.module.system.service.permission.PermissionService;
import ryd.checknm.dashboard.module.system.service.permission.RoleService;
import ryd.checknm.dashboard.module.system.service.user.AdminUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static ryd.checknm.dashboard.framework.common.pojo.CommonResult.success;
import static ryd.checknm.dashboard.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

@Tag(name = "管理后台 - 用户个人中心")
@RestController
@RequestMapping("/system/user/profile")
@Validated
@Slf4j
public class UserProfileController {

    @Resource
    private AdminUserService userService;
    @Resource
    private DeptService deptService;
    @Resource
    private PostService postService;
    @Resource
    private PermissionService permissionService;
    @Resource
    private RoleService roleService;

    @GetMapping("/get")
    @Operation(summary = "获得登录用户信息")
    @DataPermission(enable = false) // 关闭数据权限，避免只查看自己时，查询不到部门。
    public CommonResult<UserProfileRespVO> getUserProfile() {
        // 获得用户基本信息
        AdminUserDO user = userService.getUser(getLoginUserId());
        // 获得用户角色
        List<RoleDO> userRoles = roleService.getRoleListFromCache(permissionService.getUserRoleIdListByUserId(user.getId()));
        // 获得部门信息
        DeptDO dept = user.getDeptId() != null ? deptService.getDept(user.getDeptId()) : null;
        // 获得岗位信息
        List<PostDO> posts = CollUtil.isNotEmpty(user.getPostIds()) ? postService.getPostList(user.getPostIds()) : null;
        return success(UserConvert.INSTANCE.convert(user, userRoles, dept, posts));
    }

    @PutMapping("/update")
    @Operation(summary = "修改用户个人信息")
    public CommonResult<Boolean> updateUserProfile(@Valid @RequestBody UserProfileUpdateReqVO reqVO) {
        userService.updateUserProfile(getLoginUserId(), reqVO);
        return success(true);
    }

    @PutMapping("/update-password")
    @Operation(summary = "修改用户个人密码")
    public CommonResult<Boolean> updateUserProfilePassword(@Valid @RequestBody UserProfileUpdatePasswordReqVO reqVO) {
        userService.updateUserPassword(getLoginUserId(), reqVO);
        return success(true);
    }

}
