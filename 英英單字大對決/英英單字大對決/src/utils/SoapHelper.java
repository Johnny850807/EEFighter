package utils;

import java.util.Map;
import java.util.jar.Attributes.Name;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.stream.events.Namespace;

/**
 * @author Waterball
 * A SOAP helper used for sending any soap requests with specified properties.
 */
public class SoapHelper {
	public static final String NAMESPACE = "NAMESPACE";
	public static final String NAMESPACE_URL = "NAMESPACEURL";
	
	/**
	 * @param properties all key-value properties needed added to the request, as well as you can particularly add a namespace property with a URL using the constant NAMESPACE and NAMESPACE_URL as keys.
	 * @param soapEndpointUrl the endpoint where the SOAP servers location.
	 * @param soapAction the action of the request.
	 * @param print the tracing text will be output into the System.out if true.
	 */
	public static SOAPMessage callSoapWebService(Map<String, String> properties, String soapEndpointUrl, String soapAction, boolean print) {
		SOAPConnection soapConnection = null;
		SOAPMessage soaprequest = null;
		SOAPMessage soapResponse = null;
		try {
            SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
            soapConnection = soapConnectionFactory.createConnection();
            soaprequest = createSOAPRequest(properties, soapAction, print);
            soapResponse = soapConnection.call(soaprequest, soapEndpointUrl);
            if (print)
            {
            	System.out.println("Response SOAP Message:");
                soapResponse.writeTo(System.out);
                System.out.println();
            }
        } catch (Exception e) {
            System.err.println("\nError occurred while sending SOAP Request to Server!\nMake sure you have the correct endpoint URL and SOAPAction!\n");
            e.printStackTrace();
        } finally {
			try {
				if (soapConnection != null)
					soapConnection.close();
			} catch (SOAPException e) {
				e.printStackTrace();
			}
		}
		return soapResponse;
    }

    private static SOAPMessage createSOAPRequest(Map<String, String> properties, String soapAction, boolean print) throws Exception {
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();

        createSoapEnvelope(properties, soapMessage);

        MimeHeaders headers = soapMessage.getMimeHeaders();
        headers.addHeader("SOAPAction", soapAction);

        soapMessage.saveChanges();

        if (print)
        {
        	System.out.println("Request SOAP Message:");
            soapMessage.writeTo(System.out);
            System.out.println("\n");
        }
        
        return soapMessage;
    }
    
    private static void createSoapEnvelope(Map<String, String> properties, SOAPMessage soapMessage) throws SOAPException {
        SOAPPart soapPart = soapMessage.getSOAPPart();

        String myNamespace = properties.get(NAMESPACE);
        String myNamespaceURI = properties.get(NAMESPACE_URL);

        SOAPEnvelope envelope = soapPart.getEnvelope();
        envelope.addNamespaceDeclaration(myNamespace, myNamespaceURI);

        SOAPBody soapBody = envelope.getBody();
        SOAPElement soapBodyElem = soapBody.addChildElement(myNamespace, myNamespace);
        
        for (String key : properties.keySet())
        	soapBodyElem.addChildElement(key, myNamespace).addTextNode(properties.get(key));
    }
}
