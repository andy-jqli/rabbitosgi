package com.rabbitmq.client.osgi.exchange;

import java.util.Properties;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.cm.ManagedServiceFactory;

import com.rabbitmq.client.osgi.common.LogTracker;

public class ExchangesActivator implements BundleActivator {

	private LogTracker logTracker;
	private ServiceRegistration connectionExchangeMsfReg;
	private ServiceRegistration endpointMsfReg;

	public void start(BundleContext context) throws Exception {
		Properties svcProps;
		
		//logTracker = new LogTracker(context);
		//logTracker.open();
		
		ConnectionExchangeMSF connectionExchangeMSF = new ConnectionExchangeMSF(context);
		svcProps = new Properties();
		svcProps.put(Constants.SERVICE_PID, "exchange");
		connectionExchangeMsfReg = context.registerService(ManagedServiceFactory.class.getName(), connectionExchangeMSF, svcProps);
		
		ExchangeWireEndpointMSF endpointMSF = new ExchangeWireEndpointMSF(context);
		svcProps = new Properties();
		svcProps.put(Constants.SERVICE_PID, "endpoint");
		endpointMsfReg = context.registerService(ManagedServiceFactory.class.getName(), endpointMSF, svcProps);
	}

	public void stop(BundleContext context) throws Exception {
		endpointMsfReg.unregister();
		connectionExchangeMsfReg.unregister();
		logTracker.close();
	}

}
