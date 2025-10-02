package dev.api.springmvc.api.users;

import dev.api.springmvc.api.users.dtos.UserEventDto;
import dev.api.springmvc.common.kafka.events.users.UserEventSubscriber;
import org.reactivestreams.Publisher;
import org.springframework.graphql.data.method.annotation.SubscriptionMapping;
import org.springframework.stereotype.Controller;

@Controller
public class UserGraphQLSubscription {

	private final UserEventSubscriber subscriber;

	public UserGraphQLSubscription(UserEventSubscriber subscriber) {
		this.subscriber = subscriber;
	}

	@SubscriptionMapping
	public Publisher<UserEventDto> userEvents() {
		return subscriber.getFlux();
	}
}

