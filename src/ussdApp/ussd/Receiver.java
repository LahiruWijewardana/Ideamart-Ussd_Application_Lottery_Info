package ussdApp.ussd;

import ussdApp.util.Utility;
import ussdApp.mappers.MatchScoresMapper;
import ussdApp.mappers.UsersMapper;
import ussdApp.model.Users;
import ussdApp.webcontent.GetContent;
import hms.kite.samples.api.ussd.UssdRequestSender;
import hms.kite.samples.api.ussd.MoUssdListener;
import hms.kite.samples.api.ussd.messages.MoUssdReq;
import hms.kite.samples.api.ussd.messages.MtUssdReq;
import hms.kite.samples.api.SdpException;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;

public class Receiver implements MoUssdListener {

	private UssdRequestSender ussdMtSender;

	@Override
	public void init() {
		// TODO Auto-generated method stub
//		try {
//			ussdMtSender = new UssdRequestSender(new URL(Utility.USSD_URL));
//		} catch (MalformedURLException e) {
//			e.printStackTrace();
//		}

	}

	@Override
	public void onReceivedUssd(MoUssdReq ussdCode) {
		// TODO Auto-generated method stub
		System.out.println("this is reciever");
		
		GetContent getContent = new GetContent();
		
		try {
			getContent.getWebContent();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

//		String[] msisdnParts = ussdCode.getSourceAddress().split(":");
//		String msisdn = msisdnParts[1];
//
//		Users user = new Users();
//		user.setMsisdn(msisdn);
//
//		UsersMapper usersMapper = new UsersMapper();
//
//		try {
//
//			if (usersMapper.findUser(user)) {
//
//				user = usersMapper.getUser(user);
//				if ((user.getStatus() == 2)
//						&& Utility.USSD_OP_MO_INIT.equals(ussdCode.getUssdOperation()) && ussdCode.getMessage().equalsIgnoreCase("2")) {
//
//					String returnMessage = "Thank you for subcribing. Try again later";
//					
//					Operations operations = new Operations();
//					MtUssdReq request = operations.createRequest(ussdCode, returnMessage, Utility.USSD_OP_MT_CONT);
//					operations.sendRequest(request, ussdMtSender);
//
//					
//				} else if (((user.getType().equalsIgnoreCase("1")) && (user.getStatus() == 2)
//						&& Utility.USSD_OP_MO_INIT.equals(ussdCode.getUssdOperation()) && ussdCode.getMessage().equalsIgnoreCase("1")) || ((user.getType().equalsIgnoreCase("1")) && (user.getStatus() == 2)
//								&& Utility.USSD_OP_MO_INIT.equals(ussdCode.getUssdOperation()) && ussdCode.getMessage().equalsIgnoreCase(Utility.USSD_CODE_INIT))) {
//
//					MatchScoresMapper matchScoresMapper = new MatchScoresMapper();
//
//					ArrayList<String> matchDetailList = matchScoresMapper.showMatches();
//					
//					for (int i = 0; i < matchDetailList.size(); i++) {
//						matchDetailList.set(i, i +". "+ matchDetailList.get(i));
//					}
//
//					String returnMessage = "Please select the match you want to view\n"
//							+ String.join("\n", matchDetailList);
//
//					Operations operations = new Operations();
//					MtUssdReq request = operations.createRequest(ussdCode, returnMessage, Utility.USSD_OP_MT_CONT);
//					operations.sendRequest(request, ussdMtSender);
//					
//					user.setStatus(3);
//					usersMapper.updateUserStatus(user);
//					
//				} else if ((user.getType().equalsIgnoreCase("1")) && (user.getStatus() == 3)
//						&& Utility.USSD_OP_MO_INIT.equals(ussdCode.getUssdOperation())) {
//					
//					String message = ussdCode.getMessage();
//					
//					MatchScoresMapper matchScoresMapper = new MatchScoresMapper();
//					ArrayList<String> matchDetailList = matchScoresMapper.showMatches();
//					
//					String matchDetail = matchDetailList.get(Integer.parseInt(message));
//					
//					String returnMessage = matchDetail +" : "+ matchScoresMapper.getScore(matchDetail);
//					
//					Operations operations = new Operations();
//					MtUssdReq request = operations.createRequest(ussdCode, returnMessage, Utility.USSD_OP_MT_CONT);
//					operations.sendRequest(request, ussdMtSender);
//
//					user.setStatus(2);
//					usersMapper.updateUserStatus(user);
//
//				} else if ((user.getType().equalsIgnoreCase("2")) && (ussdCode.getMessage().equalsIgnoreCase("1"))) {
//					
//					usersMapper.subcribeUser(user);
//					
//					String returnMessage = "Succesfully subcribed\n 1. Get Score\n 2.Quit";
//					
//					Operations operations = new Operations();
//					MtUssdReq request = operations.createRequest(ussdCode, returnMessage, Utility.USSD_OP_MT_CONT);
//					operations.sendRequest(request, ussdMtSender);
//
//					user.setStatus(2);
//					usersMapper.updateUserStatus(user);
//					
//				} else if ((user.getType().equalsIgnoreCase("2")) && (ussdCode.getMessage().equalsIgnoreCase("2"))) {
//					
//					String returnMessage = "Thank you. Try again later";
//					
//					Operations operations = new Operations();
//					MtUssdReq request = operations.createRequest(ussdCode, returnMessage, Utility.USSD_OP_MT_CONT);
//					operations.sendRequest(request, ussdMtSender);
//
//					user.setStatus(0);
//					usersMapper.updateUserStatus(user);
//					
//				}else if (ussdCode.getMessage().equalsIgnoreCase(Utility.USSD_CODE_INIT)
//						&& Utility.USSD_OP_MO_INIT.equals(ussdCode.getUssdOperation())) {
//					
//					String returnMessage = "Please Subcribe with CricInfo service. Do you want to subcribe.\n 1.Yes\n 2.No";
//
//					Operations operations = new Operations();
//					MtUssdReq request = operations.createRequest(ussdCode, returnMessage, Utility.USSD_OP_MT_CONT);
//					operations.sendRequest(request, ussdMtSender);
//
//					user.setStatus(1);
//					usersMapper.updateUserStatus(user);
//					
//				} else {
//					String returnMessage = "Invalid USSD Code";
//
//					Operations operations = new Operations();
//					MtUssdReq request = operations.createRequest(ussdCode, returnMessage, Utility.USSD_OP_MT_CONT);
//					operations.sendRequest(request, ussdMtSender);
//				}
//			
//
//			} else if (ussdCode.getMessage().equalsIgnoreCase(Utility.USSD_CODE_INIT)
//					&& Utility.USSD_OP_MO_INIT.equals(ussdCode.getUssdOperation())) {
//				
//				user.setType("2");
//				usersMapper.addUser(user);
//
//				String returnMessage = "Please Subcribe with CricInfo service. Do you want to subcribe.\n 1.Yes\n 2.No";
//
//				Operations operations = new Operations();
//				MtUssdReq request = operations.createRequest(ussdCode, returnMessage, Utility.USSD_OP_MT_CONT);
//				operations.sendRequest(request, ussdMtSender);
//
//				user.setStatus(1);
//				usersMapper.updateUserStatus(user);
//			} else {
//				String returnMessage = "Invalid USSD Code";
//
//				Operations operations = new Operations();
//				MtUssdReq request = operations.createRequest(ussdCode, returnMessage, Utility.USSD_OP_MT_CONT);
//				operations.sendRequest(request, ussdMtSender);
//			}
//
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (SdpException e) {
//			e.printStackTrace();
//		}

	}

}
