package strangefacecolor.core;

import com.philips.lighting.hue.sdk.PHHueSDK;

public class Application {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		LightControl expLights = new LightControl();
		PHHueSDK phHueSDK = expLights.getSDK();
		expLights.findBridge(phHueSDK);
		
	}

}
