package db;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import dto.Status;

public class DBConnector {
	
	private final String JDBC_DRIVER = "org.mariadb.jdbc.Driver"; // 드라이버
	private final String DB_URL = "jdbc:mariadb://localhost:3307/corona"; // 접속할 DB 서버

	private final String USER_NAME = "root"; // DB에 접속할 사용자 이름을 상수로 정의
	private final String PASSWORD = "rla016";
	
	public DBConnector() {}
	
	public List<Status> getAll() {
		Connection conn = null;
		Statement state = null;
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
			state = conn.createStatement();
			// ... 위의 소스코드//

			String sql; // SQL문을 저장할 String
			sql = "select * from status order by quarantinedPatient desc,date desc;";
			ResultSet rs = state.executeQuery(sql); // SQL문을 전달하여 실행
			List<Status> result = new ArrayList<Status>();
			Status obj;
			while (rs.next()) {
				
				obj = Status.builder()
					.quarantinedPatient(Integer.parseInt(rs.getString("quarantinedPatient")))
					.deceasedPerson(Integer.parseInt(rs.getString("deceasedPerson")))
					.treatedPatient(Integer.parseInt(rs.getString("treatedPatient")))
					.inspecting(Integer.parseInt(rs.getString("inspecting")))
					.date(rs.getString("date"))
					.build();
				
				result.add(obj);

				
			}

			rs.close();
			state.close();
			conn.close();
			
			return result;
		} catch (Exception e) {
			System.out.println(e.toString());
			return null;

		} finally { // 예외가 있든 없든 무조건 실행
			try {
				if (state != null)
					state.close();
			} catch (SQLException ex1) {
				System.out.println(ex1.toString());
			}

			try {
				if (conn != null)
					conn.close();
			} catch (SQLException ex1) {
				System.out.println(ex1.toString());
			}
		}

	}
	
	public boolean  insert(Status status) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
			// ... 위의 소스코드//

			String sql = "insert into "
					+ "status (quarantinedPatient, treatedPatient, deceasedPerson, inspecting, date) "
					+ "values(?, ?, ?, ?, ?)";
			
			pstmt = conn.prepareStatement(sql); // 4. pstmt.set<데이터타입>(? 순서, 값) ex).setString(), .setInt ... 
			pstmt.setInt(1, status.getQuarantinedPatient()); 
			pstmt.setInt(2, status.getTreatedPatient()); 
			pstmt.setInt(3, status.getDeceasedPerson()); 
			pstmt.setInt(4, status.getInspecting()); 
			pstmt.setString(5, status.getDate()); // 5. SQL 문장을 실행하고 결과를 리턴 - SQL 문장 실행 후, 변경된 row 수 int type 리턴 
			pstmt.executeUpdate();
			pstmt.close();
			conn.close();
			
			return true;

		} catch (Exception e) {
			System.out.println(e.toString());
			return false;

		} finally { // 예외가 있든 없든 무조건 실행
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException ex1) {
				System.out.println(ex1.toString());
			}

			try {
				if (conn != null)
					conn.close();
			} catch (SQLException ex1) {
				System.out.println(ex1.toString());
			}
		}

	}

}
