package app.common.exception;

import java.util.Arrays;

import ninja.Result;
import ninja.Results;
import domain.common.exception.ResourceNotFoundException;

public class ResourceNotFoundExceptionHandlerFilter extends
		ExceptionHandlerFilter<ResourceNotFoundException> {

	@Override
	protected Result handleException(ResourceNotFoundException e) {
		ErrorModel errorModel = new ErrorModel(Arrays.asList(e.getMessage()));
		return Results.notFound().json().render(errorModel);
	}
}
