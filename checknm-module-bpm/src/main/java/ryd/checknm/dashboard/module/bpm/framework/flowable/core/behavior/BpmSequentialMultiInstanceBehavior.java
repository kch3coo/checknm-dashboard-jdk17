package ryd.checknm.dashboard.module.bpm.framework.flowable.core.behavior;

import cn.hutool.core.collection.CollUtil;
import ryd.checknm.dashboard.framework.common.util.collection.SetUtils;
import ryd.checknm.dashboard.module.bpm.enums.definition.BpmChildProcessMultiInstanceSourceTypeEnum;
import ryd.checknm.dashboard.module.bpm.framework.flowable.core.candidate.BpmTaskCandidateInvoker;
import ryd.checknm.dashboard.module.bpm.framework.flowable.core.util.BpmnModelUtils;
import ryd.checknm.dashboard.module.bpm.framework.flowable.core.util.FlowableUtils;
import lombok.Setter;
import org.flowable.bpmn.model.Activity;
import org.flowable.bpmn.model.CallActivity;
import org.flowable.bpmn.model.FlowElement;
import org.flowable.bpmn.model.UserTask;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.impl.bpmn.behavior.AbstractBpmnActivityBehavior;
import org.flowable.engine.impl.bpmn.behavior.SequentialMultiInstanceBehavior;
import org.flowable.engine.impl.persistence.entity.ExecutionEntity;

import java.util.List;
import java.util.Set;

/**
 * 自定义的【串行】的【多个】流程任务的 assignee 负责人的分配
 *
 * 本质上，实现和 {@link BpmParallelMultiInstanceBehavior} 一样，只是继承的类不一样
 *
 * @author 芋道源码
 */
@Setter
public class BpmSequentialMultiInstanceBehavior extends SequentialMultiInstanceBehavior {

    private BpmTaskCandidateInvoker taskCandidateInvoker;

    public BpmSequentialMultiInstanceBehavior(Activity activity, AbstractBpmnActivityBehavior innerActivityBehavior) {
        super(activity, innerActivityBehavior);
    }

    /**
     * 逻辑和 {@link BpmParallelMultiInstanceBehavior#resolveNrOfInstances(DelegateExecution)} 类似
     *
     * 差异的点：是在【第二步】的时候，需要返回 LinkedHashSet 集合！因为它需要有序！
     */
    @Override
    protected int resolveNrOfInstances(DelegateExecution execution) {
        // 情况一：UserTask 节点
        if (execution.getCurrentFlowElement() instanceof UserTask) {
            // 第一步，设置 collectionVariable 和 CollectionVariable
            // 从  execution.getVariable() 读取所有任务处理人的 key
            super.collectionExpression = null; // collectionExpression 和 collectionVariable 是互斥的
            super.collectionVariable = FlowableUtils.formatExecutionCollectionVariable(execution.getCurrentActivityId());
            // 从 execution.getVariable() 读取当前所有任务处理的人的 key
            super.collectionElementVariable = FlowableUtils.formatExecutionCollectionElementVariable(execution.getCurrentActivityId());

            // 第二步，获取任务的所有处理人
            // 不使用 execution.getVariable 原因：目前依次审批任务回退后 collectionVariable 变量没有清理， 如果重新进入该任务不会重新分配审批人
            @SuppressWarnings("unchecked")
            Set<Long> assigneeUserIds = (Set<Long>) execution.getVariableLocal(super.collectionVariable, Set.class);
            if (assigneeUserIds == null) {
                assigneeUserIds = taskCandidateInvoker.calculateUsersByTask(execution);
                if (CollUtil.isEmpty(assigneeUserIds)) {
                    // 特殊：如果没有处理人的情况下，至少有一个 null 空元素，避免自动通过！
                    // 这样，保证在 BpmUserTaskActivityBehavior 至少创建出一个 Task 任务
                    // 用途：1）审批人为空时；2）审批类型为自动通过、自动拒绝时
                    assigneeUserIds = SetUtils.asSet((Long) null);
                }
                execution.setVariableLocal(super.collectionVariable, assigneeUserIds);
            }
            return assigneeUserIds.size();
        }

        // 情况二：CallActivity 节点
        if (execution.getCurrentFlowElement() instanceof CallActivity) {
            FlowElement flowElement = execution.getCurrentFlowElement();
            Integer sourceType = BpmnModelUtils.parseMultiInstanceSourceType(flowElement);
            if (sourceType.equals(BpmChildProcessMultiInstanceSourceTypeEnum.NUMBER_FORM.getType())) {
                return execution.getVariable(super.collectionExpression.getExpressionText(), Integer.class);
            }
            if (sourceType.equals(BpmChildProcessMultiInstanceSourceTypeEnum.MULTIPLE_FORM.getType())) {
                return execution.getVariable(super.collectionExpression.getExpressionText(), List.class).size();
            }
        }

        return super.resolveNrOfInstances(execution);
    }

    @Override
    protected void executeOriginalBehavior(DelegateExecution execution, ExecutionEntity multiInstanceRootExecution, int loopCounter) {
        // 参见 https://gitee.com/zhijiantianya/checknm-cloud/issues/IC239F
        super.collectionExpression = null;
        super.collectionVariable = FlowableUtils.formatExecutionCollectionVariable(execution.getCurrentActivityId());
        super.collectionElementVariable = FlowableUtils.formatExecutionCollectionElementVariable(execution.getCurrentActivityId());
        super.executeOriginalBehavior(execution, multiInstanceRootExecution, loopCounter);
    }

}
