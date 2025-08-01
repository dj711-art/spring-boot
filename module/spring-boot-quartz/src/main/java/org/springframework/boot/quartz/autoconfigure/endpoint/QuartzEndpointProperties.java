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

package org.springframework.boot.quartz.autoconfigure.endpoint;

import java.util.HashSet;
import java.util.Set;

import org.springframework.boot.actuate.endpoint.Show;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.quartz.endpoint.QuartzEndpoint;

/**
 * Configuration properties for {@link QuartzEndpoint}.
 *
 * @author Madhura Bhave
 * @since 4.0.0
 */
@ConfigurationProperties("management.endpoint.quartz")
public class QuartzEndpointProperties {

	/**
	 * When to show unsanitized job or trigger values.
	 */
	private Show showValues = Show.NEVER;

	/**
	 * Roles used to determine whether a user is authorized to be shown unsanitized job or
	 * trigger values. When empty, all authenticated users are authorized.
	 */
	private final Set<String> roles = new HashSet<>();

	public Show getShowValues() {
		return this.showValues;
	}

	public void setShowValues(Show showValues) {
		this.showValues = showValues;
	}

	public Set<String> getRoles() {
		return this.roles;
	}

}
