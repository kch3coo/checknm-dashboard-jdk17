package ryd.checknm.dashboard.module.report.convert.goview;

import ryd.checknm.dashboard.framework.common.pojo.PageResult;
import ryd.checknm.dashboard.module.report.controller.admin.goview.vo.project.GoViewProjectCreateReqVO;
import ryd.checknm.dashboard.module.report.controller.admin.goview.vo.project.GoViewProjectRespVO;
import ryd.checknm.dashboard.module.report.controller.admin.goview.vo.project.GoViewProjectUpdateReqVO;
import ryd.checknm.dashboard.module.report.dal.dataobject.goview.GoViewProjectDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface GoViewProjectConvert {

    GoViewProjectConvert INSTANCE = Mappers.getMapper(GoViewProjectConvert.class);

    GoViewProjectDO convert(GoViewProjectCreateReqVO bean);

    GoViewProjectDO convert(GoViewProjectUpdateReqVO bean);

    GoViewProjectRespVO convert(GoViewProjectDO bean);

    PageResult<GoViewProjectRespVO> convertPage(PageResult<GoViewProjectDO> page);

}
