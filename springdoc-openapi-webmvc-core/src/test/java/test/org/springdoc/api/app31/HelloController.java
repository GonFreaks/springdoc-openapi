package test.org.springdoc.api.app31;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.callbacks.Callback;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
public class HelloController {

	@PostMapping("/test")
	@Callback(callbackUrlExpression = "http://$request.query.url", name = "subscription", operation = {
			@Operation(method = "post", description = "payload data will be sent", parameters = {
					@Parameter(in = ParameterIn.PATH, name = "subscriptionId", required = true, schema = @Schema(type = "string", format = "uuid", description = "the generated UUID", accessMode = Schema.AccessMode.READ_ONLY)) }, responses = {
							@ApiResponse(responseCode = "200", description = "Return this code if the callback was received and processed successfully"),
							@ApiResponse(responseCode = "205", description = "Return this code to unsubscribe from future data updates"),
							@ApiResponse(responseCode = "default", description = "All other response codes will disable this callback subscription") }) })
	@Operation(description = "subscribes a client to updates relevant to the requestor's account, as "
			+ "identified by the input token.  The supplied url will be used as the delivery address for response payloads")
	public SubscriptionResponse subscribe(@Schema(required = true, description = "the authentication token "
			+ "provided after initially authenticating to the application") @RequestHeader("x-auth-token") String token,
			@Schema(required = true, description = "the URL to call with response "
					+ "data") @RequestParam("url") String url) {
		return null;
	}

	static class SubscriptionResponse {
		private String subscriptionUuid;

		public String getSubscriptionUuid() {
			return subscriptionUuid;
		}

		public void setSubscriptionUuid(String subscriptionUuid) {
			this.subscriptionUuid = subscriptionUuid;
		}

	}

}
