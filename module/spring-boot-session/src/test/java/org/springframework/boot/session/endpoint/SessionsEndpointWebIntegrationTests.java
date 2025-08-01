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

package org.springframework.boot.session.endpoint;

import java.util.Collections;

import net.minidev.json.JSONArray;

import org.springframework.boot.actuate.endpoint.web.test.WebEndpointTest;
import org.springframework.boot.actuate.endpoint.web.test.WebEndpointTest.Infrastructure;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.MapSession;
import org.springframework.session.Session;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

/**
 * Integration tests for {@link SessionsEndpoint} exposed by Jersey, Spring MVC, and
 * WebFlux.
 *
 * @author Vedran Pavic
 */
class SessionsEndpointWebIntegrationTests {

	private static final Session session = new MapSession();

	@SuppressWarnings("unchecked")
	private static final FindByIndexNameSessionRepository<Session> repository = mock(
			FindByIndexNameSessionRepository.class);

	@WebEndpointTest(infrastructure = { Infrastructure.JERSEY, Infrastructure.MVC })
	void sessionsForUsernameWithoutUsernameParam(WebTestClient client) {
		client.get()
			.uri((builder) -> builder.path("/actuator/sessions").build())
			.exchange()
			.expectStatus()
			.isBadRequest();
	}

	@WebEndpointTest(infrastructure = { Infrastructure.JERSEY, Infrastructure.MVC })
	void sessionsForUsernameNoResults(WebTestClient client) {
		given(repository.findByPrincipalName("user")).willReturn(Collections.emptyMap());
		client.get()
			.uri((builder) -> builder.path("/actuator/sessions").queryParam("username", "user").build())
			.exchange()
			.expectStatus()
			.isOk()
			.expectBody()
			.jsonPath("sessions")
			.isEmpty();
	}

	@WebEndpointTest(infrastructure = { Infrastructure.JERSEY, Infrastructure.MVC })
	void sessionsForUsernameFound(WebTestClient client) {
		given(repository.findByPrincipalName("user")).willReturn(Collections.singletonMap(session.getId(), session));
		client.get()
			.uri((builder) -> builder.path("/actuator/sessions").queryParam("username", "user").build())
			.exchange()
			.expectStatus()
			.isOk()
			.expectBody()
			.jsonPath("sessions.[*].id")
			.isEqualTo(new JSONArray().appendElement(session.getId()));
	}

	@WebEndpointTest(infrastructure = { Infrastructure.JERSEY, Infrastructure.MVC })
	void sessionForIdNotFound(WebTestClient client) {
		client.get()
			.uri((builder) -> builder.path("/actuator/sessions/session-id-not-found").build())
			.exchange()
			.expectStatus()
			.isNotFound();
	}

	@WebEndpointTest(infrastructure = { Infrastructure.JERSEY, Infrastructure.MVC })
	void deleteSession(WebTestClient client) {
		client.delete()
			.uri((builder) -> builder.path("/actuator/sessions/{id}").build(session.getId()))
			.exchange()
			.expectStatus()
			.isNoContent();
	}

	@Configuration(proxyBeanMethods = false)
	static class TestConfiguration {

		@Bean
		SessionsEndpoint sessionsEndpoint() {
			return new SessionsEndpoint(repository, repository);
		}

	}

}
