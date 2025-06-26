import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class OrderManager {
    public void placeOrder(int customerId, BigDecimal totalAmount, int productId, int quantity) {
        Connection conn = null;
        CallableStatement cstmt = null;
        try {
            conn = Database.getConnection();
            if (conn != null) {
                conn.setAutoCommit(false); //Bắt đầu giao dịch

                cstmt = conn.prepareCall("{call place_order(?,?,?,?,?)}");
                cstmt.setInt(1, customerId);
                cstmt.setBigDecimal(2, totalAmount);
                cstmt.setInt(3, productId);
                cstmt.setInt(4, quantity);
                cstmt.registerOutParameter(5, java.sql.Types.VARCHAR);

                cstmt.executeUpdate();
                String result = cstmt.getString(5); //lấy kết quả
                System.out.println(result); //in thông báo
                conn.commit(); // Thành công
            }
        } catch (Exception e) {
            if (conn != null) {
                try{
                    conn.rollback();  //rollback Nếu có lỗi
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
           e.printStackTrace();
        }finally{
            if (cstmt != null) {
                try {
                    cstmt.close(); //Đóng call...
                } catch (SQLException e) {
                   e.printStackTrace();
                }
            }
            if (conn != null) {
                try{
                    conn.close(); //đóng kết nối
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
