package com.charlie.server;

import java.io.*;
import java.net.*;
//import java.sql.SQLException;
//import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.log4j.Logger;

//import com.vo.MessageVO;

public class Server {

	Map<String, DataOutputStream> clients;
	Database db;
	Config conf;
	Logger logger = Logger.getLogger(Server.class.getName());

	Server() throws Exception {
		clients = Collections.synchronizedMap( //
				new HashMap<String, DataOutputStream>());

		db = new Database();
	}

	public void start() {
		ServerSocket serverSocket = null;
		Socket socket = null;

		try {
			conf = new Config();
			serverSocket = new ServerSocket(conf.getCONNECT_PORT());
			logger.info("log4j: 서버가 시작되었습니다.");

			while (true) {
				socket = serverSocket.accept();
				logger.info("log4j: [" + socket.getInetAddress() //
						+ ":" + socket.getPort() + "]" + "에서 접속하였습니다.");

				ServerReceiver thread = new ServerReceiver(socket, this.clients, this.db);
				thread.start();

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SocketUtil.close(serverSocket);
		}
	}

	public static void main(String[] args) throws Exception {
		new Server().start();
	}

}
