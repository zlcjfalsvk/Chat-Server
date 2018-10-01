package com.charlie.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;

import com.charlie.vo.MessageVO;

class ServerReceiver extends Thread {

	Logger logger = Logger.getLogger(ServerReceiver.class.getName());
	Socket socket;
	DataInputStream in;
	DataOutputStream out;
	Database db;
	Map<String, DataOutputStream> clients;

	ServerReceiver(Socket socket, Map<String, DataOutputStream> clients, Database db) throws Exception {

		this.socket = socket;
		this.clients = clients;

		try {
			this.db = db;
			this.in = new DataInputStream(socket.getInputStream());
			this.out = new DataOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			throw new IOException("소켓의 정보가 맞지 않습니다.");
		}
	}

	public void run() {
		String name = "";
		try {
			name = in.readUTF();
			sendToAll("#" + name + "님이 들어오셨습니다.");

			this.clients.put(name, out);
			logger.info("log4j: 현재 서버접속자 수는 " + clients.size() + "입니다.");

			while (in != null) {
				String msg = in.readUTF();
				this.saveDB(msg);
				this.sendToAll(msg);
			}

		} catch (IOException e) {
		} finally {

			try {
				sendToAll("#" + name + "님이 나가셨습니다.");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// 대화방에서 객체 삭제
			this.clients.remove(name);
			logger.info("log4j: [" + socket.getInetAddress() //
					+ ":" + socket.getPort() + "]" + "에서 접속을 종료하였습니다.");
			logger.info("현재 서버접속자 수는 " + clients.size() + "입니다.");
		}
	}

	void sendToAll(String msg) throws IOException {

		Iterator<String> it = clients.keySet().iterator();

		while (it.hasNext()) {
			try {
				String name = it.next();
				DataOutputStream out = clients.get(name);
				out.writeUTF(msg);
			} catch (IOException e) {
				logger.info("log4j: Client와 접속이 끊어졌습니다.");
				throw new IOException("Client와 접속이 끊어졌습니다.");
			}
		}
	}

	void saveDB(String msg) {

		String name = msg.substring(1, msg.indexOf("]"));
		String message = msg.substring(msg.indexOf("]") + 1);

		SimpleDateFormat dateFormat, timeFormat;
		Date date = new Date();
		dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		timeFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:dd");
		MessageVO vo = new MessageVO(0, name, dateFormat.format(date), timeFormat.format(date), message);

		try {
			db.insert(vo);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}