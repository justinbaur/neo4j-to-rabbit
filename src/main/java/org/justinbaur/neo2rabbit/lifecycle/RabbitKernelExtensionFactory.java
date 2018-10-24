package org.justinbaur.neo2rabbit.lifecycle;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.kernel.configuration.Config;
import org.neo4j.kernel.extension.KernelExtensionFactory;
import org.neo4j.kernel.impl.logging.LogService;
import org.neo4j.kernel.impl.spi.KernelContext;
import org.neo4j.kernel.internal.GraphDatabaseAPI;
import org.neo4j.kernel.lifecycle.Lifecycle;

public class RabbitKernelExtensionFactory extends KernelExtensionFactory<RabbitKernelExtensionFactory.Dependencies> {
	protected RabbitKernelExtensionFactory() {
		super(RabbitKernelExtension.SERVICE_NAME);
	}

	public interface Dependencies {
		Config getConfig();

		GraphDatabaseService getGraphDatabaseService();
	}

	@Override
	public Lifecycle newInstance(KernelContext context, Dependencies dependencies) {
		return new RabbitKernelExtension(((GraphDatabaseAPI) dependencies.getGraphDatabaseService())
				.getDependencyResolver().resolveDependency(LogService.class), dependencies.getGraphDatabaseService());
	}

}
