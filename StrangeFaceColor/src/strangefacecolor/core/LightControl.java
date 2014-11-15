package strangefacecolor.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.ListIterator;

import com.philips.lighting.hue.sdk.PHAccessPoint;
import com.philips.lighting.hue.sdk.PHBridgeSearchManager;
import com.philips.lighting.hue.sdk.PHHueSDK;
import com.philips.lighting.hue.sdk.PHSDKListener;
import com.philips.lighting.model.PHBridge;
import com.philips.lighting.model.PHHueParsingError;



public class LightControl {

	private PHHueSDK phHueSDK;
	private static final int MAX_HUE = 65535;
	
	private LightControl instance;
	
	public LightControl(){
		this.phHueSDK = PHHueSDK.getInstance();
		this.phHueSDK.setAppName("StrangeFaceColor");
		this.phHueSDK.setDeviceName("Experimental-PC");
		this.phHueSDK.getNotificationManager().registerSDKListener(sdkListener);
		this.instance = this;
	}
	
	public void findBridge(PHHueSDK phHueSDK){
		PHBridgeSearchManager bridgeSearcher = (PHBridgeSearchManager) phHueSDK.getSDKService(PHHueSDK.SEARCH_BRIDGE); 
		bridgeSearcher.search(true, true);
	}
	
	private PHSDKListener sdkListener = new PHSDKListener(){

		@Override
		public void onAccessPointsFound(List<PHAccessPoint> accessList) {
			ListIterator<PHAccessPoint> accessIterator = accessList.listIterator();
			InputStreamReader isr = new InputStreamReader(System.in);
			BufferedReader br = new BufferedReader(isr);
			String strInput = "Input failed";
			int selectedBridge;
			
			System.out.println("Folgende Bridges wurden gefunden:");
		
			while (accessIterator.hasNext()){
				System.out.print(Integer.toString(accessIterator.nextIndex()+1)+": ");
				System.out.println(accessIterator.next());
			}
			System.out.print("\nBitte wählen Sie die Bridge aus mit der Sie sich verbinden möchten: ");
			try {
				strInput = br.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			selectedBridge = Integer.valueOf(strInput) - 1;
			accessList.get(selectedBridge).setUsername("StrangeFaceColor");
			phHueSDK.connect(accessList.get(selectedBridge));
		}

		@Override
		public void onAuthenticationRequired(PHAccessPoint accessPoint) {
			System.out.println("\nAuthentifizierung erfordert: Bitte drücken Sie innerhalb der nächsten 30s den Push-Button auf Ihrer Bridge!");
			phHueSDK.startPushlinkAuthentication(accessPoint);
			System.out.println("Verbinde...");
		}

		@Override
		public void onBridgeConnected(PHBridge bridge) {
			System.out.println("Erfolgreich verbunden!");
			phHueSDK.setSelectedBridge(bridge);
			phHueSDK.enableHeartbeat(bridge, PHHueSDK.HB_INTERVAL);
		}

		@Override
		public void onCacheUpdated(List<Integer> arg0, PHBridge arg1) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onConnectionLost(PHAccessPoint arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onConnectionResumed(PHBridge arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onError(int arg0, String arg1) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onParsingErrors(List<PHHueParsingError> arg0) {
			// TODO Auto-generated method stub
			
		}
		
	};
	
	public PHHueSDK getSDK(){
		return this.phHueSDK;
	}
	
}

