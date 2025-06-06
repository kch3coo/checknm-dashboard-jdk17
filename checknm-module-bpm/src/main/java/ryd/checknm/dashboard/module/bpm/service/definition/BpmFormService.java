package ryd.checknm.dashboard.module.bpm.service.definition;

import ryd.checknm.dashboard.framework.common.pojo.PageResult;
import ryd.checknm.dashboard.framework.common.util.collection.CollectionUtils;
import ryd.checknm.dashboard.module.bpm.controller.admin.definition.vo.form.BpmFormPageReqVO;
import ryd.checknm.dashboard.module.bpm.controller.admin.definition.vo.form.BpmFormSaveReqVO;
import ryd.checknm.dashboard.module.bpm.dal.dataobject.definition.BpmFormDO;
import jakarta.validation.Valid;

import java.util.Collection;
import java.util.List;
import java.util.Map;


/**
 * 动态表单 Service 接口
 *
 * @author  @风里雾里
 */
public interface BpmFormService {

    /**
     * 创建动态表单
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createForm(@Valid BpmFormSaveReqVO createReqVO);

    /**
     * 更新动态表单
     *
     * @param updateReqVO 更新信息
     */
    void updateForm(@Valid BpmFormSaveReqVO updateReqVO);

    /**
     * 删除动态表单
     *
     * @param id 编号
     */
    void deleteForm(Long id);

    /**
     * 获得动态表单
     *
     * @param id 编号
     * @return 动态表单
     */
    BpmFormDO getForm(Long id);

    /**
     * 获得动态表单列表
     *
     * @return 动态表单列表
     */
    List<BpmFormDO> getFormList();

    /**
     * 获得动态表单列表
     *
     * @param ids 编号
     * @return 动态表单列表
     */
    List<BpmFormDO> getFormList(Collection<Long> ids);

    /**
     * 获得动态表单 Map
     *
     * @param ids 编号
     * @return 动态表单 Map
     */
    default Map<Long, BpmFormDO> getFormMap(Collection<Long> ids) {
        return CollectionUtils.convertMap(this.getFormList(ids), BpmFormDO::getId);
    }

    /**
     * 获得动态表单分页
     *
     * @param pageReqVO 分页查询
     * @return 动态表单分页
     */
    PageResult<BpmFormDO> getFormPage(BpmFormPageReqVO pageReqVO);

}
