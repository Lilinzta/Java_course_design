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
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.util.Objects;

public class AdminBookAdd extends JFrame {
	private final JFrame jf;
	private final JTextField textField;
	private final JTextField textField_1;
	private final JTextField textField_2;
	private final JTextField textField_3;
	private final JTextField textField_4;
	private final JTextField textField_6;
	private JComboBox comboBox;
	BookDao bookDao=new BookDao();
	DbUtil dbUtil=new DbUtil();
	BookTypeDao bookTypeDao=new BookTypeDao();
	public AdminBookAdd(){
		jf=new JFrame("管理员界面");
		jf.setBounds(400, 100, 600, 378);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "\u4E66\u7C4D\u6DFB\u52A0", TitledBorder.LEADING, TitledBorder.TOP, null, Color.RED));
		panel.setBounds(23, 21, 540, 275);
		jf.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("书名：");
		lblNewLabel.setFont(new Font("幼圆", Font.BOLD, 14));
		lblNewLabel.setBounds(58, 31, 45, 27);
		panel.add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(101, 31, 129, 27);
		panel.add(textField);
		textField.setColumns(10);
		
		JLabel label = new JLabel("作者：");
		label.setFont(new Font("幼圆", Font.BOLD, 14));
		label.setBounds(294, 31, 45, 27);
		panel.add(label);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(338, 31, 128, 27);
		panel.add(textField_1);
		
		JLabel label_1 = new JLabel("出版社：");
		label_1.setFont(new Font("幼圆", Font.BOLD, 14));
		label_1.setBounds(43, 79, 60, 27);
		panel.add(label_1);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(101, 79, 129, 27);
		panel.add(textField_2);
		
		JLabel label_2 = new JLabel("库存：");
		label_2.setFont(new Font("幼圆", Font.BOLD, 14));
		label_2.setBounds(58, 125, 45, 27);
		panel.add(label_2);
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(101, 125, 129, 27);
		panel.add(textField_3);
		
		JLabel label_3 = new JLabel("价格：");
		label_3.setFont(new Font("幼圆", Font.BOLD, 14));
		label_3.setBounds(294, 79, 45, 27);
		panel.add(label_3);
		
		textField_4 = new JTextField();
		textField_4.setColumns(10);
		textField_4.setBounds(337, 79, 129, 27);
		panel.add(textField_4);
		
		JLabel label_4 = new JLabel("类别：");
		label_4.setFont(new Font("幼圆", Font.BOLD, 14));
		label_4.setBounds(294, 125, 45, 27);
		panel.add(label_4);
		
		JLabel label_5 = new JLabel("描述：");
		label_5.setFont(new Font("幼圆", Font.BOLD, 14));
		label_5.setBounds(58, 170, 45, 27);
		panel.add(label_5);
		
		textField_6 = new JTextField();
		textField_6.setColumns(10);
		textField_6.setBounds(101, 173, 365, 27);
		panel.add(textField_6);
		
		JButton btnNewButton = new JButton("添加");
		btnNewButton.addActionListener(e -> {
			String bookName = textField.getText();
			String author = textField_1.getText();
			String publish = textField_2.getText();
			String priceStr = textField_4.getText();
			String numberStr = textField_3.getText();
			String remark = textField_6.getText();
			if (toolUtil.isEmpty(bookName) || toolUtil.isEmpty(author)
					|| toolUtil.isEmpty(publish) || toolUtil.isEmpty(priceStr)
					|| toolUtil.isEmpty(numberStr) || toolUtil.isEmpty(remark)) {
				JOptionPane.showMessageDialog(null, "请输入相关内容");
				return;
			}
			BookType selectedItem = (BookType) comboBox.getSelectedItem();
			assert selectedItem != null;
			Integer typeId = selectedItem.getTypeId();
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
			book.setBookName(bookName);
			book.setAuthor(author);
			book.setBookTypeId(typeId);
			book.setNumber(number);
			book.setPrice(price);
			book.setPublish(publish);
			book.setRemark(remark);
			book.setStatus(1);
			Connection con;
			try {
				con = dbUtil.getConnection();
				int i = bookDao.add(con, book);
				if (i == 1) {
					JOptionPane.showMessageDialog(null, "添加成功");
					reset();
				} else {
					JOptionPane.showMessageDialog(null, "添加失败");
				}
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				JOptionPane.showMessageDialog(null, "添加异常");
			}
		});
		btnNewButton.setFont(new Font("幼圆", Font.BOLD, 14));
		btnNewButton.setBounds(124, 227, 77, 27);
		panel.add(btnNewButton);
		
		JButton button = new JButton("重置");
		button.addActionListener(e -> reset());
		button.setFont(new Font("幼圆", Font.BOLD, 14));
		button.setBounds(329, 229, 77, 27);
		panel.add(button);
		
		comboBox = new JComboBox();
		comboBox.setBounds(338, 126, 128, 26);
		panel.add(comboBox);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon(Objects.requireNonNull(AdminBookAdd.class.getResource("/tupian/adBG2.png"))));
		lblNewLabel_1.setBounds(0, -4, 584, 323);
		jf.getContentPane().add(lblNewLabel_1);
		getBookType();
		
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
		mnNewMenu_2.add(mntmNewMenuItem_2);
		
		JMenuItem mntmNewMenuItem_3 = new JMenuItem("书籍修改");
		mntmNewMenuItem_3.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent evt) {
				jf.dispose();
				new AdminBookEdit();
			}
		});
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
	
		
		
		
		jf.setVisible(true);
		jf.setResizable(true);
	}
	protected void reset() {
		textField.setText("");
		textField_1.setText("");
		textField_2.setText("");
		textField_3.setText("");
		textField_4.setText("");
		textField_6.setText("");
		
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
				comboBox.addItem(bookType);
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
	public static void main(String[] args) {
		try {
			BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.generalNoTranslucencyShadow;
			org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
		} catch (Exception e) {
			e.printStackTrace();
		}
		//new AdminBookAdd();
	}
}
