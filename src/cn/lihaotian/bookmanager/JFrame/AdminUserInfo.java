package cn.lihaotian.bookmanager.JFrame;


import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;

import cn.lihaotian.bookmanager.dao.UserDao;
import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

import com.mysql.jdbc.Connection;
import cn.lihaotian.bookmanager.model.User;
import cn.lihaotian.bookmanager.utils.DbUtil;
import cn.lihaotian.bookmanager.utils.toolUtil;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.Color;

import javax.swing.JLabel;

import java.awt.Font;
import java.sql.ResultSet;
import java.util.Objects;
import java.util.Vector;

import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;

public class AdminUserInfo extends JFrame {
	private final JFrame jf;
	private final JTextField textField;
	private final JTable table;
	DbUtil dbUtil=new DbUtil();
	UserDao userDao=new UserDao();
	private final JTextField textField_1;
	private final JTextField textField_2;
	private final JTextField textField_3;
	private final JTextField textField_4;
	private final JTextField textField_5;
	public AdminUserInfo(){
		jf=new JFrame("管理员界面");
		jf.setBounds(400, 100, 600, 516);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.getContentPane().setLayout(null);
		
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
	
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "借阅信息", TitledBorder.LEADING, TitledBorder.TOP, null, Color.RED));
		panel.setBounds(20, 10, 540, 74);
		jf.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("用户名：");
		lblNewLabel.setFont(new Font("幼圆", Font.BOLD, 14));
		lblNewLabel.setBounds(57, 22, 60, 30);
		panel.add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(127, 27, 155, 25);
		panel.add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("查询");
		btnNewButton.addActionListener(e -> {
			String userName = textField.getText();
			User user = new User();
			user.setUserName(userName);
			putDates(user);
		});
		btnNewButton.setFont(new Font("幼圆", Font.BOLD, 14));
		btnNewButton.setBounds(316, 26, 93, 23);
		panel.add(btnNewButton);
		
		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "\u7528\u6237\u4FE1\u606F", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(255, 0, 0)));
		panel_1.setBounds(20, 94, 541, 195);
		
		 /*做一个表头栏数据  一位数组
		  * */
		 String[] title={"编号","用户名","密码","性别","电话"};
		/*具体的各栏行记录 先用空的二位数组占位*/
		 String[][] dates={};
		 /*然后实例化 上面2个控件对象*/
		DefaultTableModel model = new DefaultTableModel(dates, title);
		 table=new JTable(model);
		 putDates(new User());//获取数据库数据放置table中		 
		  panel_1.setLayout(null);
		  JScrollPane jscrollpane = new JScrollPane();
		  jscrollpane.setBounds(20, 22, 496, 154);
			jscrollpane.setViewportView(table);
			panel_1.add(jscrollpane);
			jf.getContentPane().add(panel_1);
		jf.getContentPane().add(panel_1);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(null, "\u7528\u6237\u7F16\u8F91", TitledBorder.LEADING, TitledBorder.TOP, null, Color.RED));
		panel_2.setBounds(20, 302, 540, 137);
		jf.getContentPane().add(panel_2);
		panel_2.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("编号：");
		lblNewLabel_1.setFont(new Font("幼圆", Font.BOLD, 15));
		lblNewLabel_1.setBounds(49, 30, 48, 34);
		panel_2.add(lblNewLabel_1);
		
		textField_1 = new JTextField();
		textField_1.setEditable(false);
		textField_1.setBounds(103, 37, 66, 21);
		panel_2.add(textField_1);
		textField_1.setColumns(10);
		
		JLabel label = new JLabel("用户名：");
		label.setFont(new Font("幼圆", Font.BOLD, 15));
		label.setBounds(187, 30, 66, 34);
		panel_2.add(label);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(259, 37, 93, 21);
		panel_2.add(textField_2);
		
		JLabel label_1 = new JLabel("密码：");
		label_1.setFont(new Font("幼圆", Font.BOLD, 15));
		label_1.setBounds(383, 30, 48, 34);
		panel_2.add(label_1);
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(437, 37, 93, 21);
		panel_2.add(textField_3);
		
		JButton btnNewButton_1 = new JButton("修改");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String userId = textField_1.getText();
				String userName = textField_2.getText();
				String password = textField_3.getText();
				String sex=textField_4.getText();
				String phone=textField_5.getText();
				if (toolUtil.isEmpty(userName) || toolUtil.isEmpty(password)||toolUtil.isEmpty(sex)||toolUtil.isEmpty(phone)) {
					JOptionPane.showMessageDialog(null, "请输入相关信息");
					return;
				}
				User user = new User();
				user.setUserId(Integer.parseInt(userId));
				user.setUserName(userName);
				user.setPassword(password);
				user.setSex(sex);
				user.setPhone(phone);
				Connection con = null;
				try {
					con = dbUtil.getConnection();
					int i = userDao.update(con, user);
					if (i == 1) {
						JOptionPane.showMessageDialog(null, "修改成功");
						putDates(new User());
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
			}
		});
		btnNewButton_1.setFont(new Font("幼圆", Font.BOLD, 15));
		btnNewButton_1.setBounds(422, 74, 87, 34);
		panel_2.add(btnNewButton_1);
		
		JLabel label_2 = new JLabel("性别：");
		label_2.setFont(new Font("幼圆", Font.BOLD, 15));
		label_2.setBounds(49, 74, 48, 34);
		panel_2.add(label_2);
		
		textField_4 = new JTextField();
		textField_4.setColumns(10);
		textField_4.setBounds(103, 81, 66, 21);
		panel_2.add(textField_4);
		
		JLabel label_3 = new JLabel("手机号：");
		label_3.setFont(new Font("幼圆", Font.BOLD, 15));
		label_3.setBounds(187, 74, 66, 34);
		panel_2.add(label_3);
		
		textField_5 = new JTextField();
		textField_5.setColumns(10);
		textField_5.setBounds(259, 81, 93, 21);
		panel_2.add(textField_5);
		
		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon(Objects.requireNonNull(AdminUserInfo.class.getResource("/tupian/adBG3.png"))));
		lblNewLabel_2.setBounds(0, 0, 584, 457);
		jf.getContentPane().add(lblNewLabel_2);
		
		table.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent evt) {
				tableMousePressed();
			}
		});
		
		jf.setVisible(true);
		jf.setResizable(true);
	}
	protected void tableMousePressed() {
		int row = table.getSelectedRow();
		textField_1.setText(table.getValueAt(row, 0).toString());
		textField_2.setText(table.getValueAt(row, 1).toString());
		textField_3.setText(table.getValueAt(row, 2).toString());
		textField_4.setText(table.getValueAt(row, 3).toString());
		textField_5.setText(table.getValueAt(row, 4).toString());
	}
	private void putDates(User user) {
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.setRowCount(0);

		Connection con = null;
		try {
			con = dbUtil.getConnection();
			ResultSet list = userDao.list(con, user);
			while (list.next()) {
				Vector<java.io.Serializable> rowData = new Vector<>();
				rowData.add(list.getInt("id"));
				rowData.add(list.getString("username"));
				rowData.add(list.getString("password"));
				rowData.add(list.getString("sex"));
				rowData.add(list.getString("phone"));
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
	public static void main(String[] args) {
		try {
			BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.generalNoTranslucencyShadow;
			org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
		} catch (Exception e) {
			e.printStackTrace();
		}
		//new AdminUserInfo();
	}
}
