package org.springdoc.core;

import java.lang.reflect.ParameterizedType;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonView;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.media.Schema;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@SuppressWarnings("rawtypes")
@Component
public class ResponseBuilder extends AbstractResponseBuilder {

	public ResponseBuilder(OperationBuilder operationBuilder) {
		super(operationBuilder);
	}

	@Override
	protected Schema calculateSchemaFromParameterizedType(Components components, ParameterizedType parameterizedType,
			JsonView jsonView) {
		Schema schemaN = null;
		if (Mono.class.getName().contentEquals(parameterizedType.getRawType().getTypeName())
				|| Flux.class.getName().contentEquals(parameterizedType.getRawType().getTypeName())) {
			if (parameterizedType.getActualTypeArguments()[0] instanceof ParameterizedType && ResponseEntity.class
					.getName().contentEquals(((ParameterizedType) parameterizedType.getActualTypeArguments()[0])
							.getRawType().getTypeName())) {
				ParameterizedType parameterizedTypeNew = (ParameterizedType) parameterizedType
						.getActualTypeArguments()[0];
				schemaN = calculateSchemaParameterizedType(components, parameterizedTypeNew, jsonView);
			} else {
				schemaN = calculateSchemaParameterizedType(components, parameterizedType, jsonView);
			}
		}
		return schemaN;
	}

}
