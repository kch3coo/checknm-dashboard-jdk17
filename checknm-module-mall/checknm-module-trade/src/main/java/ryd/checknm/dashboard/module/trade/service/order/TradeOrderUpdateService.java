package ryd.checknm.dashboard.module.trade.service.order;

import ryd.checknm.dashboard.module.trade.controller.admin.order.vo.TradeOrderDeliveryReqVO;
import ryd.checknm.dashboard.module.trade.controller.admin.order.vo.TradeOrderRemarkReqVO;
import ryd.checknm.dashboard.module.trade.controller.admin.order.vo.TradeOrderUpdateAddressReqVO;
import ryd.checknm.dashboard.module.trade.controller.admin.order.vo.TradeOrderUpdatePriceReqVO;
import ryd.checknm.dashboard.module.trade.controller.app.order.vo.AppTradeOrderCreateReqVO;
import ryd.checknm.dashboard.module.trade.controller.app.order.vo.AppTradeOrderSettlementReqVO;
import ryd.checknm.dashboard.module.trade.controller.app.order.vo.AppTradeOrderSettlementRespVO;
import ryd.checknm.dashboard.module.trade.controller.app.order.vo.item.AppTradeOrderItemCommentCreateReqVO;
import ryd.checknm.dashboard.module.trade.dal.dataobject.order.TradeOrderDO;
import jakarta.validation.constraints.NotNull;

import java.util.List;

/**
 * 交易订单【写】Service 接口
 *
 * @author LeeYan9
 * @since 2022-08-26
 */
public interface TradeOrderUpdateService {

    // =================== Order ===================

    /**
     * 获得订单结算信息
     *
     * @param userId          登录用户
     * @param settlementReqVO 订单结算请求
     * @return 订单结算结果
     */
    AppTradeOrderSettlementRespVO settlementOrder(Long userId, AppTradeOrderSettlementReqVO settlementReqVO);

    /**
     * 【会员】创建交易订单
     *
     * @param userId      登录用户
     * @param createReqVO 创建交易订单请求模型
     * @return 交易订单的
     */
    TradeOrderDO createOrder(Long userId, AppTradeOrderCreateReqVO createReqVO);

    /**
     * 更新交易订单已支付
     *
     * @param id         交易订单编号
     * @param payOrderId 支付订单编号
     */
    void updateOrderPaid(Long id, Long payOrderId);

    /**
     * 同步订单的支付状态
     *
     * 1. Quietly 表示，即使同步失败，也不会抛出异常
     * 2. 什么时候回出现异常？因为是主动同步，可能和支付模块的回调通知 {@link #updateOrderPaid(Long, Long)} 存在并发冲突，导致抛出异常
     *
     * @param id 订单编号
     * @param payOrderId 支付订单编号
     */
    void syncOrderPayStatusQuietly(Long id, Long payOrderId);

    /**
     * 【管理员】发货交易订单
     *
     * @param deliveryReqVO 发货请求
     */
    void deliveryOrder(TradeOrderDeliveryReqVO deliveryReqVO);

    /**
     * 【会员】收货交易订单
     *
     * @param userId 用户编号
     * @param id     订单编号
     */
    void receiveOrderByMember(Long userId, Long id);

    /**
     * 【系统】自动收货交易订单
     *
     * @return 收货数量
     */
    int receiveOrderBySystem();

    /**
     * 【会员】取消交易订单
     *
     * @param userId 用户编号
     * @param id     订单编号
     */
    void cancelOrderByMember(Long userId, Long id);

    /**
     * 【系统】自动取消订单
     *
     * @return 取消数量
     */
    int cancelOrderBySystem();

    /**
     * 【会员】删除订单
     *
     * @param userId 用户编号
     * @param id     订单编号
     */
    void deleteOrder(Long userId, Long id);

    /**
     * 【管理员】交易订单备注
     *
     * @param reqVO 请求
     */
    void updateOrderRemark(TradeOrderRemarkReqVO reqVO);

    /**
     * 【管理员】调整价格
     *
     * @param reqVO 请求
     */
    void updateOrderPrice(TradeOrderUpdatePriceReqVO reqVO);

    /**
     * 【管理员】调整地址
     *
     * @param reqVO 请求
     */
    void updateOrderAddress(TradeOrderUpdateAddressReqVO reqVO);

    /**
     * 【管理员】核销订单
     *
     * @param userId 管理员编号
     * @param id 订单编号
     */
    void pickUpOrderByAdmin(Long userId, Long id);

    /**
     * 【管理员】核销订单
     *
     * @param userId 管理员编号
     * @param pickUpVerifyCode 自提核销码
     */
    void pickUpOrderByAdmin(Long userId, String pickUpVerifyCode);

    /**
     * 【管理员】根据自提核销码，查询订单
     *
     * @param pickUpVerifyCode 自提核销码
     */
    TradeOrderDO getByPickUpVerifyCode(String pickUpVerifyCode);

    // =================== Order Item ===================

    /**
     * 当售后申请后，更新交易订单项的售后状态
     *
     * @param id          交易订单项编号
     * @param afterSaleId 售后单编号
     */
    void updateOrderItemWhenAfterSaleCreate(@NotNull Long id, @NotNull Long afterSaleId);

    /**
     * 当售后完成后，更新交易订单项的售后状态
     *
     * @param id          交易订单项编号
     * @param refundPrice 退款金额
     */
    void updateOrderItemWhenAfterSaleSuccess(@NotNull Long id, @NotNull Integer refundPrice);

    /**
     * 当售后取消（用户取消、管理员驳回、管理员拒绝收货）后，更新交易订单项的售后状态
     *
     * @param id 交易订单项编号
     */
    void updateOrderItemWhenAfterSaleCancel(@NotNull Long id);

    /**
     * 【会员】创建订单项的评论
     *
     * @param userId      用户编号
     * @param createReqVO 创建请求
     * @return 得到评价 id
     */
    Long createOrderItemCommentByMember(Long userId, AppTradeOrderItemCommentCreateReqVO createReqVO);

    /**
     * 【系统】创建订单项的评论
     *
     * @return 被评论的订单数
     */
    int createOrderItemCommentBySystem();

    /**
     * 更新拼团相关信息到订单
     *
     * @param orderId             订单编号
     * @param activityId          拼团活动编号
     * @param combinationRecordId 拼团记录编号
     * @param headId              团长编号
     */
    void updateOrderCombinationInfo(Long orderId, Long activityId, Long combinationRecordId, Long headId);

    /**
     * 取消支付订单
     *
     * @param userId           用户编号
     * @param orderId          订单编号
     * @param cancelType       取消类型
     */
    void cancelPaidOrder(Long userId, Long orderId, Integer cancelType);

    /**
     * 取消支付订单的退款回调
     *
     * @param id               订单编号
     * @param payRefundId      支付退款编号
     */
    void updatePaidOrderRefunded(Long id, Long payRefundId);

    /**
     * 更新下单赠送的优惠券编号到订单
     *
     * @param userId        用户编号
     * @param orderId       订单编号
     * @param giveCouponIds 赠送的优惠券编号列表
     */
    void updateOrderGiveCouponIds(Long userId, Long orderId, List<Long> giveCouponIds);

}
