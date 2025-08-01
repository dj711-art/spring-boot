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

package org.springframework.boot.metrics.autoconfigure.export.simple;

import io.micrometer.core.instrument.simple.SimpleConfig;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Stephane Nicoll
 */
class SimplePropertiesTests {

	@Test
	void defaultValuesAreConsistent() {
		SimpleProperties properties = new SimpleProperties();
		SimpleConfig config = SimpleConfig.DEFAULT;
		assertThat(properties.getStep()).isEqualTo(config.step());
		assertThat(properties.getMode()).isEqualTo(config.mode());
	}

}
