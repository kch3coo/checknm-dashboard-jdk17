package ryd.checknm.dashboard.module.crm.dal.mysql.statistics;

import ryd.checknm.dashboard.module.crm.controller.admin.statistics.vo.funnel.CrmStatisticsBusinessInversionRateSummaryByDateRespVO;
import ryd.checknm.dashboard.module.crm.controller.admin.statistics.vo.funnel.CrmStatisticsBusinessSummaryByDateRespVO;
import ryd.checknm.dashboard.module.crm.controller.admin.statistics.vo.funnel.CrmStatisticsBusinessSummaryByEndStatusRespVO;
import ryd.checknm.dashboard.module.crm.controller.admin.statistics.vo.funnel.CrmStatisticsFunnelReqVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * CRM 销售漏斗 Mapper
 *
 * @author HUIHUI
 */
@Mapper
public interface CrmStatisticsFunnelMapper {

    Long selectCustomerCountByDate(CrmStatisticsFunnelReqVO reqVO);

    Long selectBusinessCountByDateAndEndStatus(@Param("reqVO") CrmStatisticsFunnelReqVO reqVO, @Param("status") Integer status);

    List<CrmStatisticsBusinessSummaryByEndStatusRespVO> selectBusinessSummaryListGroupByEndStatus(CrmStatisticsFunnelReqVO reqVO);

    List<CrmStatisticsBusinessSummaryByDateRespVO> selectBusinessSummaryGroupByDate(CrmStatisticsFunnelReqVO reqVO);

    List<CrmStatisticsBusinessInversionRateSummaryByDateRespVO> selectBusinessInversionRateSummaryByDate(CrmStatisticsFunnelReqVO reqVO);

}
