/*
 * Licensed to jclouds, Inc. (jclouds) under one or more contributor license agreements.
 * See the NOTICE file distributed with this work for additional information regarding
 * copyright ownership.
 * jclouds licenses this file to you under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.  You may obtain a copy of the Licens at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */
package org.jclouds.googlecomputeengine.features;

import com.google.common.collect.Lists;
import org.jclouds.collect.PagedIterable;
import org.jclouds.googlecomputeengine.domain.ForwardingRule;
import org.jclouds.googlecomputeengine.domain.TargetPool;
import org.jclouds.googlecomputeengine.internal.BaseGoogleComputeEngineApiLiveTest;
import org.jclouds.googlecomputeengine.options.ListOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

/**
 * @author Andrea Turli
 */
public class ForwardingRuleApiLiveTest extends BaseGoogleComputeEngineApiLiveTest {

   private static final String FORWARDING_RULE_NAME = "forwarding-rule-api-live-test";
   private static final String TARGETPOOL_NAME = "forwarding-rule-api-live-test-targetpool";
   private static final int TIME_WAIT = 30;
   private TargetPool targetPool;

   /**
    * The API under test
    * @return
    */
   private ForwardingRuleApi api() {
      return api.getForwardingRuleApiForProject(userProject.get());
   }

   private TargetPoolApi targetPoolApi() {
      return api.getTargetPoolApiForProject(userProject.get());
   }

   @BeforeClass
   public void init() {
      assertRegionOperationDoneSucessfully(targetPoolApi().createInRegion(DEFAULT_REGION_NAME, TARGETPOOL_NAME), TIME_WAIT);
      targetPool = targetPoolApi().getInRegion(DEFAULT_REGION_NAME, TARGETPOOL_NAME);
   }

   @AfterClass
   public void tearDown() {
      assertRegionOperationDoneSucessfully(targetPoolApi().deleteInRegion(DEFAULT_REGION_NAME, TARGETPOOL_NAME), TIME_WAIT);
   }

   @Test(groups = "live")
   public void testInsertForwardingRule() {
      assertRegionOperationDoneSucessfully(api().createInRegion(DEFAULT_REGION_NAME, FORWARDING_RULE_NAME, targetPool.getSelfLink()), TIME_WAIT);
   }

   @Test(groups = "live", dependsOnMethods = "testInsertForwardingRule")
   public void testGetForwardingRule() {
      ForwardingRule forwardingRule = api().getInRegion(DEFAULT_REGION_NAME, FORWARDING_RULE_NAME);
      assertNotNull(forwardingRule);
      assertEquals(forwardingRule.getName(), FORWARDING_RULE_NAME);
   }

   @Test(groups = "live", dependsOnMethods = "testGetForwardingRule")
   public void testListForwardingRule() {

      PagedIterable<ForwardingRule> forwardingRule = api().listInRegion(DEFAULT_REGION_NAME, new ListOptions.Builder()
              .filter("name eq " + FORWARDING_RULE_NAME));
      List<ForwardingRule> forwardingRulesAsList = Lists.newArrayList(forwardingRule.concat());
      assertEquals(forwardingRulesAsList.size(), 1);
   }

   @Test(groups = "live", dependsOnMethods = "testListForwardingRule")
   public void testDeleteForwardingRule() {
      assertRegionOperationDoneSucessfully(api().deleteInRegion(DEFAULT_REGION_NAME, FORWARDING_RULE_NAME), TIME_WAIT);
   }
}
