package controller.order;

import model.OrderDetail;
import util.CrudUtil;

import java.sql.SQLException;
import java.util.List;

public class OrderDetailController {
    public boolean addOrderDetail(List<OrderDetail> orderDetails) {
        boolean isAdd = false;
        for (OrderDetail orderDetail : orderDetails) {
            isAdd = addOrderDetail(orderDetail);
        }
        return isAdd;
    }

    public boolean addOrderDetail(OrderDetail orderDetails) {
        String sql = "insert into orderdetail values(?,?,?,?)";
        try {
            Object execute = CrudUtil.execute(sql,
                    orderDetails.getOrderId(),
                    orderDetails.getItemCode(),
                    orderDetails.getQty(),
                    orderDetails.getDiscount()
            );
            if (execute != null) {
                return true;
            }
            return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
