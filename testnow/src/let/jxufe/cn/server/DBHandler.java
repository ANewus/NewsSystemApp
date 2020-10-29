package let.jxufe.cn.server;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
public class DBHandler {
//	String url = "jdbc:mysql://localhost:3306/test?useSSL=false";
	String url = "jdbc:sqlserver://localhost:1435;DatabaseName=test";
	String user="sa";
	String psd="123456";
	private Connection conn;
	private PreparedStatement ps;
	private ResultSet rs;
	public Connection getConn(){
		try{
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			conn = DriverManager.getConnection(url, user, psd);
			System.out.println("链接成功");
		}catch(Exception ex){
			ex.printStackTrace();
		}		
		return conn;
	}
	public String query(String sql,String[] args){
		String result="";
		try{
			conn=getConn();
			System.out.println(sql);
			ps=conn.prepareStatement(sql);
//			System.out.println(ps);
			for(int i=0;i<args.length;i++){
				ps.setString(i+1, args[i]);
			}			
			rs=ps.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();			
			int count=rsmd.getColumnCount();
			System.out.println(count);
			while (rs.next()) {
				for(int i=1;i<=count;i++){
					result+=rs.getString(i)+"*";
				}				
			}
			System.out.println(result);
		}catch (Exception ex) {
			ex.printStackTrace();
		}		
		return result;		
	}
	public boolean insert(String sql,String[] args){
		boolean flag=false;
		try{
			conn=getConn();
			System.out.println(sql);
			ps=conn.prepareStatement(sql);
			for(int i=0;i<args.length;i++){
				ps.setString(i+1, args[i]);
			}			
			int i=ps.executeUpdate();
			System.out.println(i);
			if(i==1){
				flag=true;
			}
		}catch (Exception ex) {
			ex.printStackTrace();
		}		
		return flag;
	}
	public boolean checkUser(String sql,String[] args){
		boolean flag=false;
		try{
			conn=getConn();
			ps=conn.prepareStatement(sql);
			for(int i=0;i<args.length;i++){
				ps.setString(i+1, args[i]);
			}			
			rs=ps.executeQuery();
			if(rs.next()){
				flag=true;
			}
		}catch (Exception ex) {
			ex.printStackTrace();
		}		
		return flag;
	}
//	public static void main(String[] args){//���ڲ������ݿ��Ƿ����ӳɹ�
//		DBHandler dbh=new DBHandler();
//		String result=dbh.query(
//				"select * from book where book_name like ? or book_author like ?",
//				new String[] { "%����%", "%����%" });
//		System.out.println(result);
////		boolean b=dbh.checkUser("select * from user where user_name=? and user_psd=?",new String[]{"gao","cheng"} );
////		System.out.println(b);
//	}
}
