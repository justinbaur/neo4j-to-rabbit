package org.justinbaur.neo2rabbit.handler;

import java.util.concurrent.ExecutorService;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.event.TransactionData;
import org.neo4j.graphdb.event.TransactionEventHandler;
import org.neo4j.kernel.impl.logging.LogService;
import org.neo4j.logging.Log;

public class RabbitTransactionHandler implements TransactionEventHandler<Object> {


	private Log log;
	@SuppressWarnings("unused")
	private GraphDatabaseService graphService;
	
	public RabbitTransactionHandler(GraphDatabaseService graphService, ExecutorService executor,
			LogService logService) {
		this.log = logService.getUserLog(RabbitTransactionHandler.class);
		this.graphService = graphService;
	}

	public Object beforeCommit(TransactionData data) throws Exception {
		return null;
	}

	public void afterCommit(TransactionData data, Object state) {
		data.createdNodes().forEach(node -> {
			log.info("After commit message: Created node with id {}", node.getId());
		});
	}

	public void afterRollback(TransactionData data, Object state) {

	}

}
