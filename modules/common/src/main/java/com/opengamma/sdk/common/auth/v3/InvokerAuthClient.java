/*
 * Copyright (C) 2016 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.sdk.common.auth.v3;

import static com.opengamma.sdk.common.ServiceInvoker.MEDIA_JSON;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Objects;

import org.joda.beans.ser.JodaBeanSer;

import com.opengamma.sdk.common.v3.ServiceInvoker;

import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Implementation of the auth client.
 */
public final class InvokerAuthClient implements AuthClient {

  /**
   * The service invoker.
   */
  private final ServiceInvoker invoker;

  //-------------------------------------------------------------------------
  /**
   * Obtains an instance.
   * 
   * @param invoker  the service invoker
   * @return the client
   */
  static InvokerAuthClient of(ServiceInvoker invoker) {
    return new InvokerAuthClient(invoker);
  }

  private InvokerAuthClient(ServiceInvoker invoker) {
    this.invoker = Objects.requireNonNull(invoker, "invoker must not be null");
  }

  //-------------------------------------------------------------------------
  @Override
  public AccessTokenResult authenticateApiKey(String apiKey, String secret) {
    RequestBody requestBody = RequestBody.create(MEDIA_JSON, "{ \"grant_type\": \"client_credentials\","
        + "\"client_id\": \"" + apiKey + "\","
        + "\"client_secret\": \"" + secret + "\""
        + "}");
    return authenticate("auth/v3/token", "API key: " + apiKey, requestBody);
  }

  @Override
  public AccessTokenResult authenticateApiKey(ApiKeyCredentials credentials) {
    return credentials.authenticate(this);
  }

  private AccessTokenResult authenticate(String url, String message, RequestBody formBody) {
    Request request = new Request.Builder()
        .url(invoker.getServiceUrl().resolve(url))
        .post(formBody)
        .header("Content-Type", MEDIA_JSON.toString())
        .header("Accept", MEDIA_JSON.toString())
        .build();

    try (Response response = invoker.getHttpClient().newCall(request).execute()) {
      if (response.code() == 401) {
        throw new IllegalStateException("Authentication failed: Invalid credentials for " + message);
      }
      if (response.code() == 403) {
        throw new IllegalStateException("Authentication failed: Forbidden access for " + message);
      }
      if (!response.isSuccessful()) {
        throw new IllegalStateException("Authentication failed: " + response.code() + " for " + message);
      }
      return JodaBeanSer.COMPACT.jsonReader().read(response.body().string(), AccessTokenResult.class);

    } catch (IOException ex) {
      throw new UncheckedIOException(ex);
    }
  }

}