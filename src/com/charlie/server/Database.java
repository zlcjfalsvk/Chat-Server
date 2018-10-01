package com.charlie.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

import com.charlie.vo.MessageVO;

public class Database {
	Logger logger = Logger.getLogger(Database.class.getName());
	Connection conn = null;
	Statement st = null;

	public Database() throws Exception {
		Config conf = new Config();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://" + conf.getJDBC_MAIN_SERVER() + ":" + conf.getJDBC_MAIN_PORT() + "/"
					+ conf.getJDBC_MAIN_DBNAME() + "?useUnicode=" + conf.isJDBC_MAIN_UNICODE() + "&characterEncoding="
					+ conf.getJDBC_MAIN_ENCODING();
			conn = DriverManager.getConnection(url, conf.getJDBC_MAIN_USER(), conf.getJDBC_MAIN_PASSWORD());
			st = conn.createStatement();

		} catch (Exception e) {
			// TODO: handle exception
			logger.error("DB연결에 실패하였습니다.\n" + e.getMessage(), e);
			throw new Exception("DB연결에 실패하였습니다.");
		}

	}

	public boolean insert(MessageVO vo) throws SQLException {

		String sql = "insert into message values('" + vo.getSq() + "', '" + vo.getId() + "', '" + vo.getDate() + "', '"
				+ vo.getTime() + "', '" + vo.getMessage() + "');";

		int value = st.executeUpdate(sql);

		if (value == 1) {
			return true;
		} else {
			throw new SQLException("Falied Insert");
		}
	}

}
