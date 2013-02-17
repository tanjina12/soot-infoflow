/**
    @author Benjamin Livshits <livshits@cs.stanford.edu>
    
    $Id: Basic19.java,v 1.7 2006/04/04 20:00:40 livshits Exp $
 */
package securibench.micro.basic;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import securibench.micro.BasicTestCase;
import securibench.micro.MicroTestCase;

/** 
 *  @servlet description="simple SQL injection with prepared statements" 
 *  @servlet vuln_count = "1" 
 *  */
public class Basic19 extends BasicTestCase implements MicroTestCase {
    private static final String FIELD_NAME = "name";

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String name = req.getParameter(FIELD_NAME);
        
        Connection con = null; 
        try {
            con = DriverManager.getConnection(MicroTestCase.CONNECTION_STRING);
            con.prepareStatement("select * from Users where name=" + name); /* BAD */
        } catch (SQLException e) {
            System.err.println("An error occurred");
        } finally {
            try {
                if(con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    public String getDescription() {
        return "simple SQL injection with prepared statements";
    }
    
    public int getVulnerabilityCount() {
        return 1;
    }
}