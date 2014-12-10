package org.seerc.brokeratcloud.messagebroker;

import java.util.ArrayList;
import java.util.List;

import org.wso2.carbon.registry.core.exceptions.RegistryException;

public class OutOfRangeSLAViolationChecker{

	private WSO2MBClient mb; 
	private List<OutOfRangeSLAViolationSubscriber> qvTopicSubscribers;
	
	public OutOfRangeSLAViolationChecker()
	{
		mb = new WSO2MBClient();
		qvTopicSubscribers = new ArrayList<OutOfRangeSLAViolationSubscriber>();
		
		/*
		 * 1) Subscribe to all monitoringTopics
		 * 2) The listener of those subscribers should check the payload for out-of-range.
		 *    If it is found out of range, it should send the message to the error topic. 
		 */
		
		try {
			List<String> allTopics = mb.getAllTopics();
			for(String topicName:allTopics)
			{
				if(topicName.startsWith(WSO2MBClient.monitoringTopicPrefix))
				{ // monitoring Topic
					String subscriberName = "subscriberFor_" + topicName;
					OutOfRangeSLAViolationSubscriber qvSubscriber = new OutOfRangeSLAViolationSubscriber(subscriberName, topicName);
					qvTopicSubscribers.add(qvSubscriber);
					qvSubscriber.subscribeToTopic();
					System.out.println("Created QV out of range subscriber " + subscriberName);
				}
			}
			
		} catch (RegistryException e) {
			e.printStackTrace();
		}
		
		// cleanup on JVM shutdown - will only run when running from command-line, NOT from within IDE.
		Runtime.getRuntime().addShutdownHook(new Thread() {

		    @Override
		    public void run() {
		    	for(OutOfRangeSLAViolationSubscriber mbs:qvTopicSubscribers)
		    	{
		    		mbs.releaseResources();
		    	}
		    }

		});		
	}

	public static void main(String[] args) {
		new OutOfRangeSLAViolationChecker();
	}
}
