import java.awt.EventQueue;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.io.IOUtils;

import javax.swing.JTextPane;



public class ro {

	private JFrame frame;
	private JTextField textField_1;
	private JLabel label;
	private JTextArea textPane;
	private JPanel jp;
	private final static Executor executor = Executors.newCachedThreadPool();//启用多线程

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ro window = new ro();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws IOException 
	 */
	public ro() throws IOException {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws IOException 
	 */
	private void initialize() throws IOException {
		frame = new JFrame();
		frame.setBounds(100, 100, 633, 594);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("RO提取玩家ID号");
		lblNewLabel.setBounds(24, 102, 132, 29);
		frame.getContentPane().add(lblNewLabel);

 		 
 		
		textField_1 = new JTextField();
		textField_1.setText("请输入玩家名称");
		textField_1.setBounds(170, 88, 283, 60);
		frame.getContentPane().add(textField_1);
		
 		
		JButton btnNewButton = new JButton("提取礼包");
		 btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String playName=textField_1.getText();
				String result = null;
				String codeList="FMT|MILKTEA|SUSU|JTKJZ|BIAOGE|CFCRAZY|GJWD|JLASX|DCK|CNBOY|RUOFENG|CPCDHL|WXS|qxd|CAOMEI|CHUNHEI|LFQ|SQLFR|7hao|SXZB|lexburner|NAI|SUFEI|XZJET|ADOU|NJXW|SJJ|XIAOXIAO|DBJ";
				String code[] = codeList.split("\\|");
				String name;
				try {
					name = java.net.URLEncoder.encode(playName, "utf-8");
				for (String c : code){
					 StringBuffer params = new StringBuffer();
					   params.append("callback=Query1102009803182119503617_"+System.currentTimeMillis());
					   params.append("&");
					   params.append("name="+name);
					   params.append("&");
					   params.append("code="+c);
					   params.append("&");
					   params.append("_="+System.currentTimeMillis());
					   executor.execute(new Runnable() {
						
						@Override
						public void run() {
							String abc = post(params);
							textPane.append(abc+"\n\r");

						}
					   });
						
				}} catch (UnsupportedEncodingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} 
				
		}
		});
		btnNewButton.setBounds(222, 453, 126, 36);
		frame.getContentPane().add(btnNewButton);
		
		label = new JLabel("礼包类型");
		label.setBounds(50, 210, 106, 18);
		frame.getContentPane().add(label);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"RO集合礼包1"}));
		comboBox.setSelectedIndex(0);
		comboBox.setBounds(170, 195, 283, 50);
		frame.getContentPane().add(comboBox);
		
		textPane = new JTextArea();
 		textPane.setBounds(66, 258, 410, 158);
 		textPane.setLineWrap(true);
 		frame.getContentPane().add(textPane);
		
	}
	
	
	public static String post(StringBuffer params){
		String result = null;
		try{
			   HttpClient client = new HttpClient();
			   client.getHttpConnectionManager().getParams().setConnectionTimeout(10000);
			   client.getHttpConnectionManager().getParams().setSoTimeout(10000);
			   System.out.println("https://party.xd.com/event/2017jana/ajax_submit"+"?"+params);
			   GetMethod getMethod = new GetMethod("https://party.xd.com/event/2017jana/ajax_submit"+"?"+params);
			   getMethod.getParams().setContentCharset("UTF-8");
			   getMethod.addRequestHeader("Content-Type", "text/html"); 
			   int status;
				status = client.executeMethod(getMethod);
				   /** 通知成功*/
			  	   if (status == HttpStatus.SC_OK) {
			  		   InputStream json = getMethod.getResponseBodyAsStream();
//			  		   System.out.println(ro.unicode2string(IOUtils.toString(json, "UTF-8")));
			  		   result = ro.unicode2string(IOUtils.toString(json, "UTF-8"));
			  	   }
				Thread.sleep(3000);
		} catch (Exception e1){
			e1.printStackTrace();
		}
		return result;
	}
	
	
	public static String unicode2string(String s) {
        List<String> list =new ArrayList<String>();
        String zz="\\\\u[0-9,a-z,A-Z]{4}";
         
        //正则表达式用法参考API
        Pattern pattern = Pattern.compile(zz);
        Matcher m = pattern.matcher(s);
        while(m.find()){
            list.add(m.group());
        }
        for(int i=0,j=2;i<list.size();i++){
            String st = list.get(i).substring(j, j+4);
             
            //将得到的数值按照16进制解析为十进制整数，再強转为字符
            char ch = (char) Integer.parseInt(st, 16);
            //用得到的字符替换编码表达式
            s = s.replace(list.get(i), String.valueOf(ch));
        }
        return s;
    }
}
