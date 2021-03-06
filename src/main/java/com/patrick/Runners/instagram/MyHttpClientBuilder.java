package com.patrick.Runners.instagram;


// Builds an HttpClient Object, either with cookies or without

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyHttpClientBuilder {

  private static final Logger LOG = LoggerFactory.getLogger(MyHttpClientBuilder.class.getName());

  public static HttpClient buildHttpClient(Cookies cookies) {
    LOG.info("Building New HTTP Client");
    BasicCookieStore cookieStore = createCookieStore(cookies);
    HttpClient cookieClient = HttpClientBuilder.create().setDefaultCookieStore(cookieStore).build();
    return cookieClient;
  }


  public static BasicCookieStore createCookieStore(Cookies cookies){
    BasicCookieStore cookieStore = new BasicCookieStore();
    BasicClientCookie cookie1 = new BasicClientCookie("sessionid", cookies.getSessionid());
    BasicClientCookie cookie2 = new BasicClientCookie("csrftoken", cookies.getCsrftoken());

    cookie1.setDomain(".instagram.com");
    cookie1.setPath("/");
    cookie2.setDomain(".instagram.com");
    cookie2.setPath("/");
    cookieStore.addCookie(cookie1);
    cookieStore.addCookie(cookie2);

    return cookieStore;

  }


}
