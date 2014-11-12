/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jclouds.oauth.v2.binders;

import static org.jclouds.oauth.v2.domain.Claims.EXPIRATION_TIME;
import static org.jclouds.oauth.v2.domain.Claims.ISSUED_AT;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertSame;
import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.util.Map;

import org.jclouds.http.HttpRequest;
import org.jclouds.json.config.GsonModule;
import org.jclouds.oauth.v2.domain.Header;
import org.jclouds.oauth.v2.domain.TokenRequest;
import org.jclouds.util.Strings2;
import org.testng.annotations.Test;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.base.Splitter;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;
import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Module;
import com.google.inject.Provides;

@Test(groups = "unit", testName = "OAuthTokenBinderTest")
public class TokenBinderTest {
   public static final String STRING_THAT_GENERATES_URL_UNSAFE_BASE64_ENCODING = "§1234567890'+±!\"#$%&/()" +
         "=?*qwertyuiopº´WERTYUIOPªàsdfghjklç~ASDFGHJKLÇ^<zxcvbnm,.->ZXCVBNM;:_@€";

   public void testPayloadIsUrlSafe() throws IOException {
      Header header = Header.create("a", "b");

      Map<String, Object> claims = ImmutableMap.<String, Object>builder()
            .put(ISSUED_AT, 0)
            .put(EXPIRATION_TIME, 0)
            .put("ist", STRING_THAT_GENERATES_URL_UNSAFE_BASE64_ENCODING).build();

      TokenRequest tokenRequest = TokenRequest.create(header, claims);
      HttpRequest request = tokenBinder.bindToRequest(
            HttpRequest.builder().method("GET").endpoint("http://localhost").build(), tokenRequest);

      assertNotNull(request.getPayload());

      String payload = Strings2.toStringAndClose(request.getPayload().getInput());

      // make sure the paylod is in the format {header}.{claims}.{signature}
      Iterable<String> parts = Splitter.on(".").split(payload);

      assertSame(Iterables.size(parts), 3);

      assertTrue(!payload.contains("+"));
      assertTrue(!payload.contains("/"));
   }

   private final TokenBinder tokenBinder = Guice.createInjector(new GsonModule(), new Module() {
      @Override public void configure(Binder binder) {
      }

      @Provides Supplier<Function<byte[], byte[]>> signer() {
         return (Supplier) Suppliers.ofInstance(Functions.constant(null));
      }
   }).getInstance(TokenBinder.class);
}
