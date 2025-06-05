package ryd.checknm.dashboard.framework.dict.core.util;

import ryd.checknm.dashboard.framework.common.biz.system.dict.DictDataCommonApi;
import ryd.checknm.dashboard.framework.common.biz.system.dict.dto.DictDataRespDTO;
import ryd.checknm.dashboard.framework.dict.core.DictFrameworkUtils;
import ryd.checknm.dashboard.framework.test.core.ut.BaseMockitoUnitTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.List;

import static ryd.checknm.dashboard.framework.test.core.util.RandomUtils.randomPojo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

/**
 * {@link DictFrameworkUtils} 的单元测试
 */
public class DictFrameworkUtilsTest extends BaseMockitoUnitTest {

    @Mock
    private DictDataCommonApi dictDataApi;

    @BeforeEach
    public void setUp() {
        DictFrameworkUtils.init(dictDataApi);
        DictFrameworkUtils.clearCache();
    }

    @Test
    public void testParseDictDataLabel() {
        // mock 数据
        List<DictDataRespDTO> dictDatas = List.of(
                randomPojo(DictDataRespDTO.class, o -> o.setDictType("animal").setValue("cat").setLabel("猫")),
                randomPojo(DictDataRespDTO.class, o -> o.setDictType("animal").setValue("dog").setLabel("狗"))
        );
        // mock 方法
        when(dictDataApi.getDictDataList(eq("animal"))).thenReturn(dictDatas);

        // 断言返回值
        assertEquals("狗", DictFrameworkUtils.parseDictDataLabel("animal", "dog"));
    }

    @Test
    public void testParseDictDataValue() {
        // mock 数据
        List<DictDataRespDTO> dictDatas = List.of(
                randomPojo(DictDataRespDTO.class, o -> o.setDictType("animal").setValue("cat").setLabel("猫")),
                randomPojo(DictDataRespDTO.class, o -> o.setDictType("animal").setValue("dog").setLabel("狗"))
        );
        // mock 方法
        when(dictDataApi.getDictDataList(eq("animal"))).thenReturn(dictDatas);

        // 断言返回值
        assertEquals("dog", DictFrameworkUtils.parseDictDataValue("animal", "狗"));
    }

}
