package graphql;

import com.coxautodev.graphql.tools.GraphQLSubscriptionResolver;
import graphql.events.AnonymousUserChangeEvent;
import graphql.events.ChangeEventType;
import graphql.events.CustomerChangeEvent;
import graphql.events.EmailMessageChangeEvent;
import graphql.events.OneTimePasswordChangeEvent;
import graphql.events.UserChangeEvent;
import graphql.events.UserSessionChangeEvent;
import graphql.schema.DataFetchingEnvironment;
import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableEmitter;
import io.reactivex.rxjava3.core.FlowableOnSubscribe;
import io.reactivex.rxjava3.flowables.ConnectableFlowable;
import java.util.List;
import javax.annotation.PostConstruct;
import models.AnonymousUser;
import models.Customer;
import models.EmailMessage;
import models.OneTimePassword;
import models.User;
import models.UserSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.event.TransactionalEventListener;
import security.AppSessionProvider;
import store.DataStoreEvent;
import store.StoreEventType;

@org.springframework.stereotype.Component
public class Subscription
    implements GraphQLSubscriptionResolver, FlowableOnSubscribe<DataStoreEvent> {
  public ConnectableFlowable<DataStoreEvent> flowable;
  @Autowired private AppSessionProvider provider;
  private FlowableEmitter<DataStoreEvent> emitter;

  @PostConstruct
  public void init() {
    this.flowable = Flowable.create(this, BackpressureStrategy.BUFFER).publish();
    this.flowable.connect();
    flowable.subscribe((a) -> {});
  }

  @Async
  @TransactionalEventListener
  public void handleContextStart(DataStoreEvent event) {
    this.emitter.onNext(event);
  }

  @Override
  public void subscribe(FlowableEmitter<DataStoreEvent> emitter) throws Throwable {
    this.emitter = emitter;
  }

  public ChangeEventType from(StoreEventType type) {
    switch (type) {
      case INSERT:
        {
          return ChangeEventType.Insert;
        }
      case UPDATE:
        {
          return ChangeEventType.Update;
        }
      case DELETE:
        {
          return ChangeEventType.Delete;
        }
      default:
        {
          return null;
        }
    }
  }

  public Object onCustomerChangeEvent(DataFetchingEnvironment env) {
    provider.set(env);
    return this.flowable
        .filter((e) -> e.getEntity() instanceof Customer)
        .map(
            (e) -> {
              CustomerChangeEvent event = new CustomerChangeEvent();
              event.model = ((Customer) e.getEntity());
              event.changeType = from(e.getType());
              return event;
            });
  }

  public Object onCustomerChangeEventById(List<Long> ids, DataFetchingEnvironment env) {
    provider.set(env);
    return this.flowable
        .filter(
            (e) ->
                e.getEntity() instanceof Customer
                    && ids.contains(((Customer) e.getEntity()).getId()))
        .map(
            (e) -> {
              CustomerChangeEvent event = new CustomerChangeEvent();
              event.model = ((Customer) e.getEntity());
              event.changeType = from(e.getType());
              return event;
            });
  }

  public Object onAnonymousUserChangeEvent(DataFetchingEnvironment env) {
    provider.set(env);
    return this.flowable
        .filter((e) -> e.getEntity() instanceof AnonymousUser)
        .map(
            (e) -> {
              AnonymousUserChangeEvent event = new AnonymousUserChangeEvent();
              event.model = ((AnonymousUser) e.getEntity());
              event.changeType = from(e.getType());
              return event;
            });
  }

  public Object onAnonymousUserChangeEventById(List<Long> ids, DataFetchingEnvironment env) {
    provider.set(env);
    return this.flowable
        .filter(
            (e) ->
                e.getEntity() instanceof AnonymousUser
                    && ids.contains(((AnonymousUser) e.getEntity()).getId()))
        .map(
            (e) -> {
              AnonymousUserChangeEvent event = new AnonymousUserChangeEvent();
              event.model = ((AnonymousUser) e.getEntity());
              event.changeType = from(e.getType());
              return event;
            });
  }

  public Object onEmailMessageChangeEvent(DataFetchingEnvironment env) {
    provider.set(env);
    return this.flowable
        .filter((e) -> e.getEntity() instanceof EmailMessage)
        .map(
            (e) -> {
              EmailMessageChangeEvent event = new EmailMessageChangeEvent();
              event.model = ((EmailMessage) e.getEntity());
              event.changeType = from(e.getType());
              return event;
            });
  }

  public Object onEmailMessageChangeEventById(List<Long> ids, DataFetchingEnvironment env) {
    provider.set(env);
    return this.flowable
        .filter(
            (e) ->
                e.getEntity() instanceof EmailMessage
                    && ids.contains(((EmailMessage) e.getEntity()).getId()))
        .map(
            (e) -> {
              EmailMessageChangeEvent event = new EmailMessageChangeEvent();
              event.model = ((EmailMessage) e.getEntity());
              event.changeType = from(e.getType());
              return event;
            });
  }

  public Object onOneTimePasswordChangeEvent(DataFetchingEnvironment env) {
    provider.set(env);
    return this.flowable
        .filter((e) -> e.getEntity() instanceof OneTimePassword)
        .map(
            (e) -> {
              OneTimePasswordChangeEvent event = new OneTimePasswordChangeEvent();
              event.model = ((OneTimePassword) e.getEntity());
              event.changeType = from(e.getType());
              return event;
            });
  }

  public Object onOneTimePasswordChangeEventById(List<Long> ids, DataFetchingEnvironment env) {
    provider.set(env);
    return this.flowable
        .filter(
            (e) ->
                e.getEntity() instanceof OneTimePassword
                    && ids.contains(((OneTimePassword) e.getEntity()).getId()))
        .map(
            (e) -> {
              OneTimePasswordChangeEvent event = new OneTimePasswordChangeEvent();
              event.model = ((OneTimePassword) e.getEntity());
              event.changeType = from(e.getType());
              return event;
            });
  }

  public Object onUserChangeEvent(DataFetchingEnvironment env) {
    provider.set(env);
    return this.flowable
        .filter((e) -> e.getEntity() instanceof User)
        .map(
            (e) -> {
              UserChangeEvent event = new UserChangeEvent();
              event.model = ((User) e.getEntity());
              event.changeType = from(e.getType());
              return event;
            });
  }

  public Object onUserChangeEventById(List<Long> ids, DataFetchingEnvironment env) {
    provider.set(env);
    return this.flowable
        .filter(
            (e) -> e.getEntity() instanceof User && ids.contains(((User) e.getEntity()).getId()))
        .map(
            (e) -> {
              UserChangeEvent event = new UserChangeEvent();
              event.model = ((User) e.getEntity());
              event.changeType = from(e.getType());
              return event;
            });
  }

  public Object onUserSessionChangeEvent(DataFetchingEnvironment env) {
    provider.set(env);
    return this.flowable
        .filter((e) -> e.getEntity() instanceof UserSession)
        .map(
            (e) -> {
              UserSessionChangeEvent event = new UserSessionChangeEvent();
              event.model = ((UserSession) e.getEntity());
              event.changeType = from(e.getType());
              return event;
            });
  }

  public Object onUserSessionChangeEventById(List<Long> ids, DataFetchingEnvironment env) {
    provider.set(env);
    return this.flowable
        .filter(
            (e) ->
                e.getEntity() instanceof UserSession
                    && ids.contains(((UserSession) e.getEntity()).getId()))
        .map(
            (e) -> {
              UserSessionChangeEvent event = new UserSessionChangeEvent();
              event.model = ((UserSession) e.getEntity());
              event.changeType = from(e.getType());
              return event;
            });
  }
}
