package cn.lihaotian.bookmanager.dao;


import java.sql.ResultSet;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import cn.lihaotian.bookmanager.model.Book;
import cn.lihaotian.bookmanager.utils.toolUtil;

public class BookDao {
	
	// 图书添加
	public int add(Connection con,Book book)throws Exception{
		String sql="insert into book (book_name,type_id,author,publish,price,number,status,remark) values(?,?,?,?,?,?,?,?)";
		PreparedStatement pstmt=(PreparedStatement) con.prepareStatement(sql);
		pstmt.setString(1, book.getBookName());
		pstmt.setInt(2, book.getBookTypeId());
		pstmt.setString(3, book.getAuthor());
		pstmt.setString(4, book.getPublish());
		pstmt.setDouble(5, book.getPrice());
		pstmt.setInt(6, book.getNumber());
		pstmt.setInt(7, book.getStatus());
		pstmt.setString(8, book.getRemark());
		return pstmt.executeUpdate();
	}
	
	// 图书信息查询
	public ResultSet list(Connection con,Book book)throws Exception{
		StringBuilder sb=new StringBuilder("select b.*,bt.type_name from book b,book_type bt where b.type_id=bt.id");
		if(!toolUtil.isEmpty(book.getBookName())){
			sb.append(" and b.book_name like '%").append(book.getBookName()).append("%'");
		}
		if(!toolUtil.isEmpty(book.getAuthor())){
			sb.append(" and b.author like '%").append(book.getAuthor()).append("%'");
		}
		if(book.getBookTypeId()!=null && book.getBookTypeId()!=0){
			sb.append(" and b.type_id=").append(book.getBookTypeId());
		}
		if(book.getStatus()!=null){
			sb.append(" and b.status=").append(book.getStatus());
		}
		if(book.getBookId() != null){
			sb.append(" and b.id=").append(book.getBookId());
		}
		sb.append(" ORDER BY b.status");
		PreparedStatement pstmt=(PreparedStatement) con.prepareStatement(sb.toString());
		return pstmt.executeQuery();
		
		
	}
	
	// 图书信息查询(学生)
	public ResultSet listCan(Connection con,Book book)throws Exception{
		StringBuilder sb=new StringBuilder("select b.*,bt.type_name from book b,book_type bt where type_id=bt.id and b.status = 1");
		if(!toolUtil.isEmpty(book.getBookName())){
			sb.append(" and b.book_name like '%").append(book.getBookName()).append("%'");
		}
		if(book.getBookId() != null){
			sb.append(" and b.id=").append(book.getBookId());
		}
		PreparedStatement pstmt=(PreparedStatement) con.prepareStatement(sb.toString());
		return pstmt.executeQuery();
	}
	
	//图书信息删除
	public int delete(Connection con,String id)throws Exception{
		String sql="delete from book where id=?";
		PreparedStatement pstmt=(PreparedStatement) con.prepareStatement(sql);
		pstmt.setString(1, id);
		return pstmt.executeUpdate();
	}
	
	//图书信息修改
	public int update(Connection con,Book book)throws Exception{
		String sql="update book set book_name=?,type_id=?,author=?,publish=?,price=?,number=?,status=?,remark=? where id=?";
		PreparedStatement pstmt=(PreparedStatement) con.prepareStatement(sql);
		pstmt.setString(1, book.getBookName());
		pstmt.setInt(2, book.getBookTypeId());
		pstmt.setString(3, book.getAuthor());
		pstmt.setString(4, book.getPublish());
		pstmt.setDouble(5, book.getPrice());
		pstmt.setInt(6, book.getNumber());
		pstmt.setInt(7, book.getStatus());
		pstmt.setString(8, book.getRemark());
		pstmt.setInt(9, book.getBookId());
		return pstmt.executeUpdate();
	}
	
}
