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
package org.jclouds.googlecomputeengine.compute.functions;

import javax.inject.Inject;

import org.jclouds.compute.functions.GroupNamingConvention;

import com.google.common.base.Predicate;

/**
 * The convention for naming instance tags that firewall rules recognise.
 */
public final class FirewallTagNamingConvention {

   public static final class Factory {

      private final GroupNamingConvention.Factory namingConvention;

      @Inject Factory(GroupNamingConvention.Factory namingConvention) {
         this.namingConvention = namingConvention;
      }

      public FirewallTagNamingConvention get(String groupName) {
         return new FirewallTagNamingConvention(namingConvention.create().sharedNameForGroup(groupName));
      }
   }

   private final String sharedResourceName;

   public FirewallTagNamingConvention(String sharedResourceName) {
      this.sharedResourceName = sharedResourceName;
   }

   public String name(int port) {
      return String.format("%s-port-%s", sharedResourceName, port);
   }

   public Predicate<String> isFirewallTag() {
      return new Predicate<String>() {
         @Override
         public boolean apply(String input) {
            return input != null && input.startsWith(sharedResourceName + "-port-");
         }
      };
   }
}
