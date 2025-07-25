/*
 * Copyright 2012-present the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.boot.undertow.servlet;

import io.undertow.Handlers;
import io.undertow.Undertow.Builder;
import io.undertow.server.HttpHandler;
import io.undertow.servlet.api.DeploymentManager;

import org.springframework.boot.undertow.HttpHandlerFactory;
import org.springframework.boot.undertow.UndertowWebServer;
import org.springframework.boot.web.server.WebServer;
import org.springframework.util.StringUtils;

/**
 * {@link WebServer} that can be used to control an embedded Undertow server. Typically
 * this class should be created using {@link UndertowServletWebServerFactory} and not
 * directly.
 *
 * @author Ivan Sopov
 * @author Andy Wilkinson
 * @author Eddú Meléndez
 * @author Christoph Dreis
 * @author Kristine Jetzke
 * @since 4.0.0
 * @see UndertowServletWebServerFactory
 */
public class UndertowServletWebServer extends UndertowWebServer {

	private final String contextPath;

	private final DeploymentManager manager;

	/**
	 * Create a new {@link UndertowServletWebServer} instance.
	 * @param builder the builder
	 * @param httpHandlerFactories the handler factories
	 * @param contextPath the root context path
	 * @param autoStart if the server should be started
	 * @since 4.0.0
	 */
	public UndertowServletWebServer(Builder builder, Iterable<HttpHandlerFactory> httpHandlerFactories,
			String contextPath, boolean autoStart) {
		super(builder, httpHandlerFactories, autoStart);
		this.contextPath = contextPath;
		this.manager = findManager(httpHandlerFactories);
	}

	private DeploymentManager findManager(Iterable<HttpHandlerFactory> httpHandlerFactories) {
		for (HttpHandlerFactory httpHandlerFactory : httpHandlerFactories) {
			if (httpHandlerFactory instanceof DeploymentManagerHttpHandlerFactory deploymentManagerFactory) {
				return deploymentManagerFactory.getDeploymentManager();
			}
		}
		return null;
	}

	@Override
	protected HttpHandler createHttpHandler() {
		HttpHandler handler = super.createHttpHandler();
		if (StringUtils.hasLength(this.contextPath)) {
			handler = Handlers.path().addPrefixPath(this.contextPath, handler);
		}
		return handler;
	}

	@Override
	protected String getStartedLogMessage() {
		String contextPath = StringUtils.hasText(this.contextPath) ? this.contextPath : "/";
		StringBuilder message = new StringBuilder(super.getStartedLogMessage());
		message.append(" with context path '");
		message.append(contextPath);
		message.append("'");
		return message.toString();
	}

	public DeploymentManager getDeploymentManager() {
		return this.manager;
	}

}
