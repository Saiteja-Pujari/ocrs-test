package main;

import com.coxautodev.graphql.tools.SchemaParserDictionary;
import d3e.core.D3ELocalResourceHandler;
import d3e.core.D3EResourceHandler;
import d3e.core.GraphQLFilter;
import d3e.core.TransactionWrapper;
import graphql.events.AnonymousUserChangeEvent;
import graphql.events.CustomerChangeEvent;
import graphql.events.EmailMessageChangeEvent;
import graphql.events.OneTimePasswordChangeEvent;
import graphql.events.UserChangeEvent;
import graphql.events.UserSessionChangeEvent;
import graphql.input.AnonymousUserEntityInput;
import models.AnonymousUser;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.solr.repository.config.EnableSolrRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;

@SpringBootApplication
@EnableJpaAuditing
@EnableTransactionManagement
@ComponentScan({
  "d3e.studio",
  "classes",
  "graphql",
  "helpers",
  "models",
  "repository",
  "security",
  "storage",
  "d3e.core",
  "store",
  "parser"
})
@EntityScan({
  "studio.d3e",
  "classes",
  "graphql",
  "helpers",
  "models",
  "repository",
  "security",
  "storage",
  "d3e.core",
  "org.meta"
})
@EnableJpaRepositories("repository.jpa")
@EnableSolrRepositories("repository.solr")
public class D3EStudio {
  public static void main(String[] args) {
    SpringApplication.run(D3EStudio.class, args);
  }

  @Bean
  public FilterRegistrationBean<GraphQLFilter> graphQLFilter(TransactionWrapper wrapper) {
    FilterRegistrationBean<GraphQLFilter> registrationBean =
        new FilterRegistrationBean<GraphQLFilter>();
    registrationBean.setFilter(new GraphQLFilter(wrapper));
    registrationBean.addUrlPatterns("/graphql");
    return registrationBean;
  }

  @Bean
  public SchemaParserDictionary getSchemaParser() {
    SchemaParserDictionary dictionary = new SchemaParserDictionary();
    dictionary.add("AnonymousUserChangeEvent", AnonymousUserChangeEvent.class);
    dictionary.add("CustomerChangeEvent", CustomerChangeEvent.class);
    dictionary.add("EmailMessageChangeEvent", EmailMessageChangeEvent.class);
    dictionary.add("OneTimePasswordChangeEvent", OneTimePasswordChangeEvent.class);
    dictionary.add("UserChangeEvent", UserChangeEvent.class);
    dictionary.add("UserSessionChangeEvent", UserSessionChangeEvent.class);
    dictionary.add("AnonymousUser", AnonymousUser.class);
    dictionary.add("AnonymousUserEntityInput", AnonymousUserEntityInput.class);
    return dictionary;
  }

  @Bean
  public ServletServerContainerFactoryBean createServletServerContainerFactoryBean() {
    ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
    container.setMaxTextMessageBufferSize(32768);
    container.setMaxBinaryMessageBufferSize(32768);
    return container;
  }

  @Bean
  @Primary
  public D3EResourceHandler getResourceHandler() {
    return new D3ELocalResourceHandler();
  }
}
