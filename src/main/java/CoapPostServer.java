import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.CoapServer;
import static org.eclipse.californium.core.coap.CoAP.ResponseCode.CHANGED;
import static org.eclipse.californium.core.coap.CoAP.ResponseCode.CREATED;
import static org.eclipse.californium.core.coap.CoAP.ResponseCode.DELETED;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.eclipse.californium.core.server.resources.CoapExchange;

/**
 * @author ebruno
 */
public class CoapPostServer extends CoapResource {

    public static void main(String[] args) {
        CoapServer server = new CoapServer();
        server.add(new CoapPostServer("data"));
        server.start();
    }

    public CoapPostServer(String name) {
        super(name);
    }
    
    @Override
    public void handleGET(CoapExchange exchange) {
        exchange.respond("hello world");
    }

    @Override
    public void handlePOST(CoapExchange exchange) {
        exchange.accept();

        int format = exchange.getRequestOptions().getContentFormat();
        if (format == MediaTypeRegistry.APPLICATION_XML) {
            String xml = exchange.getRequestText();
            String responseTxt = "Received XML: '" + xml + "'";
            System.out.println(responseTxt);
            exchange.respond(CREATED, responseTxt);

        } 
        else if (format == MediaTypeRegistry.TEXT_PLAIN) {
            // ...
            String plain = exchange.getRequestText();
            String responseTxt = "Received text: '" + plain + "'";
            System.out.println(responseTxt);
            exchange.respond(CREATED, responseTxt );
        }
        else {
            // ...
            byte[] bytes = exchange.getRequestPayload();
            System.out.println("Received bytes: " + bytes);
            exchange.respond(CREATED);
        }
    }

    @Override
    public void handlePUT(CoapExchange exchange) {
        // ...
        exchange.respond(CHANGED);
    }

    @Override
    public void handleDELETE(CoapExchange exchange) {
        delete();
        exchange.respond(DELETED);
    }
}
