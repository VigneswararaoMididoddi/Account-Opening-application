import java.sql.*;
import javax.servlet.*;
import java.io.*;
public class AccountOpeningServlet extends GenericServlet
{
  Connection con;
  PreparedStatement ps;
  String responsepage="success.html";
  public void init(ServletConfig config)
  {
    try
    {
      Class.forName("oracle.jdbc.driver.OracleDriver");
		  con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","System","123456");
      ps=con.prepareStatement("insert into account values(?,?,?)");
    }
    catch(SQLException e)
    {
      e.printStackTrace();
    }
    catch(ClassNotFoundException e)
    {
      e.printStackTrace();
    }
  }
  public void service(ServletRequest req,ServletResponse res)throws ServletException,IOException
    {
      int accno=Integer.parseInt(req.getParameter("accno"));
      String name=req.getParameter("name");
      float bal=Float.parseFloat(req.getParameter("bal"));
      try {
        ps.setInt(1,accno);
        ps.setString(2,name);
        ps.setFloat(3,bal);
        ps.executeUpdate();
        responsepage="success.html";
      }
      catch (SQLException e) {
        e.printStackTrace();
        responsepage="failure.html";
      }
      catch (Exception e) {
        e.printStackTrace();
        responsepage="failure.html";
      }
    }
    public void destroy()
    {
      try
      {
        ps.close();
        con.close();
      }
      catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
