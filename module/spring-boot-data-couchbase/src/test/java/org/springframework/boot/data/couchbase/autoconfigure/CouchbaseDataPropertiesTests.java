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

package org.springframework.boot.data.couchbase.autoconfigure;

import org.junit.jupiter.api.Test;

import org.springframework.data.couchbase.core.convert.DefaultCouchbaseTypeMapper;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link CouchbaseDataProperties}.
 *
 * @author Stephane Nicoll
 */
class CouchbaseDataPropertiesTests {

	@Test
	void typeKeyHasConsistentDefault() {
		assertThat(new CouchbaseDataProperties().getTypeKey()).isEqualTo(DefaultCouchbaseTypeMapper.DEFAULT_TYPE_KEY);
	}

}
