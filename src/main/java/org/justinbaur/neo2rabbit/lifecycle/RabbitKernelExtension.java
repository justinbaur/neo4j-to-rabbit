package org.justinbaur.neo2rabbit.lifecycle;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.justinbaur.neo2rabbit.handler.RabbitTransactionHandler;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.kernel.impl.logging.LogService;
import org.neo4j.kernel.lifecycle.LifecycleAdapter;
import org.neo4j.logging.Log;

public class RabbitKernelExtension extends LifecycleAdapter {

	public static final String SERVICE_NAME = "neo2rabbit";

	private Log log;
	private LogService logService;
	private GraphDatabaseService graphService;

	private RabbitTransactionHandler handler;
	private ExecutorService executor;

	public RabbitKernelExtension(LogService logService, GraphDatabaseService graphDatabaseService) {
		this.logService = logService;
		this.graphService = graphDatabaseService;

		log = logService.getUserLog(RabbitKernelExtension.class);
	}

	@Override
	public void start() {
		log.info("Starting rabbit kernel extension");
		executor = Executors.newFixedThreadPool(2);
		handler = new RabbitTransactionHandler(graphService, executor, logService);
		graphService.registerTransactionEventHandler(handler);
	}

	@Override
	public void shutdown() {
		log.info("Stopping rabbit kernel extension");
		executor.shutdown();
		graphService.unregisterTransactionEventHandler(handler);
	}

}
