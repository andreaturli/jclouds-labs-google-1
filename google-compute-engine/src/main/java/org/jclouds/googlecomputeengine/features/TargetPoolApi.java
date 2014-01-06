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

import org.jclouds.Fallbacks.EmptyIterableWithMarkerOnNotFoundOr404;
import org.jclouds.Fallbacks.EmptyPagedIterableOnNotFoundOr404;
import org.jclouds.Fallbacks.NullOnNotFoundOr404;
import org.jclouds.collect.PagedIterable;
import org.jclouds.googlecomputeengine.domain.ListPage;
import org.jclouds.googlecomputeengine.domain.Operation;
import org.jclouds.googlecomputeengine.domain.TargetPool;
import org.jclouds.googlecomputeengine.functions.internal.ParseTargetPools;
import org.jclouds.googlecomputeengine.options.ListOptions;
import org.jclouds.javax.annotation.Nullable;
import org.jclouds.oauth.v2.config.OAuthScopes;
import org.jclouds.oauth.v2.filters.OAuthAuthenticator;
import org.jclouds.rest.annotations.Fallback;
import org.jclouds.rest.annotations.MapBinder;
import org.jclouds.rest.annotations.PayloadParam;
import org.jclouds.rest.annotations.RequestFilters;
import org.jclouds.rest.annotations.ResponseParser;
import org.jclouds.rest.annotations.SkipEncoding;
import org.jclouds.rest.annotations.Transform;
import org.jclouds.rest.binders.BindToJsonPayload;

import javax.inject.Named;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.net.URI;
import java.util.List;

import static org.jclouds.googlecomputeengine.GoogleComputeEngineConstants.COMPUTE_READONLY_SCOPE;
import static org.jclouds.googlecomputeengine.GoogleComputeEngineConstants.COMPUTE_SCOPE;

/**
 * Provides access to TargetPools via their REST API.
 *
 * @author Andrea Turli
 * @see <a href="https://developers.google.com/compute/docs/reference/latest/#TargetPools"/>
 */
@SkipEncoding({'/', '='})
@RequestFilters(OAuthAuthenticator.class)
public interface TargetPoolApi {

   /**
    * Returns the specified TargetPool resource.
    *
    * @param region     the name of the region scoping this request.
    * @param targetPool the name of the TargetPool resource to return.
    * @return a TargetPool resource.
    */
   @Named("TargetPools:get")
   @GET
   @Consumes(MediaType.APPLICATION_JSON)
   @Path("/regions/{region}/targetPools/{targetPool}")
   @OAuthScopes(COMPUTE_READONLY_SCOPE)
   @Fallback(NullOnNotFoundOr404.class)
   @Nullable
   TargetPool getInRegion(@PathParam("region") String region, @PathParam("targetPool") String targetPool);

   /**
    * Creates a TargetPool resource in the specified project and region using the data included in the request.
    *
    * @param targetPoolName the name of the targetPool.
    * @return an Operation resource. To check on the status of an operation, poll the Operations resource returned to
    *         you, and look for the status field.
    */
   @Named("TargetPools:insert")
   @POST
   @Consumes(MediaType.APPLICATION_JSON)
   @Produces(MediaType.APPLICATION_JSON)
   @Path("/regions/{region}/targetPools")
   @OAuthScopes({COMPUTE_SCOPE})
   @MapBinder(BindToJsonPayload.class)
   Operation createInRegion(@PathParam("region") String region,
                            @PayloadParam("name") String targetPoolName);

   /**
    * Creates a TargetPool resource in the specified project and region using the data included in the request.
    *
    * @param targetPoolName the name of the targetPool.
    * @param instances A list of resource URLs to the member VMs serving this pool. They must live in zones
    *                  contained in the same region as this pool.
    * @return an Operation resource. To check on the status of an operation, poll the Operations resource returned to
    *         you, and look for the status field.
    */
   @Named("TargetPools:insert")
   @POST
   @Consumes(MediaType.APPLICATION_JSON)
   @Produces(MediaType.APPLICATION_JSON)
   @Path("/regions/{region}/targetPools")
   @OAuthScopes({COMPUTE_SCOPE})
   @MapBinder(BindToJsonPayload.class)
   Operation createInRegion(@PathParam("region") String region,
                            @PayloadParam("name") String targetPoolName,
                            @PayloadParam("instances") List<URI> instances);

   /**
    * Creates a TargetPool resource in the specified project and region using the data included in the request.
    *
    * @param targetPoolName the name of the targetPool.
    * @param instances A list of resource URLs to the member VMs serving this pool. They must live in zones
    *                  contained in the same region as this pool.
    * @param healthChecks A URL to one HttpHealthCheck resource. A member VM in this pool is considered healthy if
    *                     and only if the specified health checks pass. An empty list means all member virtual
    *                     machines will  be considered healthy at all times but the health status of this target
    *                     pool will be marked as unhealthy to indicate that no health checks are being performed.
    * @return an Operation resource. To check on the status of an operation, poll the Operations resource returned to
    *         you, and look for the status field.
    */
   @Named("TargetPools:insert")
   @POST
   @Consumes(MediaType.APPLICATION_JSON)
   @Produces(MediaType.APPLICATION_JSON)
   @Path("/regions/{region}/targetPools")
   @OAuthScopes({COMPUTE_SCOPE})
   @MapBinder(BindToJsonPayload.class)
   Operation createInRegion(@PathParam("region") String region,
                            @PayloadParam("name") String targetPoolName,
                            @PayloadParam("instances") List<URI> instances,
                            @PayloadParam("healthChecks") List<URI> healthChecks);

   /**
    * Creates a TargetPool resource in the specified project and region using the data included in the request.
    *
    * @param targetPoolName the name of the targetPool.
    * @param instances A list of resource URLs to the member VMs serving this pool. They must live in zones
    *                  contained in the same region as this pool.
    * @param healthChecks A URL to one HttpHealthCheck resource. A member VM in this pool is considered healthy if
    *                     and only if the specified health checks pass. An empty list means all member virtual
    *                     machines will  be considered healthy at all times but the health status of this target
    *                     pool will be marked as unhealthy to indicate that no health checks are being performed.
    * @param backupPool it is applicable only when the target pool is serving a forwarding rule as the primary pool.
    *                   Must be a fully-qualified URL to a target pool that is in the same region as the primary
    *                   target pool.
    * @param sessionAffinity Defines the session affinity option. Session affinity determines the hash method that
    *                        Google Compute Engine uses to distribute traffic. Acceptable values are:
    *                        "CLIENT_IP": Connections from the same client IP are guaranteed to go to the same VM in the pool while that VM remains healthy.
    *                        "CLIENT_IP_PROTO":  Connections from the same client IP and port are guaranteed to go to the same VM in the pool while that VM remains healthy.
    *                        "NONE": Connections from the same client IP may go to any VM in the pool.
    * @return an Operation resource. To check on the status of an operation, poll the Operations resource returned to
    *         you, and look for the status field.
    */
   @Named("TargetPools:insert")
   @POST
   @Consumes(MediaType.APPLICATION_JSON)
   @Produces(MediaType.APPLICATION_JSON)
   @Path("/regions/{region}/targetPools")
   @OAuthScopes({COMPUTE_SCOPE})
   @MapBinder(BindToJsonPayload.class)
   Operation createInRegion(@PathParam("region") String region,
                            @PayloadParam("name") String targetPoolName,
                            @PayloadParam("instances") List<URI> instances,
                            @PayloadParam("healthChecks") List<URI> healthChecks,
                            @PayloadParam("backupPool") String backupPool,
                            @PayloadParam("sessionAffinity") String sessionAffinity);

   /**
    * Deletes the specified TargetPool resource.
    *
    * @param region     the region the target pool is in.
    * @param targetPool name of the persistent target pool resource to delete.
    * @return an Operation resource. To check on the status of an operation, poll the Operations resource returned to
    *         you, and look for the status field.
    */
   @Named("TargetPools:delete")
   @DELETE
   @Consumes(MediaType.APPLICATION_JSON)
   @Path("/regions/{region}/targetPools/{targetPool}")
   @OAuthScopes(COMPUTE_SCOPE)
   @Fallback(NullOnNotFoundOr404.class)
   @Nullable
   Operation deleteInRegion(@PathParam("region") String region, @PathParam("targetPool") String targetPool);

   /**
    * @see org.jclouds.googlecomputeengine.features.TargetPoolApi#listAtMarkerInRegion(String, String, org.jclouds.googlecomputeengine.options.ListOptions)
    */
   @Named("TargetPools:list")
   @GET
   @Consumes(MediaType.APPLICATION_JSON)
   @Path("/regions/{region}/targetPools")
   @OAuthScopes(COMPUTE_READONLY_SCOPE)
   @ResponseParser(ParseTargetPools.class)
   @Fallback(EmptyIterableWithMarkerOnNotFoundOr404.class)
   ListPage<TargetPool> listFirstPageInRegion(@PathParam("region") String region);

   /**
    * @see org.jclouds.googlecomputeengine.features.TargetPoolApi#listAtMarkerInRegion(String, String,
    * org.jclouds.googlecomputeengine.options.ListOptions)
    */
   @Named("TargetPools:list")
   @GET
   @Consumes(MediaType.APPLICATION_JSON)
   @Path("/regions/{region}/targetPools")
   @OAuthScopes(COMPUTE_READONLY_SCOPE)
   @ResponseParser(ParseTargetPools.class)
   @Fallback(EmptyIterableWithMarkerOnNotFoundOr404.class)
   ListPage<TargetPool> listAtMarkerInRegion(@PathParam("region") String region, @QueryParam("pageToken") @Nullable String marker);

   /**
    * Retrieves the listPage of target pool resources contained within the specified project and zone.
    * By default the listPage as a maximum size of 100, if no options are provided or ListOptions#getMaxResults() has
    * not been set.
    *
    * @param region      the region to search in
    * @param marker      marks the beginning of the next list page
    * @param listOptions listing options
    * @return a page of the listPage
    * @see org.jclouds.googlecomputeengine.options.ListOptions
    * @see org.jclouds.googlecomputeengine.domain.ListPage
    */
   @Named("TargetPools:list")
   @GET
   @Consumes(MediaType.APPLICATION_JSON)
   @Path("/regions/{region}/targetPools")
   @OAuthScopes(COMPUTE_READONLY_SCOPE)
   @ResponseParser(ParseTargetPools.class)
   @Fallback(EmptyIterableWithMarkerOnNotFoundOr404.class)
   ListPage<TargetPool> listAtMarkerInRegion(@PathParam("region") String region, @QueryParam("pageToken") @Nullable String marker, ListOptions listOptions);

   /**
    * @param region the region to list in
    * @return a Paged, Fluent Iterable that is able to fetch additional pages when required
    * @see org.jclouds.collect.PagedIterable
    * @see org.jclouds.googlecomputeengine.features.TargetPoolApi#listAtMarkerInRegion(String, String, org.jclouds.googlecomputeengine.options.ListOptions)
    */
   @Named("TargetPools:list")
   @GET
   @Consumes(MediaType.APPLICATION_JSON)
   @Path("/regions/{region}/targetPools")
   @OAuthScopes(COMPUTE_READONLY_SCOPE)
   @ResponseParser(ParseTargetPools.class)
   @Transform(ParseTargetPools.ToPagedIterable.class)
   @Fallback(EmptyPagedIterableOnNotFoundOr404.class)
   PagedIterable<TargetPool> listInRegion(@PathParam("region") String region);

   @Named("TargetPools:list")
   @GET
   @Consumes(MediaType.APPLICATION_JSON)
   @Path("/regions/{region}/targetPools")
   @OAuthScopes(COMPUTE_READONLY_SCOPE)
   @ResponseParser(ParseTargetPools.class)
   @Transform(ParseTargetPools.ToPagedIterable.class)
   @Fallback(EmptyPagedIterableOnNotFoundOr404.class)
   PagedIterable<TargetPool> listInRegion(@PathParam("region") String region, ListOptions options);

   /**
    * Adds instance url to targetPool.
    *
    * @param region the zone the target pool is in.
    * @param targetPool the name of the target pool.
    * @param instanceName the name for the instance to be added to targetPool.
    *
    * @return an Operation resource. To check on the status of an operation, poll the Operations resource returned to
    *         you, and look for the status field.
    */
   @Named("TargetPools:addInstance")
   @POST
   @Consumes(MediaType.APPLICATION_JSON)
   @Path("/regions/{region}/targetPools/{targetPool}/addInstance")
   @OAuthScopes(COMPUTE_SCOPE)
   @Fallback(NullOnNotFoundOr404.class)
   @MapBinder(BindToJsonPayload.class)
   @Nullable
   Operation addInstance(@PathParam("region") String region, @PathParam("targetPool") String targetPool,
                         @PayloadParam("instance") String instanceName);

   /**
    * Adds health check URL to targetPool.
    *
    * @param region the zone the target pool is in.
    * @param targetPool the name of the target pool.
    * @param healthCheck the name for the healthCheck to be added to targetPool.
    *
    * @return an Operation resource. To check on the status of an operation, poll the Operations resource returned to
    *         you, and look for the status field.
    */
   @Named("TargetPools:addHealthCheck")
   @POST
   @Consumes(MediaType.APPLICATION_JSON)
   @Path("/regions/{region}/targetPools/{targetPool}/addHealthCheck")
   @OAuthScopes(COMPUTE_SCOPE)
   @Fallback(NullOnNotFoundOr404.class)
   @MapBinder(BindToJsonPayload.class)
   @Nullable
   Operation addHealthCheck(@PathParam("region") String region, @PathParam("targetPool") String targetPool,
                         @PayloadParam("healthCheck") String healthCheck);

   /**
    * Removes instance URL from targetPool.
    *
    * @param region the zone the target pool is in.
    * @param targetPool the name of the target pool.
    * @param instanceName the name for the instance to be removed from targetPool.
    *
    * @return an Operation resource. To check on the status of an operation, poll the Operations resource returned to
    *         you, and look for the status field.
    */
   @Named("TargetPools:removeInstance")
   @POST
   @Consumes(MediaType.APPLICATION_JSON)
   @Path("/regions/{region}/targetPools/{targetPool}/removeInstance")
   @OAuthScopes(COMPUTE_SCOPE)
   @Fallback(NullOnNotFoundOr404.class)
   @MapBinder(BindToJsonPayload.class)
   @Nullable
   Operation removeInstance(@PathParam("region") String region, @PathParam("targetPool") String targetPool,
                            @PayloadParam("instanceName") String instanceName);

   /**
    * Changes backup pool configurations.
    *
    * @param region the zone the target pool is in.
    * @param targetPool the name of the target pool.
    * @param target the URL of target pool for which you want to use as backup.
    *
    * @return an Operation resource. To check on the status of an operation, poll the Operations resource returned to
    *         you, and look for the status field.
    */
   @Named("TargetPools:setBackup")
   @POST
   @Consumes(MediaType.APPLICATION_JSON)
   @Path("/regions/{region}/targetPools/{targetPool}/setBackup")
   @OAuthScopes(COMPUTE_SCOPE)
   @Fallback(NullOnNotFoundOr404.class)
   @MapBinder(BindToJsonPayload.class)
   @Nullable
   Operation setBackup(@PathParam("region") String region, @PathParam("targetPool") String targetPool,
                       @PayloadParam("target") String target);
}