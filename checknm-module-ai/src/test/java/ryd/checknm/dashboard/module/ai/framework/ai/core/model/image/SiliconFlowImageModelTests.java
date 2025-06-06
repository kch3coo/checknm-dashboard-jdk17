package ryd.checknm.dashboard.module.ai.framework.ai.core.model.image;

import ryd.checknm.dashboard.module.ai.framework.ai.core.model.siliconflow.SiliconFlowImageApi;
import ryd.checknm.dashboard.module.ai.framework.ai.core.model.siliconflow.SiliconFlowImageModel;
import ryd.checknm.dashboard.module.ai.framework.ai.core.model.siliconflow.SiliconFlowImageOptions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;

/**
 * {@link SiliconFlowImageModel} 集成测试
 */
public class SiliconFlowImageModelTests {

    private final SiliconFlowImageModel imageModel = new SiliconFlowImageModel(
            new SiliconFlowImageApi("sk-epsakfenqnyzoxhmbucsxlhkdqlcbnimslqoivkshalvdozz") // 密钥
    );

    @Test
    @Disabled
    public void testCall() {
        // 准备参数
        SiliconFlowImageOptions imageOptions = SiliconFlowImageOptions.builder()
                .model("Kwai-Kolors/Kolors")
                .build();
        ImagePrompt prompt = new ImagePrompt("万里长城", imageOptions);

        // 方法调用
        ImageResponse response = imageModel.call(prompt);
        // 打印结果
        System.out.println(response);
    }

}
