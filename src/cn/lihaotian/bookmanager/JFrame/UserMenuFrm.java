package cn.lihaotian.bookmanager.JFrame;

import cn.lihaotian.bookmanager.dao.BookDao;
import cn.lihaotian.bookmanager.dao.BorrowDetailDao;
import cn.lihaotian.bookmanager.model.Book;
import cn.lihaotian.bookmanager.model.BorrowDetail;
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
import java.sql.ResultSet;
import java.util.Objects;
import java.util.Vector;


public class UserMenuFrm extends JFrame {
	private final JFrame jf;
	private final JTextField textField;
	private final JTable table;
	private final JTable BookTable;
	private final JButton btnBackBook;
	DbUtil dbUtil=new DbUtil();
	BorrowDetailDao bdetailDao=new BorrowDetailDao();
	BookDao bookDao=new BookDao();
	private final JTextField textField_1;
	private JComboBox<String> comboBox;
	private final JTextField textField_2;
	private final JTextField textField_3;

	public UserMenuFrm() {
		jf=new JFrame();
		jf.setTitle("用户页面");
		jf.setBounds(250,100,700,902);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.getContentPane().setLayout(null);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "\u501F\u9605\u4FE1\u606F", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(255, 0, 0)));
		panel_1.setBounds(23, 48, 651, 239);
		
		 /*做一个表头栏数据  一位数组
		  * */
		 String[] title={"编号", "书名", "状态", "借书时间", "还书时间"};
		/*具体的各栏行记录 先用空的二位数组占位*/
		 String[][] dates={};
		 /*然后实例化 上面2个控件对象*/
		DefaultTableModel model = new DefaultTableModel(dates, title);
		 table=new JTable();
		 table.setModel(model);
		 
		 putDates(new BorrowDetail());//获取数据库数据放置table中		 
		  panel_1.setLayout(null);
		  JScrollPane jscrollpane = new JScrollPane();
		  jscrollpane.setBounds(20, 22, 607, 188);
			jscrollpane.setViewportView(table);
			panel_1.add(jscrollpane);
			jf.getContentPane().add(panel_1);

		JLabel lblNewLabel_1 = new JLabel("New label");
			lblNewLabel_1.setForeground(Color.RED);
			lblNewLabel_1.setFont(new Font("Dialog", Font.BOLD, 18));
			lblNewLabel_1.setBounds(315, 10, 197, 28);
			jf.getContentPane().add(lblNewLabel_1);
			lblNewLabel_1.setText(LoginFrm.currentUser.getUserName());

		JLabel lblNewLabel_2 = new JLabel("欢迎您,");
			lblNewLabel_2.setFont(new Font("Dialog", Font.BOLD, 18));
			lblNewLabel_2.setBounds(254, 11, 258, 28);
			jf.getContentPane().add(lblNewLabel_2);
			
			JPanel panel = new JPanel();
			panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "\u8FD8\u4E66", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(255, 0, 0)));
			panel.setBounds(23, 294, 651, 70);
			jf.getContentPane().add(panel);
			panel.setLayout(null);
			
			JLabel lblNewLabel = new JLabel("编号：");
			lblNewLabel.setBounds(90, 25, 51, 27);
			panel.add(lblNewLabel);
			lblNewLabel.setFont(new Font("幼圆", Font.BOLD, 16));
			
			textField = new JTextField();
			textField.setBounds(145, 28, 116, 24);
			panel.add(textField);
			textField.setColumns(10);
			
			btnBackBook = new JButton("还书");
			btnBackBook.setFont(new Font("Dialog", Font.BOLD, 15));
			btnBackBook.setBounds(299, 25, 85, 31);
			panel.add(btnBackBook);

		JButton button = new JButton("退出系统");
			button.setFont(new Font("Dialog", Font.BOLD, 15));
			button.setBounds(407, 25, 103, 31);
			panel.add(button);

		JPanel panel_2 = new JPanel();
			panel_2.setBorder(new TitledBorder(null, "借阅信息", TitledBorder.LEADING, TitledBorder.TOP, null, Color.RED));
			panel_2.setBounds(23, 374, 651, 346);
			jf.getContentPane().add(panel_2);
			panel_2.setLayout(null);
			
			textField_1 = new JTextField();
			textField_1.setColumns(10);
			textField_1.setBounds(252, 23, 135, 27);
			panel_2.add(textField_1);

		JButton button_1 = new JButton("查询");
			button_1.addActionListener(e -> {
				int index = comboBox.getSelectedIndex();
				if(index==0){
					String bookName = textField_1.getText();
					Book book = new Book();
					book.setBookName(bookName);
					putDates(book);
				}else{
					String authoerName = textField_1.getText();
					Book book = new Book();
					book.setAuthor(authoerName);
					putDates(book);
				}
			});
			button_1.setFont(new Font("幼圆", Font.BOLD, 16));
			button_1.setBounds(408, 20, 93, 33);
			panel_2.add(button_1);
			
			comboBox = new JComboBox<>();
			comboBox.setFont(new Font("幼圆", Font.BOLD, 15));
			comboBox.setBounds(123, 26, 109, 24);
			comboBox.addItem("书籍名称");
			comboBox.addItem("书籍作者");
			panel_2.add(comboBox);
			
			 String[] BookTitle={"编号", "书名", "类型", "作者", "描述" };
				/*具体的各栏行记录 先用空的二位数组占位*/
				 String[][] BookDates={};
				 /*然后实例化 上面2个控件对象*/
		DefaultTableModel bookModel = new DefaultTableModel(BookDates, BookTitle);
				 BookTable=new JTable(bookModel);
				 putDates(new Book());//获取数据库数据放置table中		 
				 panel_2.setLayout(null);
				  JScrollPane jscrollpane1 = new JScrollPane();
				  jscrollpane1.setBounds(22, 74, 607, 250);
					jscrollpane1.setViewportView(BookTable);
					panel_2.add(jscrollpane1);
					jf.getContentPane().add(panel_1);
					
					JPanel panel_3 = new JPanel();
					panel_3.setBorder(new TitledBorder(null, "\u501F\u4E66", TitledBorder.LEADING, TitledBorder.TOP, null, Color.RED));
					panel_3.setBounds(23, 730, 645, 87);
					jf.getContentPane().add(panel_3);
					panel_3.setLayout(null);
					
					JLabel label = new JLabel("编号：");
					label.setFont(new Font("Dialog", Font.BOLD, 15));
					label.setBounds(68, 31, 48, 33);
					panel_3.add(label);
					
					textField_2 = new JTextField();
					textField_2.setEditable(false);
					textField_2.setColumns(10);
					textField_2.setBounds(126, 34, 135, 27);
					panel_3.add(textField_2);
					
					JLabel label_1 = new JLabel("书名：");
					label_1.setFont(new Font("Dialog", Font.BOLD, 15));
					label_1.setBounds(281, 31, 48, 33);
					panel_3.add(label_1);
					
					textField_3 = new JTextField();
					textField_3.setEditable(false);
					textField_3.setColumns(10);
					textField_3.setBounds(339, 34, 135, 27);
					panel_3.add(textField_3);
					
					JButton button_2 = new JButton("借书");
					//借书
					button_2.addActionListener(e -> {
						String bookId = textField_2.getText();
						String bookName = textField_3.getText();
						if (toolUtil.isEmpty(bookId) || toolUtil.isEmpty(bookName)) {
							JOptionPane.showMessageDialog(null, "请选择相关书籍");
							return;
						}
						BorrowDetail borrowDetail = new BorrowDetail();
						borrowDetail.setUserId(LoginFrm.currentUser.getUserId());
						borrowDetail.setBookId(Integer.parseInt(bookId));
						borrowDetail.setStatus(1);
						borrowDetail.setBorrowTime(toolUtil.getTime());
						Connection con = null;
						try {
							con = dbUtil.getConnection();

							//先查询是否有该书
							ResultSet list = bdetailDao.list(con, borrowDetail);
							while(list.next()){
								JOptionPane.showMessageDialog(null, "该书已在借,请先还再借");
								return;
							}
							int i = bdetailDao.add(con, borrowDetail);
							if (i == 1) {
								JOptionPane.showMessageDialog(null, "借书成功");
								putDates(new BorrowDetail());
							} else {
								JOptionPane.showMessageDialog(null, "借书失败");
							}
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
							JOptionPane.showMessageDialog(null, "借书异常");
						}finally{
							try {
								dbUtil.closeCon(con);
							} catch (Exception e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
					});
					button_2.setFont(new Font("Dialog", Font.BOLD, 16));
					button_2.setBounds(495, 31, 80, 33);
					panel_3.add(button_2);

		JLabel lblNewLabel_3 = new JLabel("");
					lblNewLabel_3.setIcon(new ImageIcon(Objects.requireNonNull(UserMenuFrm.class.getResource("/tupian/uBG.png"))));
					lblNewLabel_3.setBounds(0, 0, 684, 864);
					jf.getContentPane().add(lblNewLabel_3);
					
					BookTable.addMouseListener(new MouseAdapter() {
						public void mousePressed(MouseEvent evt) {
							tableMousePressed();
						}
					});
			
			
			button.addActionListener(e -> {
				JOptionPane.showMessageDialog(null, "欢迎再次使用");
				jf.dispose();
			});
			btnBackBook.setVisible(false);
			btnBackBook.addActionListener(e -> {
				String BorrowStr = textField.getText();
				if (toolUtil.isEmpty(BorrowStr)) {
					JOptionPane.showMessageDialog(null, "请选择未还的书籍");
					return;
				}
				BorrowDetail detail = new BorrowDetail();
				detail.setBorrowId(Integer.parseInt(BorrowStr));
				detail.setStatus(2);
				detail.setReturnTime(toolUtil.getTime());
				Connection con = null;
				try {
					con = dbUtil.getConnection();
					int i = bdetailDao.returnBook(con, detail);
					if (i == 1) {
						JOptionPane.showMessageDialog(null, "还书成功");
					} else {
						JOptionPane.showMessageDialog(null, "还书失败");
					}
				} catch (Exception e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "还书异常");
				}finally{
					try {
						dbUtil.closeCon(con);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
				putDates(new BorrowDetail());
			});
		
		
		jf.setVisible(true);
		jf.setResizable(true);
	}
	protected void tableMousePressed() {
		int row = BookTable.getSelectedRow();
		Object bookId = BookTable.getValueAt(row, 0);
		Object bookName = BookTable.getValueAt(row, 1);
		textField_2.setText(bookId.toString());
		textField_3.setText(bookName.toString());
		
	}
	//从数据库获取书籍信息
	private void putDates(Book book) {
		DefaultTableModel model = (DefaultTableModel) BookTable.getModel();
		model.setRowCount(0);
		Connection con = null;
		try {
			con = dbUtil.getConnection();
			book.setStatus(1);
			ResultSet list = bookDao.list(con, book);
			
			while (list.next()) {
				Vector<java.io.Serializable> rowData = new Vector<>();
				rowData.add(list.getInt("id"));
				rowData.add(list.getString("book_name"));
				rowData.add(list.getString("type_name"));
				rowData.add(list.getString("author"));
				rowData.add(list.getString("remark"));
				model.addRow(rowData);
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
	private void putDates(BorrowDetail borrowDetail) {
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.setRowCount(0);
		Integer userId = LoginFrm.currentUser.getUserId();
		Connection con = null;
		try {
			con = dbUtil.getConnection();
			borrowDetail.setUserId(userId);
			ResultSet list = bdetailDao.list(con, borrowDetail);
			while (list.next()) {
				Vector<java.io.Serializable> rowData = new Vector<>();
				rowData.add(list.getInt("id"));
				rowData.add(list.getString("book_name"));
				int status = list.getInt("status");
				if (status == 1) {
					rowData.add("在借");
				}
				if (status == 2) {
					rowData.add("已还");
				}
				rowData.add(toolUtil.getDateByTime(list.getLong("borrow_time")));
				if (status == 2) {
					rowData.add(toolUtil.getDateByTime(list.getLong("return_time")));
				}
				model.addRow(rowData);
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
		table.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent me){
				putBack();
			}
		});
	}
	protected void putBack(){
		int row = table.getSelectedRow();
		Integer borrowId = (Integer) table.getValueAt(row, 0);
		String status = (String) table.getValueAt(row, 2);
		textField.setText(borrowId.toString());
		this.btnBackBook.setVisible(status.equals("在借"));
		
	}
	public static void main(String[] args) {
		try {
			BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.generalNoTranslucencyShadow;
			org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
		} catch (Exception e) {
			e.printStackTrace();
		}
		new UserMenuFrm();
	}
}