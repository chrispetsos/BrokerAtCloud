package org.seerc.brokeratcloud.messagebroker;

public class SDDeprecateListener extends AbstractSDLifecycleListener {

	@Override
	protected void performLifecycleEventForService(String serviceID) {
		slp.serviceDeprecated(serviceID);
	}
}
