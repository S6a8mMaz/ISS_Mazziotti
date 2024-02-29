package main;
import unibo.basicomm23.utils.BasicMsgUtil;
import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapResponse;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.utils.CommUtils;

public class ServiceCallerCoapNaive {
/*1*/private final String destination = "servicemath";
/*2*/private final String sender      = "clientcoapjava";
/*3*/private final String hostAddr    = "localhost";
/*4*/private final int    port        = 8011;
/*5*/private final String msgid       = "dofibo";
/*6*/private final String msgcontent  = "dofibo(28)";

    private CoapClient client;
    private String url;
/*7*/private String path = "ctxservice/"+destination;
    private CoapResponse response;

    public void doJob() {
        try {
/*8*/  IApplMessage req =
        BasicMsgUtil.buildRequest(sender,msgid,msgcontent,destination);
          String answer = sendUsingCoap( req  );
          if( answer != null )
             CommUtils.outblue(
                "ServiceCallerCoapNaive|answer="+answer);
        }catch(Exception e){
          CommUtils.outred("ERROR " + e.getMessage() );
        }
    }

    protected String sendUsingCoap( IApplMessage req  ) {
      try {
        url  = "coap://"+hostAddr+":"+port + "/"+ path;
/*9*/   client  = new CoapClient( url );
/*10*/  response=
          client.put(req.toString(),MediaTypeRegistry.TEXT_PLAIN);
          return getAnswer(response );
      }catch(Exception e){
        CommUtils.outred("ERROR " + e.getMessage() );
      }
      return "";
    }

    protected String getAnswer( CoapResponse response ) {
      if( response != null ) {
              CommUtils.outblue(
        "ServerCallerCoapNaive|response="+response);//ACK-2.05 ...
/*11*/  String answer = response.getResponseText();
        return answer;
      }else {
        CommUtils.outred("ServiceCallerCoapNaive RESPONSE NULL");
        return null;
      }
    }

    public static void main( String[] args) {
      new ServiceCallerCoapNaive().doJob();
      CommUtils.outyellow("sendUsingCoap BYE "  );
    }
}