package com.code.Remote;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import android.util.Log;

public class androidsocket {
	String IP;
	int port;
	int choose;

	public androidsocket(String IP, int port, int choose) {
		this.IP = IP;
		this.port = port;
		this.choose = choose;
		sendcocket();

	}

	public String getIP() {
		return IP;
	}

	public int getport() {
		return port;
	}

	public int getchoose() {
		return choose;
	}

	public static String getdiredata(int dierchoose) {
		String diredata;
		switch (dierchoose) {
		case 1:
			diredata = new String("@psi#" + main_fragment.Zangle + "#"
					+ main_fragment.Xangle + "#" + main_fragment.Yangle + "$");

			break;
		case 2:
			diredata = new String("@rel#" + main_fragment.Zangle + "#"
					+ main_fragment.Xangle + "#" + main_fragment.Yangle + "$");
			break;
		case 3:
			diredata = new String("@key#ML_D$");
			break;
		case 4:
			diredata = new String("@key#ML_U$");
			break;
		case 5:
			diredata = new String("@key#ML_C$");
			break;
		case 6:
			diredata = new String("@key#MR_D$");
			break;
		case 7:
			diredata = new String("@key#MR_U$");
			break;
		case 8:
			diredata = new String("@key#MR_C$");
			break;
		case 9:
			diredata = new String("@rgr#" + main_fragment.myIP + "$");
			break;
		case 10:
			diredata = new String("@thb#" + mk_fragment.sendx + "#"
					+ mk_fragment.sendy + "$");
			break;
		case 11:
			diredata = new String("@thb#s" + "$");
			break;
		case 12:
			diredata = new String("@thb#x" + "$");
			break;
		case 13:
			diredata = new String("@key#Space$");
			break;
		case 14:
			diredata = new String("@key#forward$");
			break;
		case 15:
			diredata = new String("@key#back$");
			break;
		default:
			diredata = new String("error");
			break;
		}
		return diredata;
	}

	public void sendcocket() {
		try {

			DatagramSocket phonesocket = new DatagramSocket();
			InetAddress computeraddress = InetAddress.getByName(IP);
			String diredata = getdiredata(choose);
			byte sendbyte[] = diredata.getBytes();
			DatagramPacket sendpacket = new DatagramPacket(sendbyte,
					sendbyte.length, computeraddress, port);
			phonesocket.send(sendpacket);
			phonesocket.close();
		} catch (SocketException | UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
