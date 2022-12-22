package cn.lihaotian.bookmanager.dao;

import java.sql.ResultSet;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import cn.lihaotian.bookmanager.model.BorrowDetail;

public class BorrowDetailDao {
	
	public ResultSet list(Connection con,BorrowDetail borrowDetail)throws Exception{
		StringBuffer sb=new StringBuffer("SELECT bd.*,u.username,b.book_name from borrowdetail bd,user u,book b where u.id=bd.user_id and b.id=bd.book_id");
		if(borrowDetail.getUserId() != null){
			sb.append(" and u.id = ?");
		}
		if(borrowDetail.getStatus() != null){
			sb.append(" and bd.status = ?");
		}
		if(borrowDetail.getBookId() != null){
			sb.append(" and bd.book_id = ?");
		}
		sb.append("  order by bd.id");
		PreparedStatement pstmt=(PreparedStatement) con.prepareStatement(sb.toString());
		if(borrowDetail.getUserId() != null){
			pstmt.setInt(1, borrowDetail.getUserId());
		}
		if(borrowDetail.getStatus() != null && borrowDetail.getBookId() != null){
			pstmt.setInt(2, borrowDetail.getStatus());
			pstmt.setInt(3, borrowDetail.getBookId());
		}
		return pstmt.executeQuery();
	}

	public int add(Connection con, BorrowDetail borrowDetail) throws Exception {
		String sql = "insert into borrowdetail (user_id,book_id,status,borrow_time) values (?,?,?,?)";
		PreparedStatement pstmt=(PreparedStatement) con.prepareStatement(sql);
		pstmt.setInt(1, borrowDetail.getUserId());
		pstmt.setInt(2, borrowDetail.getBookId());
		pstmt.setInt(3, borrowDetail.getStatus());
		pstmt.setLong(4, borrowDetail.getBorrowTime());
		return pstmt.executeUpdate();
	}

	public int returnBook(Connection con,BorrowDetail detail)throws Exception {
		String sql = "update borrowdetail set status = ? ,return_time = ? where id = ?";
		PreparedStatement pstmt=(PreparedStatement) con.prepareStatement(sql);
		pstmt.setInt(1, detail.getStatus());
		pstmt.setLong(2, detail.getReturnTime());
		pstmt.setInt(3, detail.getBorrowId());
		return pstmt.executeUpdate();
	}
}
