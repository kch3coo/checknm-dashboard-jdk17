package ryd.checknm.dashboard.module.erp.service.finance;

import ryd.checknm.dashboard.framework.common.enums.CommonStatusEnum;
import ryd.checknm.dashboard.framework.common.pojo.PageResult;
import ryd.checknm.dashboard.framework.common.util.object.BeanUtils;
import ryd.checknm.dashboard.module.erp.controller.admin.finance.vo.account.ErpAccountPageReqVO;
import ryd.checknm.dashboard.module.erp.controller.admin.finance.vo.account.ErpAccountSaveReqVO;
import ryd.checknm.dashboard.module.erp.dal.dataobject.finance.ErpAccountDO;
import ryd.checknm.dashboard.module.erp.dal.mysql.finance.ErpAccountMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Collection;
import java.util.List;

import static ryd.checknm.dashboard.framework.common.exception.util.ServiceExceptionUtil.exception;
import static ryd.checknm.dashboard.module.erp.enums.ErrorCodeConstants.*;

/**
 * ERP 结算账户 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class ErpAccountServiceImpl implements ErpAccountService {

    @Resource
    private ErpAccountMapper accountMapper;

    @Override
    public Long createAccount(ErpAccountSaveReqVO createReqVO) {
        // 插入
        ErpAccountDO account = BeanUtils.toBean(createReqVO, ErpAccountDO.class);
        accountMapper.insert(account);
        // 返回
        return account.getId();
    }

    @Override
    public void updateAccount(ErpAccountSaveReqVO updateReqVO) {
        // 校验存在
        validateAccountExists(updateReqVO.getId());
        // 更新
        ErpAccountDO updateObj = BeanUtils.toBean(updateReqVO, ErpAccountDO.class);
        accountMapper.updateById(updateObj);
    }

    @Override
    public void updateAccountDefaultStatus(Long id, Boolean defaultStatus) {
        // 1. 校验存在
        validateAccountExists(id);

        // 2.1 如果开启，则需要关闭所有其它的默认
        if (defaultStatus) {
            ErpAccountDO account = accountMapper.selectByDefaultStatus();
            if (account != null) {
                accountMapper.updateById(new ErpAccountDO().setId(account.getId()).setDefaultStatus(false));
            }
        }
        // 2.2 更新对应的默认状态
        accountMapper.updateById(new ErpAccountDO().setId(id).setDefaultStatus(defaultStatus));
    }

    @Override
    public void deleteAccount(Long id) {
        // 校验存在
        validateAccountExists(id);
        // 删除
        accountMapper.deleteById(id);
    }

    private void validateAccountExists(Long id) {
        if (accountMapper.selectById(id) == null) {
            throw exception(ACCOUNT_NOT_EXISTS);
        }
    }

    @Override
    public ErpAccountDO getAccount(Long id) {
        return accountMapper.selectById(id);
    }

    @Override
    public ErpAccountDO validateAccount(Long id) {
        ErpAccountDO account = accountMapper.selectById(id);
        if (account == null) {
            throw exception(ACCOUNT_NOT_EXISTS);
        }
        if (CommonStatusEnum.isDisable(account.getStatus())) {
            throw exception(ACCOUNT_NOT_ENABLE, account.getName());
        }
        return account;
    }

    @Override
    public List<ErpAccountDO> getAccountListByStatus(Integer status) {
        return accountMapper.selectListByStatus(status);
    }

    @Override
    public List<ErpAccountDO> getAccountList(Collection<Long> ids) {
        return accountMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<ErpAccountDO> getAccountPage(ErpAccountPageReqVO pageReqVO) {
        return accountMapper.selectPage(pageReqVO);
    }

}