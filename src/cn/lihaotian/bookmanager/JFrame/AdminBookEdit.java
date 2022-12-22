package cn.lihaotian.bookmanager.JFrame;


import cn.lihaotian.bookmanager.dao.BookDao;
import cn.lihaotian.bookmanager.dao.BookTypeDao;
import cn.lihaotian.bookmanager.model.Book;
import cn.lihaotian.bookmanager.model.BookType;
import cn.lihaotian.bookmanager.utils.DbUtil;
import cn.lihaotian.bookmanager.utils.toolUtil;
import com.mysql.jdbc.Connection;
import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.util.Objects;
import java.util.Vector;

public class AdminBookEdit extends JFrame {
	private final JFrame jf;
	private final JTextField textField;
	private final JTable table;
	DbUtil dbUtil=new DbUtil();
	BookDao bookDao=new BookDao();
	BookTypeDao bookTypeDao=new BookTypeDao();
	private final JTextField textField_1;
	private final JTextField textField_2;
	private final JTextField textField_3;
	private final JTextField textField_4;
	private final JTextField textField_5;
	private final JTextField textField_6;
	private final JTextField textField_7;
	private final JComboBox<String> comboBox;
	private final JComboBox<String> comboBox_2;
	private final JComboBox<BookType> comboBox_1;
	public AdminBookEdit(){
		jf=new JFrame("管理员界面");
		jf.setBounds(400, 50, 600, 672);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		jf.setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("类别管理");
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("类别添加");
		mntmNewMenuItem.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent evt) {
				jf.dispose();
				new AdminMenuFrm();
			}
		});
		mnNewMenu.add(mntmNewMenuItem);
		
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("类别修改");
		mntmNewMenuItem_1.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent evt) {
				jf.dispose();
				new AdminBTypeEdit();
			}
		});
		mnNewMenu.add(mntmNewMenuItem_1);
		
		JMenu mnNewMenu_2 = new JMenu("书籍管理");
		menuBar.add(mnNewMenu_2);
		
		JMenuItem mntmNewMenuItem_2 = new JMenuItem("书籍添加");
		mntmNewMenuItem_2.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent evt) {
				jf.dispose();
				new AdminBookAdd();
			}
		});
		mnNewMenu_2.add(mntmNewMenuItem_2);
		
		JMenuItem mntmNewMenuItem_3 = new JMenuItem("书籍修改");
		mnNewMenu_2.add(mntmNewMenuItem_3);
		
		JMenu menu1 = new JMenu("用户管理");
		menuBar.add(menu1);
		
		JMenuItem mntmNewMenuItem_4 = new JMenuItem("用户信息");
		mntmNewMenuItem_4.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent evt) {
				jf.dispose();
				new AdminUserInfo();
			}
		});
		menu1.add(mntmNewMenuItem_4);
		
		JMenuItem mntmNewMenuItem_5 = new JMenuItem("借阅信息");
		mntmNewMenuItem_5.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent evt) {
				jf.dispose();
				new AdminBorrowInfo();
			}
		});
		menu1.add(mntmNewMenuItem_5);
		
		JMenu mnNewMenu_1 = new JMenu("退出系统");
		mnNewMenu_1.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent evt) {
				JOptionPane.showMessageDialog(null, "欢迎再次使用");
				jf.dispose();
			}
		});
		menuBar.add(mnNewMenu_1);
	
		jf.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "\u4E66\u76EE\u67E5\u8BE2", TitledBorder.LEADING, TitledBorder.TOP, null, Color.RED));
		panel.setBounds(20, 10, 541, 78);
		jf.getContentPane().add(panel);
		panel.setLayout(null);
		
		comboBox = new JComboBox<>();
		comboBox.setFont(new Font("幼圆", Font.BOLD, 15));
		comboBox.setBounds(55, 28, 109, 24);
		comboBox.addItem("书籍名称");
		comboBox.addItem("书籍作者");
		panel.add(comboBox);
		
		textField = new JTextField();
		textField.setBounds(185, 28, 146, 24);
		panel.add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("查询");
		btnNewButton.addActionListener(e -> {
			int index = comboBox.getSelectedIndex();
			if(index==0){
				String bookName = textField.getText();
				Book book = new Book();
				book.setBookName(bookName);
				putDates(book);
			}else{
				String authoerName = textField.getText();
				Book book = new Book();
				book.setAuthor(authoerName);
				putDates(book);
			}
		});
		btnNewButton.setFont(new Font("幼圆", Font.BOLD, 14));
		btnNewButton.setBounds(352, 28, 81, 25);
		panel.add(btnNewButton);
		
		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "\u4E66\u7C4D\u4FE1\u606F", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(255, 0, 0)));
		panel_1.setBounds(20, 105, 541, 195);
		
		 /*做一个表头栏数据  一位数组
		  * */
		 String[] title={"编号", "书名", "类别", "作者", "价格", "库存", "状态" };
		/*具体的各栏行记录 先用空的二位数组占位*/
		 String[][] dates={};
		 /*然后实例化 上面2个控件对象*/
		DefaultTableModel model = new DefaultTableModel(dates, title);
		 table=new JTable(model);
		 putDates(new Book());//获取数据库数据放置table中		 
		  panel_1.setLayout(null);
		  JScrollPane jscrollpane = new JScrollPane();
		  jscrollpane.setBounds(20, 22, 496, 154);
			jscrollpane.setViewportView(table);
			panel_1.add(jscrollpane);
			jf.getContentPane().add(panel_1);
		jf.getContentPane().add(panel_1);
		
		table.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent evt) {
				tableMousePressed();
			}
		});
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(20, 310, 541, 292);
		jf.getContentPane().add(panel_2);
		panel_2.setLayout(null);
		
		JLabel label = new JLabel("编号：");
		label.setFont(new Font("幼圆", Font.BOLD, 14));
		label.setBounds(58, 10, 45, 27);
		panel_2.add(label);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(101, 10, 129, 27);
		panel_2.add(textField_1);
		
		JLabel label_1 = new JLabel("书名：");
		label_1.setFont(new Font("幼圆", Font.BOLD, 14));
		label_1.setBounds(294, 10, 45, 27);
		panel_2.add(label_1);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(338, 10, 128, 27);
		panel_2.add(textField_2);
		
		JLabel label_2 = new JLabel("作者：");
		label_2.setFont(new Font("幼圆", Font.BOLD, 14));
		label_2.setBounds(58, 58, 45, 27);
		panel_2.add(label_2);
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(101, 58, 129, 27);
		panel_2.add(textField_3);
		
		JLabel label_3 = new JLabel("价格：");
		label_3.setFont(new Font("幼圆", Font.BOLD, 14));
		label_3.setBounds(58, 104, 45, 27);
		panel_2.add(label_3);
		
		textField_4 = new JTextField();
		textField_4.setColumns(10);
		textField_4.setBounds(101, 104, 129, 27);
		panel_2.add(textField_4);
		
		JLabel label_4 = new JLabel("出版：");
		label_4.setFont(new Font("幼圆", Font.BOLD, 14));
		label_4.setBounds(294, 58, 45, 27);
		panel_2.add(label_4);
		
		textField_5 = new JTextField();
		textField_5.setColumns(10);
		textField_5.setBounds(337, 58, 129, 27);
		panel_2.add(textField_5);
		
		JLabel label_5 = new JLabel("类别：");
		label_5.setFont(new Font("幼圆", Font.BOLD, 14));
		label_5.setBounds(58, 189, 45, 27);
		panel_2.add(label_5);
		
		comboBox_1 = new JComboBox<>();
		comboBox_1.setBounds(102, 190, 128, 26);
		//获取类别
		getBookType();
		panel_2.add(comboBox_1);
		
		JLabel label_6 = new JLabel("库存：");
		label_6.setFont(new Font("幼圆", Font.BOLD, 14));
		label_6.setBounds(294, 104, 45, 27);
		panel_2.add(label_6);
		
		textField_6 = new JTextField();
		textField_6.setColumns(10);
		textField_6.setBounds(337, 104, 129, 27);
		panel_2.add(textField_6);
		
		JLabel label_7 = new JLabel("描述：");
		label_7.setFont(new Font("幼圆", Font.BOLD, 14));
		label_7.setBounds(58, 152, 45, 27);
		panel_2.add(label_7);
		
		textField_7 = new JTextField();
		textField_7.setColumns(10);
		textField_7.setBounds(101, 152, 365, 27);
		panel_2.add(textField_7);
		
		JLabel label_8 = new JLabel("状态：");
		label_8.setFont(new Font("幼圆", Font.BOLD, 14));
		label_8.setBounds(294, 190, 45, 27);
		panel_2.add(label_8);
		
		 comboBox_2 = new JComboBox<>();
		comboBox_2.setBounds(338, 191, 128, 26);
		comboBox_2.addItem("上架");
		comboBox_2.addItem("下架");
		panel_2.add(comboBox_2);
		
		JButton btnNewButton_1 = new JButton("修改");
		btnNewButton_1.addActionListener(e -> {
			String bookName = textField_2.getText();
			String author = textField_3.getText();
			String publish = textField_5.getText();
			String priceStr = textField_4.getText();
			String numberStr = textField_6.getText();
			String remark = textField_7.getText();
			String bookId = textField_1.getText();
			if (toolUtil.isEmpty(bookId) || toolUtil.isEmpty(bookName)
					|| toolUtil.isEmpty(author) || toolUtil.isEmpty(publish)
					|| toolUtil.isEmpty(priceStr)
					|| toolUtil.isEmpty(numberStr) || toolUtil.isEmpty(remark)) {
				JOptionPane.showMessageDialog(null, "请输入相关内容");
				return;
			}
			BookType selectedItem = (BookType) comboBox_1.getSelectedItem();
			assert selectedItem != null;
			Integer typeId = selectedItem.getTypeId();
			int index = comboBox_2.getSelectedIndex();

			int number;
			double price;
			try {
				number = Integer.parseInt(numberStr);
				price = new BigDecimal(priceStr).setScale(2, RoundingMode.DOWN)
						.doubleValue();
			} catch (Exception e1) {
				JOptionPane.showMessageDialog(null, "参数错误");
				return;
			}
			Book book = new Book();
			book.setBookId(Integer.parseInt(bookId));
			book.setBookName(bookName);
			book.setAuthor(author);
			book.setBookTypeId(typeId);
			book.setNumber(number);
			book.setPrice(price);
			book.setPublish(publish);
			book.setRemark(remark);
			book.setStatus(1);
			if (index == 0) {
				book.setStatus(1);
			} else if (index == 1) {
				book.setStatus(2);
			}
			Connection con = null;
			try {
				con = dbUtil.getConnection();
				int i = bookDao.update(con, book);
				if (i == 1) {
					JOptionPane.showMessageDialog(null, "修改成功");
				} else {
					JOptionPane.showMessageDialog(null, "修改失败");
				}
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				JOptionPane.showMessageDialog(null, "修改异常");
			}finally{
				try {
					dbUtil.closeCon(con);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

			putDates(new Book());
		});
		btnNewButton_1.setFont(new Font("幼圆", Font.BOLD, 14));
		btnNewButton_1.setBounds(304, 235, 93, 35);
		panel_2.add(btnNewButton_1);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(Objects.requireNonNull(AdminBookEdit.class.getResource("/tupian/adBG3.png"))));
		lblNewLabel.setBounds(0, 0, 584, 613);
		jf.getContentPane().add(lblNewLabel);
		
		

		jf.setVisible(true);
		jf.setResizable(true);
	}
	private void getBookType() {
		Connection con = null;
		try {
			con = dbUtil.getConnection();
			ResultSet list = bookTypeDao.list(con, new BookType());
			while (list.next()) {
				BookType bookType = new BookType();
				bookType.setTypeId(list.getInt("id"));
				bookType.setTypeName(list.getString("type_name"));
				comboBox_1.addItem(bookType);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	//点击表格获取数据
	/**
	 */
	protected void tableMousePressed() {
		int row = table.getSelectedRow();
		Integer bookId = (Integer) table.getValueAt(row, 0);
		Book book = new Book();
		book.setBookId(bookId);
		Connection con = null;
		try {
			con = dbUtil.getConnection();
			ResultSet list = bookDao.list(con, book);
			if (list.next()) {
				textField_1.setText(list.getString("id"));
				textField_2.setText(list.getString("book_name"));
				textField_3.setText(list.getString("author"));
				textField_5.setText(list.getString("publish"));
				textField_4.setText(list.getString("price"));
				textField_6.setText(list.getString("number"));
				textField_7.setText(list.getString("remark"));
				int status = list.getInt("status");
				if (status == 1) {
					comboBox_2.setSelectedIndex(0);
				} else {
					comboBox_2.setSelectedIndex(1);
				}
				int typeId = list.getInt("type_id");
				int count = comboBox_1.getItemCount();
				for (int i = 0; i < count; i++) {
					BookType bookType = comboBox_1.getItemAt(i);
					if (bookType.getTypeId() == typeId) {
						comboBox_1.setSelectedIndex(i);
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	private void putDates(Book book) {
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.setRowCount(0);
		Connection con = null;
		try {
			con = dbUtil.getConnection();
			ResultSet resultSet = bookDao.list(con, book);
			while (resultSet.next()) {
				Vector<java.io.Serializable> rowData = new Vector<>();
				rowData.add(resultSet.getInt("id"));
				rowData.add(resultSet.getString("book_name"));
				rowData.add(resultSet.getString("type_name"));
				rowData.add(resultSet.getString("author"));
				rowData.add(resultSet.getDouble("price"));
				rowData.add(resultSet.getInt("number"));
				if (resultSet.getInt("status") == 1) {
					rowData.add("上架");
				} else {
					rowData.add("下架");
				}
				model.addRow(rowData);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	public static void main(String[] args) {
		try {
			BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.generalNoTranslucencyShadow;
			org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
		} catch (Exception e) {
			e.printStackTrace();
		}
		//new AdminBookEdit();
	}
}
