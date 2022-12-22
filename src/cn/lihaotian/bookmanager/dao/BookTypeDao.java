package cn.lihaotian.bookmanager.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import cn.lihaotian.bookmanager.model.BookType;
import cn.lihaotian.bookmanager.utils.toolUtil;
public class BookTypeDao {
	
	// 图书类别添加
	public int add(Connection con,BookType bookType)throws Exception{
		//先查询是否有一样的类别名
		String sql="select * from book_type where type_name = ?";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1, bookType.getTypeName());
		ResultSet resultSet = pstmt.executeQuery();
		while(resultSet.next()){
			return 2;
		}
		sql="insert into book_type (type_name,remark) values(?,?)";
		PreparedStatement pstmt1=con.prepareStatement(sql);
		pstmt1.setString(1, bookType.getTypeName());
		pstmt1.setString(2, bookType.getRemark());
		return pstmt1.executeUpdate();
	}
	
	//查询图书类别集合
	public ResultSet list(Connection con,BookType bookType)throws Exception{
		StringBuilder sb=new StringBuilder("select * from book_type");
		if(!toolUtil.isEmpty(bookType.getTypeName())){
			sb.append(" and type_name like '%").append(bookType.getTypeName()).append("%'");
		}
		PreparedStatement pstmt=con.prepareStatement(sb.toString().replaceFirst("and", "where"));
//		PreparedStatement pstmt=con.prepareStatement("select * from book_type");
		return pstmt.executeQuery();
	}
	
	//删除图书类别
	public int delete(Connection con,String id)throws Exception{
		//先查询该类别下是否有书籍
		String sql="select b.* from book b left join book_type bt on b.type_id = bt.id where bt.id =? ";
		PreparedStatement pstmt1=con.prepareStatement(sql);
		pstmt1.setString(1, id);
		ResultSet query = pstmt1.executeQuery();
		int number =0;
		while(query.next()){
			number++;
		}
		if(number > 0){
			return 3;
		}
		
		//判断类别总数是否大于1
		sql="select * from book_type";
		PreparedStatement pstmt2=con.prepareStatement(sql);
		ResultSet resultSet = pstmt2.executeQuery();
		int count = 0;
		while(resultSet.next()){
			count++;
		}
		if(count<2){
			return 2;
		}
		sql="delete from book_type where id=?";
		PreparedStatement pstmt3=con.prepareStatement(sql);
		pstmt3.setString(1, id);
		return pstmt3.executeUpdate();
	}
	
	// 更新图示类别
	public int update(Connection con,BookType bookType)throws Exception{
		String sql="update book_type set type_name=?,remark=? where id=?";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1, bookType.getTypeName());
		pstmt.setString(2, bookType.getRemark());
		pstmt.setInt(3, bookType.getTypeId());
		return pstmt.executeUpdate();
	}
}
