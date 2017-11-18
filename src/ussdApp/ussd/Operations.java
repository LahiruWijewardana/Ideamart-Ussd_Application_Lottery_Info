package ussdApp.ussd;

import hms.kite.samples.api.SdpException;
import hms.kite.samples.api.StatusCodes;
import hms.kite.samples.api.ussd.UssdRequestSender;
import hms.kite.samples.api.ussd.messages.MoUssdReq;
import hms.kite.samples.api.ussd.messages.MtUssdReq;
import hms.kite.samples.api.ussd.messages.MtUssdResp;
import ussdApp.util.Utility;

public class Operations {
	
	public MtUssdReq createRequest(MoUssdReq moUssdReq, String menuContent, String ussdOperation) {

        MtUssdReq request = new MtUssdReq();
        request.setApplicationId(moUssdReq.getApplicationId());
        request.setEncoding(moUssdReq.getEncoding());
        request.setMessage(menuContent);
        request.setPassword(Utility.USSD_PASSWORD);
        request.setSessionId(moUssdReq.getSessionId());
        request.setUssdOperation(ussdOperation);
        request.setVersion(moUssdReq.getVersion());
        request.setDestinationAddress(moUssdReq.getSourceAddress());
        return request;
    }

    public MtUssdResp sendRequest(MtUssdReq request, UssdRequestSender ussdMtSender) throws SdpException {
        // sending request to service
        MtUssdResp response = null;
        try {
            System.out.println();
            response = ussdMtSender.sendUssdRequest(request);
        } catch (SdpException e) {
            throw e;
        }

        // response status
        String statusCode = response.getStatusCode();
        
        if (StatusCodes.SuccessK.equals(statusCode)) {
            System.out.println("Message sent succeeded");
        } else {
            System.out.println("Message sent failed");
        }
        return response;
    }

}
