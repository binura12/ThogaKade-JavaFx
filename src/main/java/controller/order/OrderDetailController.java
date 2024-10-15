package controller.order;

import model.OrderDetail;
import util.CrudUtil;

import java.sql.SQLException;
import java.util.List;

public class OrderDetailController {
    public boolean addOrderDetail(List<OrderDetail> orderDetails) {
        for (OrderDetail orderDetail : orderDetails) {
            boolean isAdd =  addOrderDetail(orderDetail);
            if(!isAdd){
                return false;
            }
        }
        return true;
    }

    public boolean addOrderDetail(OrderDetail orderDetails) {
        String sql = "insert into orderdetail values(?,?,?,?)";
        try {
            return CrudUtil.execute(sql,
                    orderDetails.getOrderId(),
                    orderDetails.getItemCode(),
                    orderDetails.getQty(),
                    orderDetails.getDiscount()
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
