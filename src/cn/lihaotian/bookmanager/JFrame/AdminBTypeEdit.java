package cn.lihaotian.bookmanager.JFrame;


import cn.lihaotian.bookmanager.dao.BookTypeDao;
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
import java.sql.ResultSet;
import java.util.Objects;
import java.util.Vector;

public class AdminBTypeEdit extends JFrame {
	private final JFrame jf;
	private final JTable table;
	DbUtil dbUtil=new DbUtil();
	BookTypeDao bookTypeDao=new BookTypeDao();
	private final JTextField textField;
	private final JTextField textField_1;
	private final JTextField textField_2;
	public AdminBTypeEdit(){
		jf=new JFrame("管理员界面");
		jf.setBounds(400, 100, 611, 472);
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
	
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "\u7C7B\u522B\u4FE1\u606F", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(255, 0, 0)));
		panel_1.setBounds(20, 10, 554, 208);
		
		 /*做一个表头栏数据  一位数组
		  * */
		 String[] title={"编号","类别名称","类别描述"};
		/*具体的各栏行记录 先用空的二位数组占位*/
		 String[][] dates={};
		 /*然后实例化 上面2个控件对象*/
		DefaultTableModel model = new DefaultTableModel(dates, title);
		 table=new JTable(model);
		 putDates();//获取数据库数据放置table中		 
		  panel_1.setLayout(null);
		  JScrollPane jscrollpane = new JScrollPane();
		  jscrollpane.setBounds(20, 22, 517, 163);
			jscrollpane.setViewportView(table);
			panel_1.add(jscrollpane);
			jf.getContentPane().add(panel_1);
			
			JPanel panel = new JPanel();
			panel.setBorder(new TitledBorder(null, "\u7C7B\u522B\u7F16\u8F91", TitledBorder.LEADING, TitledBorder.TOP, null, Color.RED));
			panel.setBounds(20, 228, 554, 168);
			jf.getContentPane().add(panel);
			panel.setLayout(null);
			
			table.addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent evt) {
					typeTableMousePressed();
				}
			});
			
			JLabel lblNewLabel = new JLabel("编号：");
			lblNewLabel.setFont(new Font("幼圆", Font.BOLD, 14));
			lblNewLabel.setBounds(73, 26, 45, 28);
			panel.add(lblNewLabel);
			
			textField = new JTextField();
			textField.setBounds(116, 30, 92, 24);
			panel.add(textField);
			textField.setColumns(10);
			
			JLabel label = new JLabel("类别名称：");
			label.setFont(new Font("幼圆", Font.BOLD, 14));
			label.setBounds(238, 26, 75, 28);
			panel.add(label);
			
			textField_1 = new JTextField();
			textField_1.setColumns(10);
			textField_1.setBounds(314, 30, 122, 24);
			panel.add(textField_1);
			
			JLabel label_1 = new JLabel("描述：");
			label_1.setFont(new Font("幼圆", Font.BOLD, 14));
			label_1.setBounds(73, 65, 45, 28);
			panel.add(label_1);
			
			textField_2 = new JTextField();
			textField_2.setColumns(10);
			textField_2.setBounds(116, 69, 320, 24);
			panel.add(textField_2);
			
			JButton btnNewButton = new JButton("修改");
			//类别修改
			btnNewButton.addActionListener(e -> {
				String typeId = textField.getText();
				String typeName = textField_1.getText();
				String typeRemark = textField_2.getText();
				if (toolUtil.isEmpty(typeName) || toolUtil.isEmpty(typeRemark)) {
					JOptionPane.showMessageDialog(null, "请输入相关信息");
					return;
				}
				BookType bookType = new BookType();
				bookType.setTypeId(Integer.parseInt(typeId));
				bookType.setTypeName(typeName);
				bookType.setRemark(typeRemark);
				Connection con = null;
				try {
					con = dbUtil.getConnection();
					int i = bookTypeDao.update(con, bookType);
					if (i == 1) {
						JOptionPane.showMessageDialog(null, "修改成功");
						 putDates();
					} else {
						JOptionPane.showMessageDialog(null, "修改失败");
					}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "修改异常");
				} finally {
					try {
						dbUtil.closeCon(con);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
			btnNewButton.setFont(new Font("幼圆", Font.BOLD, 14));
			btnNewButton.setBounds(128, 117, 93, 28);
			panel.add(btnNewButton);
			
			JButton button = new JButton("删除");
			button.addActionListener(e -> {
				String typeId = textField.getText();
				if (toolUtil.isEmpty(typeId)) {
					JOptionPane.showMessageDialog(null, "请选择相关信息");
					return;
				}
				Connection con = null;
				try {
					con = dbUtil.getConnection();
					int i = bookTypeDao.delete(con, typeId);
					if (i == 1) {
						JOptionPane.showMessageDialog(null, "删除成功");
						putDates();
					} else if (i == 2) {
						JOptionPane.showMessageDialog(null, "删除失败-类别最少保留一个");
					} else if (i == 3) {
						JOptionPane.showMessageDialog(null, "删除失败-该类别下有书籍");
					} else {
						JOptionPane.showMessageDialog(null, "删除失败");
					}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "删除异常");
				} finally {
					try {
						dbUtil.closeCon(con);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
			button.setFont(new Font("幼圆", Font.BOLD, 14));
			button.setBounds(314, 117, 93, 28);
			panel.add(button);
			
			JLabel lblNewLabel_1 = new JLabel("");
			lblNewLabel_1.setIcon(new ImageIcon(Objects.requireNonNull(AdminBTypeEdit.class.getResource("/tupian/adBG3.png"))));
			lblNewLabel_1.setBounds(0, 0, 595, 413);
			jf.getContentPane().add(lblNewLabel_1);
		
		
		jf.setVisible(true);
		jf.setResizable(true);
	}
	//点击表格获取信息
	protected void typeTableMousePressed() {
		int row = table.getSelectedRow();
		textField.setText(table.getValueAt(row, 0).toString());
		textField_1.setText(table.getValueAt(row, 1).toString());
		textField_2.setText(table.getValueAt(row, 2).toString());
		
	}
	private void putDates() {
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.setRowCount(0);
		Connection con = null;
		try {
			con = dbUtil.getConnection();
			ResultSet list = bookTypeDao.list(con, new BookType());
			while (list.next()) {
				Vector<java.io.Serializable> rowData = new Vector<>();
				rowData.add(list.getInt("id"));
				rowData.add(list.getString("type_name"));
				rowData.add(list.getString("remark"));
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
		//new AdminBTypeEdit();
	}
}
