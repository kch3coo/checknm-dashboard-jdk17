package ryd.checknm.dashboard.module.bpm.service.task;

import ryd.checknm.dashboard.framework.common.pojo.PageResult;
import ryd.checknm.dashboard.module.bpm.api.task.dto.BpmProcessInstanceCreateReqDTO;
import ryd.checknm.dashboard.module.bpm.controller.admin.task.vo.instance.*;
import jakarta.validation.Valid;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.runtime.ProcessInstance;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static ryd.checknm.dashboard.framework.common.util.collection.CollectionUtils.convertMap;

/**
 * 流程实例 Service 接口
 *
 * @author 芋道源码
 */
public interface BpmProcessInstanceService {

    // ========== Query 查询相关方法 ==========

    /**
     * 获得流程实例
     *
     * @param id 流程实例的编号
     * @return 流程实例
     */
    ProcessInstance getProcessInstance(String id);

    /**
     * 获得流程实例列表
     *
     * @param ids 流程实例的编号集合
     * @return 流程实例列表
     */
    List<ProcessInstance> getProcessInstances(Set<String> ids);

    /**
     * 获得流程实例 Map
     *
     * @param ids 流程实例的编号集合
     * @return 流程实例列表 Map
     */
    default Map<String, ProcessInstance> getProcessInstanceMap(Set<String> ids) {
        return convertMap(getProcessInstances(ids), ProcessInstance::getProcessInstanceId);
    }

    /**
     * 获得历史的流程实例
     *
     * @param id 流程实例的编号
     * @return 历史的流程实例
     */
    HistoricProcessInstance getHistoricProcessInstance(String id);

    /**
     * 获得历史的流程实例列表
     *
     * @param ids 流程实例的编号集合
     * @return 历史的流程实例列表
     */
    List<HistoricProcessInstance> getHistoricProcessInstances(Set<String> ids);

    /**
     * 获得历史的流程实例 Map
     *
     * @param ids 流程实例的编号集合
     * @return 历史的流程实例列表 Map
     */
    default Map<String, HistoricProcessInstance> getHistoricProcessInstanceMap(Set<String> ids) {
        return convertMap(getHistoricProcessInstances(ids), HistoricProcessInstance::getId);
    }

    /**
     * 获得流程实例的分页
     *
     * @param userId    用户编号
     * @param pageReqVO 分页请求
     * @return 流程实例的分页
     */
    PageResult<HistoricProcessInstance> getProcessInstancePage(Long userId,
                                                               @Valid BpmProcessInstancePageReqVO pageReqVO);

    /**
     * 获取审批详情。
     * <p>
     * 可以是准备发起的流程、进行中的流程、已经结束的流程
     *
     * @param loginUserId  登录人的用户编号
     * @param reqVO 请求信息
     * @return 流程实例的进度
     */
    BpmApprovalDetailRespVO getApprovalDetail(Long loginUserId, @Valid BpmApprovalDetailReqVO reqVO);

    /**
     * 获取下一个执行节点信息
     *
     * @param loginUserId 登录人的用户编号
     * @param reqVO 请求信息
     * @return 下一个执行节点信息
     */
    List<BpmApprovalDetailRespVO.ActivityNode> getNextApprovalNodes(Long loginUserId, @Valid BpmApprovalDetailReqVO reqVO);

    /**
     * 获取流程实例的 BPMN 模型视图
     *
     * @param id 流程实例的编号
     * @return BPMN 模型视图
     */
    BpmProcessInstanceBpmnModelViewRespVO getProcessInstanceBpmnModelView(String id);

    // ========== Update 写入相关方法 ==========

    /**
     * 创建流程实例（提供给前端）
     *
     * @param userId      用户编号
     * @param createReqVO 创建信息
     * @return 实例的编号
     */
    String createProcessInstance(Long userId, @Valid BpmProcessInstanceCreateReqVO createReqVO);

    /**
     * 创建流程实例（提供给内部）
     *
     * @param userId       用户编号
     * @param createReqDTO 创建信息
     * @return 实例的编号
     */
    String createProcessInstance(Long userId, @Valid BpmProcessInstanceCreateReqDTO createReqDTO);

    /**
     * 发起人取消流程实例
     *
     * @param userId      用户编号
     * @param cancelReqVO 取消信息
     */
    void cancelProcessInstanceByStartUser(Long userId, @Valid BpmProcessInstanceCancelReqVO cancelReqVO);

    /**
     * 管理员取消流程实例
     *
     * @param userId      用户编号
     * @param cancelReqVO 取消信息
     */
    void cancelProcessInstanceByAdmin(Long userId, BpmProcessInstanceCancelReqVO cancelReqVO);

    /**
     * 更新 ProcessInstance 为不通过
     *
     * @param processInstance 流程实例
     * @param reason          理由。例如说，审批不通过时，需要传递该值
     */
    void updateProcessInstanceReject(ProcessInstance processInstance, String reason);

    /**
     * 更新 ProcessInstance 的变量
     *
     * @param id 流程编号
     * @param variables 流程变量
     */
    void updateProcessInstanceVariables(String id, Map<String, Object> variables);

    /**
     * 删除 ProcessInstance 的变量
     *
     * @param id  流程编号
     * @param variableNames 流程变量名
     */
    void removeProcessInstanceVariables(String id, Collection<String> variableNames);

    // ========== Event 事件相关方法 ==========

    /**
     * 处理 ProcessInstance 完成事件，例如说：审批通过、不通过、取消
     *
     * @param instance 流程任务
     */
    void processProcessInstanceCompleted(ProcessInstance instance);

    /**
     * 处理 ProcessInstance 开始事件，例如说：流程前置通知
     *
     * @param instance 流程任务
     */
    void processProcessInstanceCreated(ProcessInstance instance);
}
