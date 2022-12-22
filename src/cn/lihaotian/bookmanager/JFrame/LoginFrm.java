package cn.lihaotian.bookmanager.JFrame;

import cn.lihaotian.bookmanager.dao.UserDao;
import cn.lihaotian.bookmanager.model.User;
import cn.lihaotian.bookmanager.utils.DbUtil;
import cn.lihaotian.bookmanager.utils.toolUtil;
import com.mysql.jdbc.Connection;
import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class LoginFrm extends JFrame {
	public static User currentUser;
	private final JFrame jf;
	private final JTextField userNameText;
	private final JTextField passwordText;
	private final JComboBox<String> comboBox;
	
	UserDao userDao = new UserDao();
	DbUtil dbUtil = new DbUtil();
	public LoginFrm(){
				
		jf=new JFrame("图书の管理");
		jf.getContentPane().setFont(new Font("幼圆", Font.BOLD, 14));
		jf.setBounds(600, 250, 500, 467);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.getContentPane().setLayout(null);

		
		JLabel lblNewLabel = new JLabel(new ImageIcon(Objects.requireNonNull(LoginFrm.class.getResource("/tupian/bg2.png"))));
		lblNewLabel.setBounds(24, 10, 430, 218);
		jf.getContentPane().add(lblNewLabel);
		
		JLabel label = new JLabel("用户名：");
		label.setFont(new Font("幼圆", Font.BOLD, 14));
		label.setBounds(129, 250, 60, 29);
		jf.getContentPane().add(label);
		
		userNameText = new JTextField();
		userNameText.setBounds(199, 252, 127, 25);
		jf.getContentPane().add(userNameText);
		userNameText.setColumns(10);
		
		JLabel label_1 = new JLabel("密码：");
		label_1.setFont(new Font("幼圆", Font.BOLD, 14));
		label_1.setBounds(144, 289, 45, 29);
		jf.getContentPane().add(label_1);
		
		passwordText = new JPasswordField();
		passwordText.setColumns(10);
		passwordText.setBounds(199, 291, 127, 25);
		jf.getContentPane().add(passwordText);
		
		JLabel label_2 = new JLabel("权限：");
		label_2.setFont(new Font("幼圆", Font.BOLD, 14));
		label_2.setBounds(144, 328, 45, 29);
		jf.getContentPane().add(label_2);
		
		comboBox = new JComboBox<>();
		comboBox.setBounds(199, 332, 127, 25);
		comboBox.addItem("用户");
		comboBox.addItem("管理员");
		jf.getContentPane().add(comboBox);
		
		JButton button = new JButton("登录");
		button.addActionListener(e -> checkLogin());
		button.setBounds(153, 377, 65, 29);
		jf.getContentPane().add(button);
		
		JButton button_1 = new JButton("注册");
		button_1.addActionListener(e -> regUser());
		button_1.setBounds(263, 377, 65, 29);
		jf.getContentPane().add(button_1);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon(Objects.requireNonNull(LoginFrm.class.getResource("/tupian/adBG3.png"))));
		lblNewLabel_1.setBounds(0, 0, 484, 429);
		jf.getContentPane().add(lblNewLabel_1);
		
		jf.setVisible(true);
		jf.setResizable(true);
		
	}
	protected void regUser() {
		jf.setVisible(false);
		new RegFrm();
		
	}
	protected void checkLogin() {
		String userName = userNameText.getText();
		String password = passwordText.getText();
		int index = comboBox.getSelectedIndex();
		if (toolUtil.isEmpty(userName) || toolUtil.isEmpty(password)) {
			JOptionPane.showMessageDialog(null, "用户名和密码不能为空");
			return;
		}
		User user = new User();
		user.setUserName(userName);
		user.setPassword(password);
		if (index == 0) {
			user.setRole(1);
		} else {
			user.setRole(2);
		}
		Connection con = null;
		try {
			con = dbUtil.getConnection();
			User login = userDao.login(con, user);
			currentUser = login;
			if (login == null) {
				JOptionPane.showMessageDialog(null, "登录失败");
			} else {
				// 权限 1普通 2管理员
				jf.dispose();
				if (index == 0) {
					// 学生
					new UserMenuFrm();
				} else {
					// 管理员
					new AdminMenuFrm();
				}
			}
		} catch (Exception e21) {

			e21.printStackTrace();
			JOptionPane.showMessageDialog(null, "登录异常");
		} finally {
			try {
				dbUtil.closeCon(con);
			} catch (Exception e31) {

				e31.printStackTrace();
			}
		}
		
	}
	public static void main(String[] args) {
		try {
			BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.generalNoTranslucencyShadow;
			org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
			UIManager.put("RootPane.setupButtonVisible",false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		new LoginFrm();
	}
}
